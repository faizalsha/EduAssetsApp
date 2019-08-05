package com.example.krishbhatia.eduassets.ui.activities;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.FirebaseMethods;
import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.databinding.DetailsActivityBinding;
import com.example.krishbhatia.eduassets.utils.NetworkUtils;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends AppCompatActivity {


    private DetailsActivityBinding detailsActivityBinding;
    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private String course, name, college, semester;
    private String university;
    private FirebaseAuth mAuth;
    private ArrayAdapter<String> courseAdapter;
    private ArrayAdapter<String> universityAdapter;

    private ArrayList<String> courseList;
    private ArrayList<String> universityList;


    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = DetailsActivity.this;
        detailsActivityBinding = DataBindingUtil.setContentView(this, R.layout.details_activity);

        progressBar = findViewById(R.id.detailsProgressBar);
        FirebaseMethods firebaseMethods = new FirebaseMethods(DetailsActivity.this);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        detailsActivityBinding.emailEditText.setText(mAuth.getCurrentUser().getEmail());
//        courseAdapter = ArrayAdapter.createFromResource(this, R.array.courses, android.R.layout.simple_spinner_item);
//        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        courseList = new ArrayList<>();
        courseList.add(Constants.SELECT_COURSE);
        DatabaseReference courseDB = FirebaseDatabase.getInstance().getReference().child(Constants.MY_ROOT).child(Constants.COURSE_BASIC_INFO);
        courseDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String courseName;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        courseName = snapshot.child(Constants.COURSE_NAME).getValue(String.class);
                        courseList.add(courseName);
                    }

                    courseAdapter.addAll(courseList);
                    courseAdapter.add(Constants.OTHER);
                    detailsActivityBinding.courseSpinner.setAdapter(courseAdapter);


                } else {
                    Toast.makeText(mContext, "Course Data doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                courseList.add("Something is wrong");
            }
        });


        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




//        universityAdapter = ArrayAdapter.createFromResource(this, R.array.university, android.R.layout.simple_spinner_item);
//        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        universityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        universityList = new ArrayList<>();
        universityList.add(Constants.SELECT_UNIVERSITY);
        final DatabaseReference universityDB = FirebaseDatabase.getInstance().getReference().child(Constants.MY_ROOT).child(Constants.UNIVERSITY_INFO);
        universityDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String universityName;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        universityName = snapshot.child(Constants.UNIVERSITY_NAME).getValue(String.class);
                        universityList.add(universityName);
                    }

                    universityAdapter.addAll(universityList);
                    universityAdapter.add(Constants.OTHER);
                    detailsActivityBinding.universitySpinner.setAdapter(universityAdapter);


                } else {
                    Toast.makeText(mContext, "Course Data doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                courseList.add("Something is wrong");
            }
        });
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //detailsActivityBinding.courseSpinner.setAdapter(courseAdapter);
        detailsActivityBinding.universitySpinner.setAdapter(universityAdapter);
        detailsActivityBinding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = detailsActivityBinding.nameEditText.getText().toString();
                college = detailsActivityBinding.collegeEdit.getText().toString();
                semester = detailsActivityBinding.semesterEdit.getText().toString();
                if (detailsActivityBinding.courseSpinner.getSelectedItemPosition() == courseList.size()) {
                    course = detailsActivityBinding.courseEdit.getText().toString();
                } else {
                    course = detailsActivityBinding.courseSpinner.getSelectedItem().toString();
                }
                if (detailsActivityBinding.universitySpinner.getSelectedItemPosition() == universityList.size()) {
                    university = detailsActivityBinding.universityEdit.getText().toString();
                } else {
                    university = detailsActivityBinding.universitySpinner.getSelectedItem().toString();
                }


                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(course) && !TextUtils.isEmpty(college) && !TextUtils.isEmpty(semester) && detailsActivityBinding.courseSpinner.getSelectedItemPosition() != 0
                        && !TextUtils.isEmpty(university) && detailsActivityBinding.universitySpinner.getSelectedItemPosition() != 0) {

                    if (NetworkUtils.isConnectedToInternert(mContext)) {
                        progressBar.setVisibility(View.VISIBLE);
//                        final UserPOJO user = new UserPOJO(name, course, college, semester, mAuth.getUid(), mAuth.getCurrentUser().getEmail(), null);
                        final UserPOJO user = new UserPOJO(name, course, college, university, semester, mAuth.getUid(), mAuth.getCurrentUser().getEmail(), detailsActivityBinding.courseSpinner.getSelectedItemPosition(), detailsActivityBinding.universitySpinner.getSelectedItemPosition());

                        mDatabaseReference.child("users").child(mAuth.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Details Saved Successfully", Toast.LENGTH_SHORT).show();
                                SharedPreferenceImpl.getInstance().addUserPojo(user, DetailsActivity.this);
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(mContext, HomePageActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        Toast.makeText(mContext, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please Fill AllCourseActivity Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        detailsActivityBinding.signInWithDifferentAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(DetailsActivity.this, gso);
                mGoogleSignInClient.signOut();
                SharedPreferenceImpl.getInstance().clearAll(DetailsActivity.this);
                startActivity(new Intent(DetailsActivity.this, LoginActivity.class));
                finish();
            }
        });

        detailsActivityBinding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == courseList.size()) {
                    detailsActivityBinding.courseEditParent.setVisibility(View.VISIBLE);
                    detailsActivityBinding.courseEditParent.setEnabled(true);
                } else {
                    detailsActivityBinding.courseEditParent.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        detailsActivityBinding.universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == universityList.size()) {
                    detailsActivityBinding.universityEditParent.setVisibility(View.VISIBLE);
                    detailsActivityBinding.universityEditParent.setEnabled(true);
                } else {
                    detailsActivityBinding.universityEditParent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
