package com.example.krishbhatia.eduassets.ui.activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.R;

import com.example.krishbhatia.eduassets.databinding.LoginlayoutBinding;
import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.FirebaseMethods;
import com.example.krishbhatia.eduassets.SharedPreferenceImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

<<<<<<< HEAD

public class LoginActivity extends AppCompatActivity {
=======
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
>>>>>>> 2ffb7195bd915b9c8ab69a61826e1fc23d227b08
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 75;
    public  boolean animation =true;
    private LoginlayoutBinding loginlayoutBinding;
    private Context mContext;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference userDatabaseReference;
    private FirebaseMethods firebaseMethods;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginlayoutBinding=DataBindingUtil.setContentView(this,R.layout.loginlayout);

        mContext = LoginActivity.this;

        firebaseMethods=new FirebaseMethods(mContext);
        if(SharedPreferenceImpl.getSomeStringValue(LoginActivity.this, Constants.USER_ID)!=null && !SharedPreferenceImpl.getSomeStringValue(LoginActivity.this, Constants.USER_ID).equals(Constants.NOT_FOUND)){
            Intent i = new Intent(mContext, HomePageActivity.class);
            startActivity(i);
        }


        mAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();
        loginlayoutBinding.googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        loginlayoutBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginWithEmailPwd();
                firebaseMethods.loginWithEmailPwd(loginlayoutBinding.emailEditText.getText().toString(),loginlayoutBinding.passwordEditText.getText().toString());
                finish();
            }
        });
        loginlayoutBinding.textsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToRegisterActivity();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void updateUI(final FirebaseUser user){
        if(user!=null){
            userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());
            userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(user.isEmailVerified()) {
                        if (!dataSnapshot.hasChild("name")) {
                            SharedPreferenceImpl.setSomeStringValue(mContext, Constants.USER_ID, user.getUid());
                            SharedPreferenceImpl.setSomeStringValue(mContext, Constants.EMAIL, user.getEmail());

                            startActivity(new Intent(mContext, DetailsActivity.class));
                            finish();
                        } else {
                            Intent i = new Intent(mContext, HomePageActivity.class);
                            startActivity(i);
                        }
                    }
                    else {
                        Toast.makeText(mContext, "Your email is not Verified", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    private void sendToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
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




    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseMethods.signinWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed"+ e.getStatusCode(), e);
                // ...
            }
        }
    }


<<<<<<< HEAD



=======
                    }
                });
    }
>>>>>>> 2ffb7195bd915b9c8ab69a61826e1fc23d227b08
}




