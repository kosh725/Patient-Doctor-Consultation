package com.group4.patientdoctorconsultation.ui.dialogfragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.PacketItemDialog;
import com.group4.patientdoctorconsultation.data.model.DataPacketItem;
import com.group4.patientdoctorconsultation.utilities.heartrate.HeartRateCalculator;
import com.group4.patientdoctorconsultation.utilities.heartrate.HeartRateListener;

public class HeartRateDialogFragment extends PacketItemDialog implements HeartRateListener {

    HeartRateCalculator heartRateCalculator;
    TextView heartRateText;
    ImageView heartImage;

    @Override
    @SuppressLint("InflateParams")
    public View getView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_dialog_heart_rate, null);
        SurfaceView preview = view.findViewById(R.id.preview);

        heartRateText = view.findViewById(R.id.text);
        heartImage = view.findViewById(R.id.heart_icon);
        heartRateCalculator = new HeartRateCalculator(preview.getHolder(), this);

        return view;
    }

    @Override
    public String getDialogResult(){
        return heartRateText != null ? heartRateText.getText().toString() : "";
    }

    @Override
    public DataPacketItem.DataPacketItemType getPacketItemType() {
        return DataPacketItem.DataPacketItemType.HEART_RATE;
    }

    @Override
    protected String getTitle() {
        return "Record heart rate";
    }

    @Override
    public void onHeartBeat() {
        heartImage.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(() -> heartImage.setVisibility(View.GONE), 100);
    }

    @Override
    public void onHeartRate(Integer heartRate) {
        String heartRateText = String.valueOf(heartRate) + " BPM";
        this.heartRateText.setText(heartRateText);
    }

    @Override
    public void onPause() {
        heartRateCalculator.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        heartRateCalculator.resume();
        super.onResume();
    }
}
