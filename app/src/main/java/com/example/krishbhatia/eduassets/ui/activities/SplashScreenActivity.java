package com.example.krishbhatia.eduassets.ui.activities;

import android.app.usage.NetworkStats;
import android.content.Intent;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import bolts.Task;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";
    private ProgressBar progressBar;
    private UserPOJO userPOJO;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.loadingProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        final CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(mAuth.getCurrentUser()!=null){

                }
                else {
                    startActivity(new Intent(SplashScreenActivity.this,HomePageActivity.class));
                    finish();
                }
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
        if(SharedPreferenceImpl.getInstance().get(Constants.USER_ID,this)!=null && !SharedPreferenceImpl.getInstance().get(Constants.USER_ID,this).equals(Constants.NOT_FOUND)){
            Log.d(TAG, "proceedingIntent: user is logged in");
            if (SharedPreferenceImpl.getInstance().get(Constants.USERPOJO, this) == Constants.NOT_FOUND) {
                getOldUserDetails();
            } else {
                getNewUserDetails();



            }

        }
        else {
            Log.d(TAG, "proceedingIntent: user is not logged in");

            startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
            finish();
        }
    }

    private void getOldUserDetails() {
        if (mAuth == null) {
            userPOJO = new UserPOJO();
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userPOJO = dataSnapshot.child("users").child(mAuth.getUid()).getValue(UserPOJO.class);
                    if (userPOJO == null) {
                        mAuth.signOut();

                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        SharedPreferenceImpl.getInstance().addUserPojo(userPOJO, SplashScreenActivity.this);
                        startActivity(new Intent(SplashScreenActivity.this, HomePageActivity.class));
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {

            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void getNewUserDetails() {
        Gson gson = new Gson();
        userPOJO = gson.fromJson(SharedPreferenceImpl.getInstance().get(Constants.USERPOJO, this), UserPOJO.class);
        if(userPOJO==null){

            startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
            finish();

        }
        else {

            databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if(!dataSnapshot.child(userPOJO.getUserId()).exists()){
                           Toast.makeText(SplashScreenActivity.this, "You are logged out.", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                SharedPreferenceImpl.getInstance().clearAll(SplashScreenActivity.this);
                startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                finish();
                       }
                       else {
                           SharedPreferenceImpl.getInstance().addUserPojo(userPOJO, SplashScreenActivity.this);
            startActivity(new Intent(SplashScreenActivity.this,HomePageActivity.class));
            finish();
                       }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
    }

