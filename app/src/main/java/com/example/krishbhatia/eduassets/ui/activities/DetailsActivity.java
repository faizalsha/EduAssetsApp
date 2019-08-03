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

import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.databinding.DetailsActivityBinding;
import com.example.krishbhatia.eduassets.utils.NetworkUtils;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DetailsActivity extends AppCompatActivity {


    private DetailsActivityBinding detailsActivityBinding;
    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private String course, name, college, semester;
    private String university;
    private FirebaseAuth mAuth;
    private ArrayAdapter<CharSequence> courseAdapter;

    private ArrayAdapter<CharSequence> universityAdapter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = DetailsActivity.this;
        detailsActivityBinding = DataBindingUtil.setContentView(this, R.layout.details_activity);

        progressBar = findViewById(R.id.detailsProgressBar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        courseAdapter = ArrayAdapter.createFromResource(this, R.array.courses, android.R.layout.simple_spinner_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        universityAdapter = ArrayAdapter.createFromResource(this, R.array.university, android.R.layout.simple_spinner_item);
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detailsActivityBinding.courseSpinner.setAdapter(courseAdapter);
        detailsActivityBinding.universitySpinner.setAdapter(universityAdapter);
        detailsActivityBinding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = detailsActivityBinding.nameEditText.getText().toString();
                college = detailsActivityBinding.collegeEdit.getText().toString();
                semester = detailsActivityBinding.semesterEdit.getText().toString();
                if (detailsActivityBinding.courseSpinner.getSelectedItemPosition() == 5) {
                    course = detailsActivityBinding.courseEdit.getText().toString();
                } else {
                    course = detailsActivityBinding.courseSpinner.getSelectedItem().toString();
                }
                if (detailsActivityBinding.universitySpinner.getSelectedItemPosition() == 3) {
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

        detailsActivityBinding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 5) {
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
                if (position == 3) {
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
