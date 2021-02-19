package com.example.oxygen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.example.oxygen.R;
import java.io.IOException;

public class AnimationStartActivity extends AppCompatActivity {

    private ImageView _imagView;
    private static int TIME_OUT = 6000; //Time to launch the another activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(AnimationStartActivity.this.getAssets().open("logo.jpg")); //load image from assets file
        } catch (IOException e) {
            e.printStackTrace();
        }

        _imagView = (ImageView) findViewById(R.id.imageView1); //load from xml the image view
        _imagView.setImageBitmap(bmp); // add the image to imageview

        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f,
                -400.0f, 400.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(5000);  // animation duration
        animation.setRepeatCount(5);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )

        _imagView.startAnimation(animation); // add animation in imageview

        new Handler().postDelayed(new Runnable() { // after  60000 miliseconds go to menu automatical
            @Override
            public void run() {
                Intent i = new Intent(AnimationStartActivity.this, LogInActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }

}