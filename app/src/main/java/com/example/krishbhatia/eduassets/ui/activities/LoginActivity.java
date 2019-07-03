package com.example.krishbhatia.eduassets.ui.activities;


import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    public static boolean animation =true;

    private Context mContext;
    private RelativeLayout rootview, afteranimation;
    private ImageView logoTransition;
    private ProgressBar loadingProgressBar;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button buttonLogin;
    private TextView textViewRegister;

    //Firebase Related
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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
        mContext = LoginActivity.this;
        buttonLogin = findViewById(R.id.loginButton);
        textViewRegister = findViewById(R.id.textsignup);
        mAuth = FirebaseAuth.getInstance();

        passwordEditText.setOnClickListener(this);
        emailEditText.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);

        setupFirebaseAuth();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                login();
                break;
            case R.id.signup:
            case R.id.textsignup:
                sendToRegisterActivity();
        }
    }



    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            Toast.makeText(mContext, "Logging In...", Toast.LENGTH_SHORT).show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(mContext, "Signed In", Toast.LENGTH_SHORT).show();
                        sendToHomeActivity();
                    }else {
                        Toast.makeText(mContext, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }else {
            Toast.makeText(mContext, "Error: Empty Fields", Toast.LENGTH_SHORT).show();
        }

    }
    public void setupFirebaseAuth() {
        //mFirebaseDatabase=FirebaseDatabase.getInstance();
        //myRef=mFirebaseDatabase.getReference();
        //firebaseMethods=new FirebaseMethods(mContext);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(mContext, "User Logged in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Not Logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();

        updateUI(mAuth.getCurrentUser());
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void updateUI(FirebaseUser user){
        if(user!=null){
            Intent i = new Intent(mContext, HomePageActivity.class);
            startActivity(i);
        }
    }

    private void sendToHomeActivity() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }
    private void sendToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

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




