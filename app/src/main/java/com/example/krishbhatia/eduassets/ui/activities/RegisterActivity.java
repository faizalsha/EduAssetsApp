package com.example.krishbhatia.eduassets.ui.activities;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.utils.NetworkUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterAcitvity";
    //UI Related
    private EditText editTextRegisterEmail;

    private EditText editTextRegisterPassword;
    private AppCompatButton buttonRegisterButton;
    private Context mContext;

    //Firebase Related
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = RegisterActivity.this;

        editTextRegisterEmail = findViewById(R.id.registerEmail);

        editTextRegisterPassword = findViewById(R.id.registerPassword);
        buttonRegisterButton = findViewById(R.id.regbutton);
        buttonRegisterButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.regbutton)
            registerUser();
    }

    private void registerUser() {
        String registerEmail = editTextRegisterEmail.getText().toString();
        String registerPassword = editTextRegisterPassword.getText().toString();

        if(!TextUtils.isEmpty(registerEmail) && !TextUtils.isEmpty(registerPassword)){

            if (NetworkUtils.isConnectedToInternert(mContext)){
                mAuth.createUserWithEmailAndPassword(registerEmail, registerPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(mContext,    "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    sendEmailVerificationLink(user);
//                                    updateUI(user);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(mContext, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            } else {
                Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

        } else{
            Toast.makeText(mContext, "Empty Fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmailVerificationLink(final FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {
                            Log.i(TAG, "Verification email sent to " + user.getEmail());
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent intent = new Intent(mContext, DetailsActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
