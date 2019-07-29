package com.example.krishbhatia.eduassets.ui.activities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.utils.NetworkUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity{

    private static final String TAG = "RegisterAcitvity";
    //UI Related
    private EditText emailRegister;

    private EditText passwordRegister;
    private Button buttonRegisterButton;
    private Context mContext;

    //Firebase Related
    private FirebaseAuth mAuth;
    private CheckBox checkBoxShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = RegisterActivity.this;
        checkBoxShowPassword=findViewById(R.id.checkBoxShowPassword);
        emailRegister = findViewById(R.id.registerEmail);

        passwordRegister = findViewById(R.id.registerPassword);
        buttonRegisterButton = findViewById(R.id.regbutton);
        buttonRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        checkBoxShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   passwordRegister.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    passwordRegister.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }


    private void registerUser() {
        final String registerEmail = emailRegister.getText().toString();
        String registerPassword = passwordRegister.getText().toString();

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
                                    Toast.makeText(mContext, "Email Verification link has been sent. Verify your email then login", Toast.LENGTH_LONG).show();
//                                    updateUI(user);
                                    finish();
                                } else {

                                    try{
                                        throw task.getException();
                                    }catch (FirebaseAuthUserCollisionException e){
                                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
                                        Query query = userRef.orderByChild("email").equalTo(registerEmail);
                                        query.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()){
                                                    Toast.makeText(mContext, "Already a user: Go to login page", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(mContext, "Verification Email has been SENT. if didn't get the mail try 'resetting password'", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(mContext, "Authentication failed: " + task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "onComplete: " + task.getException().getMessage());
                                        //updateUI(null);
                                    }
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

//    private void updateUI(FirebaseUser user) {
//        if(user != null){
//            Intent intent = new Intent(mContext, DetailsActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

}
