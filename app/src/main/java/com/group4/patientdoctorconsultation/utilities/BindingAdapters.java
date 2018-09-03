package com.group4.patientdoctorconsultation.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseMethod;
import android.util.Log;
import android.widget.ImageView;

import com.group4.patientdoctorconsultation.common.GlideApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingAdapters {

    private static final String TAG = BindingAdapters.class.getSimpleName();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @SuppressLint("CheckResult")
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();

        GlideApp.with(context)
                .load(url)
                .into(imageView);
    }

    public static String getSimpleDateString(Date date){
        return date != null ? dateFormat.format(date) : "";
    }

    @InverseMethod("getSimpleDateString")
    public static Date getDateFromSimpleString(String dateString){
        try{
            return dateFormat.parse(dateString);
        }catch (Exception e){
            Log.w(TAG, e);
        }

        return new Date();
    }

}