package com.example.covidhelper;

import android.media.Image;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class LoadingPage extends AppCompatActivity {
    private ProgressBar progress_bar;
    private TextView text_view;
    private ImageView loading_image;
    private FirebaseAuth auth;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progress_bar = findViewById(R.id.loading_bar);
        text_view = findViewById(R.id.progress);
        loading_image = findViewById(R.id.loading_image);

        progress_bar.setMax(100);
        progress_bar.setScaleY(3f);

        progressAnimation();
        connectToDatabase();
    }

    private void connectToDatabase() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
    }

    public void progressAnimation() {
        ProgressBarAnimation anim_bar = new ProgressBarAnimation(this,
                progress_bar,
                text_view,
                0f,
                100f);

        anim_bar.setDuration(5000);
        progress_bar.setAnimation(anim_bar);

        Animation anim_logo = AnimationUtils.loadAnimation(this, R.anim.loading_transition);
        loading_image.setAnimation(anim_logo);
    }
}
