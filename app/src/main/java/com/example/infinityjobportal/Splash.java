package com.example.infinityjobportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView img;
    private static final String TAG = "Splash";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img=findViewById(R.id.imageView2);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.clockwise);
        img.startAnimation(animation);

        Thread timer=new Thread()
        {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally
                {
                    Intent i=new Intent(Splash.this, ClientLogin.class);
                    finish();
                    startActivity(i);
                }
            }
        };
        timer.start();
//        Intent i = new Intent(getApplicationContext(),ClientLogin.class);
//        startActivity(i);
    }
}