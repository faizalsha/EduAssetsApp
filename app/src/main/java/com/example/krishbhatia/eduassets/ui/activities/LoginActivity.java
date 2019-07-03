package com.example.krishbhatia.eduassets.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.R;
import com.facebook.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private EditText editTextEmailLogin;
    private EditText editTextPasswordLogin;
    private Button buttonLogin;
    private TextView textViewRegister;

    //Firebase Related
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = LoginActivity.this;
        editTextEmailLogin = findViewById(R.id.emailLogin);
        editTextPasswordLogin = findViewById(R.id.passwordLogin);
        buttonLogin = findViewById(R.id.loginButton);
        textViewRegister = findViewById(R.id.link_signup);
        mAuth = FirebaseAuth.getInstance();

        editTextPasswordLogin.setOnClickListener(this);
        editTextEmailLogin.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);

        setupFirebaseAuth();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginbutton:
                login();
                break;
            case R.id.link_signup:
                sendToRegisterActivity();
        }
    }



    private void login() {
        String email = editTextEmailLogin.getText().toString();
        String password = editTextPasswordLogin.getText().toString();
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
}