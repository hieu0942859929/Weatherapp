package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Progressbar extends Animation {
    private final Context context;
    private final ProgressBar progressBar;
    private final TextView textViewLoading;
    private final float from;
    private final float to;

    public Progressbar(Context context, ProgressBar progressBar, TextView textViewLoading, float from, float to) {
        this.context = context;
        this.progressBar = progressBar;
        this.textViewLoading = textViewLoading;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
        textViewLoading.setText((int) value + " %");

        if (value == to) {
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }
}
