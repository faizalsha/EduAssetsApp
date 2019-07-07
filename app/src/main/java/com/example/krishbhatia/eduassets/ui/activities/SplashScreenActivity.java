package com.example.krishbhatia.eduassets.ui.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.utils.Constants;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;

import static android.view.View.GONE;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar=findViewById(R.id.loadingProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        final CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onTick: initialising");
proceedingIntent();
            }
        };
        countDownTimer.start();
    }
    private void proceedingIntent(){
        if(SharedPreferenceImpl.getSomeStringValue(this,Constants.USER_ID)!=null && SharedPreferenceImpl.getSomeStringValue(this,Constants.USER_ID).equals(Constants.NOT_FOUND)){
            Log.d(TAG, "proceedingIntent: user is logged in");
            startActivity(new Intent(SplashScreenActivity.this,HomePageActivity.class));
        }
        else {
            Log.d(TAG, "proceedingIntent: user is not logged in");
            startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));

        }
    }
    }

