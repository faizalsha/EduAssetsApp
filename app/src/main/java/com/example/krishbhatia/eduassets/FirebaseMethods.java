package com.example.krishbhatia.eduassets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.ui.activities.HomePageActivity;
import com.example.krishbhatia.eduassets.utils.NetworkUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseMethods {
    private Context mContext;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "FirebaseMethods";
    public FirebaseMethods(Context mContext) {
        this.mContext = mContext;
        this.mAuth=FirebaseAuth.getInstance();
    }

    public void setupFirebaseAuth() {

        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
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
    public void signinWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferenceImpl.setSomeStringValue(mContext,Constants.USER_ID,user.getUid());
                            SharedPreferenceImpl.setSomeStringValue(mContext,Constants.EMAIL,user.getEmail());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(mContext, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void loginWithEmailPwd(  String email,String password ) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            if (NetworkUtils.isConnectedToInternert(mContext)){
                Toast.makeText(mContext, "Logging In...", Toast.LENGTH_SHORT).show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(mContext, "Signed In", Toast.LENGTH_SHORT).show();
//                            updateUI(mAuth.getCurrentUser());
//                            SharedPreferenceImpl.getSharedPreferences(mContext).edit().putString(Constants.USER_ID,task.getResult().getUser().getUid());
                                SharedPreferenceImpl.setSomeStringValue(mContext, Constants.USER_ID, task.getResult().getUser().getUid());
                                SharedPreferenceImpl.setSomeStringValue(mContext, Constants.EMAIL, task.getResult().getUser().getEmail());
                                mContext.startActivity(new Intent(mContext, HomePageActivity.class));

                            } else {
                                Toast.makeText(mContext, "Your email is not verified", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                                Toast.makeText(mContext, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                    }
                });

            }
        }else {
            Toast.makeText(mContext, "Error: Empty Fields", Toast.LENGTH_SHORT).show();
        }

    }


}
