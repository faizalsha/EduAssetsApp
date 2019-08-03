package com.example.krishbhatia.eduassets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.example.krishbhatia.eduassets.ui.activities.DetailsActivity;
import com.example.krishbhatia.eduassets.ui.activities.HomePageActivity;
import com.example.krishbhatia.eduassets.ui.activities.LoginActivity;
import com.example.krishbhatia.eduassets.ui.activities.SplashScreenActivity;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.logging.SocketHandler;

public class FirebaseMethods {
    private Context mContext;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "FirebaseMethods";
    private ProgressBar progressBar;
    private UserPOJO userPOJO;

    public FirebaseMethods(Context mContext) {
        this.mContext = mContext;
        this.mAuth = FirebaseAuth.getInstance();
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
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        progressBar = ((Activity) mContext).findViewById(R.id.loginProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
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

    public void loginWithEmailPwd(String email, String password) {
        progressBar = ((Activity) mContext).findViewById(R.id.loginProgressBar);

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            if (NetworkUtils.isConnectedToInternert(mContext)) {

                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(mContext, "Logging In...", Toast.LENGTH_SHORT).show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(mContext, "Signed In", Toast.LENGTH_SHORT).show();
                                updateUI(mAuth.getCurrentUser());
//                                mContext.startActivity(new Intent(mContext, HomePageActivity.class));
//                                ((Activity)mContext).finish();

                            } else {
                                Toast.makeText(mContext, "Your email is not verified", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

            } else {
                Toast.makeText(mContext, Constants.CHECK_YOUR_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
            }
        } else {
            Button button=((Activity) mContext).findViewById(R.id.loginButton);
            button.setClickable(true);
            Toast.makeText(mContext, Constants.EMPTY_FIELD_TOAST, Toast.LENGTH_SHORT).show();
        }

    }


    private void updateUI(final FirebaseUser user) {
        if (user != null) {
            DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(mAuth.getUid());
            userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (user.isEmailVerified()) {

                        Log.d(TAG, "onDataChange: " + dataSnapshot);

                            if (!dataSnapshot.hasChild(Constants.NAME)) {
                               mContext.startActivity(new Intent(mContext, DetailsActivity.class));
                                ((Activity) mContext).finish();

                            } else {
                                UserPOJO userPOJO = dataSnapshot.getValue(UserPOJO.class);
                                SharedPreferenceImpl.getInstance().addUserPojo(userPOJO, mContext);
                                Log.d(TAG, "onDataChange: valeeu" + userPOJO.getEmail());
                                Intent intent = new Intent(mContext, HomePageActivity.class);
                                mContext.startActivity(intent);
                                ((Activity)mContext).finish();
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

    public void getUserDetails() {
        if (mAuth.getCurrentUser() != null){
            Log.d(TAG, "getUserDetails: name exist"+SharedPreferenceImpl.getInstance().get(Constants.NAME, mContext));
            if (SharedPreferenceImpl.getInstance().contains(Constants.NAME,mContext)){
                mContext.startActivity(new Intent(mContext, HomePageActivity.class));
                ((Activity)mContext).finish();
            }else {
                updateUI(mAuth.getCurrentUser());
            }
        }else {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
            ((Activity)mContext).finish();
        }
    }

//    private void getDataBase() {
//        Toast.makeText(mContext, "getting", Toast.LENGTH_SHORT).show();
////        if (SharedPreferenceImpl.getInstance().contains("name",mContext)) {
////            getDataFromSharedPreference();
////            mContext.startActivity(new Intent(mContext, HomePageActivity.class));
////            ((Activity)mContext).finish();
////        }
//
//        updateUI(mAuth.getCurrentUser());
//
//
//    }

//    private void getDataFromSharedPreference() {
//        Gson gson = new Gson();
//        userPOJO = gson.fromJson(SharedPreferenceImpl.getInstance().get(Constants.USERPOJO, mContext), UserPOJO.class);
//
//    }

//    private void getDataFromFirebase() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(mAuth.getUid());
//        Log.d(TAG, "getDataFromFirebase: getDataFromFirebase");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                userPOJO=dataSnapshot.getValue(UserPOJO.class);
//                SharedPreferenceImpl.getInstance().addUserPojo(userPOJO,mContext);
//                mContext.startActivity(new Intent(mContext, HomePageActivity.class));
//                ((Activity)mContext).finish();
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(mContext, "Error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
