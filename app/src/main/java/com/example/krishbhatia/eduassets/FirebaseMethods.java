package com.example.krishbhatia.eduassets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.ui.activities.DetailsActivity;
import com.example.krishbhatia.eduassets.ui.activities.HomePageActivity;
import com.example.krishbhatia.eduassets.ui.activities.LoginActivity;
import com.example.krishbhatia.eduassets.utils.NetworkUtils;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;
import com.example.krishbhatia.eduassets.utils.SharedPreferencesService;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseMethods {
    private Context mContext;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "FirebaseMethods";
    private ProgressBar progressBar;
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
                    Toast.makeText(mContext, "UserPOJO Logged in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Not Logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    public void signinWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        progressBar = ((Activity)mContext).findViewById(R.id.loginProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           SharedPreferenceImpl.getInstance().save(Constants.USER_ID,user.getUid(),mContext);
                            SharedPreferenceImpl.getInstance().save(Constants.EMAIL,user.getEmail(),mContext);
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(mContext, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
    public void loginWithEmailPwd(String email, String password ) {
        progressBar = ((Activity)mContext).findViewById(R.id.loginProgressBar);

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            if (NetworkUtils.isConnectedToInternert(mContext)){

                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(mContext, "Logging In...", Toast.LENGTH_SHORT).show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(mContext, "Signed In", Toast.LENGTH_SHORT).show();
                            SharedPreferenceImpl.getInstance().save(Constants.USER_ID,mAuth.getUid(),mContext);
                            SharedPreferenceImpl.getInstance().save(Constants.EMAIL,mAuth.getCurrentUser().getEmail(),mContext);
                                updateUI(mAuth.getCurrentUser());
//                                mContext.startActivity(new Intent(mContext, HomePageActivity.class));
//                                ((Activity)mContext).finish();

                            } else {
                                Toast.makeText(mContext, "Your email is not verified", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                                Toast.makeText(mContext, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        progressBar.setVisibility(View.GONE);
                    }
                });

            } else {
                Toast.makeText(mContext, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(mContext, "Error: Empty Fields", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUI(final FirebaseUser user) {
        if (user != null) {
            DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());
            userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (user.isEmailVerified()) {

                        Log.d(TAG, "onDataChange: " + dataSnapshot);
                        if (!dataSnapshot.hasChild("name")) {
//                            SharedPreferenceImpl.setSomeStringValue(mContext, Constants.USER_ID, user.getUid());
//                            SharedPreferenceImpl.setSomeStringValue(mContext, Constants.EMAIL, user.getEmail());

                            mContext.startActivity(new Intent(mContext,DetailsActivity.class));
                            ((Activity)mContext).finish();

                        } else {
                            Intent intent = new Intent(mContext, HomePageActivity.class);
                            mContext.startActivity(intent);
                        }
                    } else {
                        Toast.makeText(mContext, "Your email is not Verified", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


}
