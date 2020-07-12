package com.example.covidhelper;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {
    private Context context;
    private ProgressBar progress_bar;
    private TextView text_view;
    private float from;
    private float to;

    public ProgressBarAnimation(Context context,
                                ProgressBar progress_bar,
                                TextView text_view,
                                float from,
                                float to) {
        this.context = context;
        this.progress_bar = progress_bar;
        this.text_view = text_view;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progress_bar.setProgress((int) value);
        text_view.setText((int)value + "%");

        if (value == to) {
            context.startActivity(new Intent(context, LoginActivity.class));
            ((LoadingPage) context).finish();
        }
    }
}
