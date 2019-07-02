package com.example.krishbhatia.eduassets.ui.activities;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.krishbhatia.eduassets.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    public static boolean animation =true;

    private Context mContext;
    private  EditText emailEditText, passwordEditText;
    private RelativeLayout rootview, afteranimation;
    private ImageView logoTransition;
    private ProgressBar loadingProgressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loginlayout);
        mContext = LoginActivity.this;
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


        settingupMaterialLogin();



   }



    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }
    //--------------------------------------------------------------------------


    private void settingupMaterialLogin(){
        rootview=findViewById(R.id.rootView);
        afteranimation=findViewById(R.id.afterAnimationView);
        logoTransition =findViewById(R.id.bookIconImageView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        if(animation) {
            final CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Log.d(TAG, "onTick: initialising");
                    loadingProgressBar.setVisibility(GONE);
                    rootview.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSplashText));
                    logoTransition.setImageResource(R.drawable.logopng);
                    startAnimation();
                }
            };
            countDownTimer.start();
        }
        else {
            Log.d(TAG, "onTick: initialising without countdown");
//            book.setVisibility(GONE);
            loadingProgressBar.setVisibility(GONE);
            rootview.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSplashText));
            logoTransition.setImageResource(R.drawable.logopng);
            startAnimation();

        }

    }

    private void startAnimation(){
        ViewPropertyAnimator viewPropertyAnimator = logoTransition.animate();
        viewPropertyAnimator.x(50f);
        viewPropertyAnimator.y(40f);
        viewPropertyAnimator.setDuration(1000);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                afteranimation.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }








    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder

                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}