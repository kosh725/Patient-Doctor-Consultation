package com.group4.patientdoctorconsultation.utilities.heartrate;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.group4.patientdoctorconsultation.utilities.ArrayUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.group4.patientdoctorconsultation.utilities.heartrate.HeartRateCalculator.FrameColour.GREEN;
import static com.group4.patientdoctorconsultation.utilities.heartrate.HeartRateCalculator.FrameColour.RED;
import static com.group4.patientdoctorconsultation.utilities.heartrate.ImageProcessor.getSmallestPreviewSize;

public class HeartRateCalculator implements Camera.PreviewCallback, SurfaceHolder.Callback{

    public enum FrameColour { GREEN, RED }

    private static final String TAG = HeartRateCalculator.class.getSimpleName();
    private static final int MINIMUM_SAMPLE_SECONDS = 10;
    private static final AtomicBoolean processing = new AtomicBoolean(false);

    private FrameColour lastFrameColour = GREEN;
    private ArrayList<Integer> redInFrames = new ArrayList<>();
    private ArrayList<Integer> bpmReadings = new ArrayList<>();
    private double beats = 0;
    private long startTime = 0;

    private HeartRateListener heartRateListener;
    private SurfaceHolder previewHolder;
    private Camera camera;

    public HeartRateCalculator(SurfaceHolder previewHolder, HeartRateListener heartRateListener) {
        this.previewHolder = previewHolder;
        this.heartRateListener = heartRateListener;
        previewHolder.addCallback(this);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Camera.Size size = camera.getParameters().getPreviewSize();

        Objects.requireNonNull(data);
        Objects.requireNonNull(size);

        if (processing.compareAndSet(false, true)){
            processFrame(data, size);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            camera.setPreviewDisplay(previewHolder);
            camera.setPreviewCallback(this);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        Camera.Size size = getSmallestPreviewSize(width, height, parameters);
        if (size != null) {
            parameters.setPreviewSize(size.width, size.height);
        }
        camera.setParameters(parameters);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {}


    public void resume() {
        camera = Camera.open();
        startTime = System.currentTimeMillis();
    }

    public void pause() {
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    private void processFrame(byte[] data, Camera.Size frameSize) {
        int redInCurrentFrame = ImageProcessor.decodeYUV420SPtoRedAvg(data.clone(), frameSize.width, frameSize.height);
        int averageRed = (int) ArrayUtils.calculateAverage(redInFrames);

        // If difference between average and current value is greater than 30%, drop the current measurements as they are invalid
        // This can occur if the user moves their fin
        if(averageRed != 0 && (Math.abs(redInCurrentFrame - averageRed) * 100 / averageRed) > 30){
            resetMeasurements();
            return;
        }

        if (redInCurrentFrame > averageRed && lastFrameColour != RED) {
            beats++;
            lastFrameColour = RED;
            heartRateListener.onHeartBeat();
        } else if (redInCurrentFrame < averageRed) {
            lastFrameColour = GREEN;
        }

        redInFrames.add(redInCurrentFrame);

        double totalTimeInSecs = (System.currentTimeMillis() - startTime) / 1000.0;
        if (totalTimeInSecs >= MINIMUM_SAMPLE_SECONDS) {
            int bpm = (int) ((beats / totalTimeInSecs) * 60.0);
            if (bpm < 30 || bpm > 180) {
                resetMeasurements();
                return;
            }
            bpmReadings.add(bpm);
            int averageBpm = (int) ArrayUtils.calculateAverage(bpmReadings);
            heartRateListener.onHeartRate(averageBpm);
            startTime = System.currentTimeMillis();
            beats = 0;
        }

        processing.set(false);
    }

    private void resetMeasurements(){
        beats = 0;
        bpmReadings.clear();
        redInFrames.clear();
        startTime = System.currentTimeMillis();
        processing.set(false);
    }
}
