package com.example.krishbhatia.eduassets.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.databinding.ProfileActivityBinding;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ProfileActivityBinding profileActivityBinding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private boolean somethingChanged = false;
    String name ,course,college,semester;
    private String university;
    private UserPOJO userPOJO;
    private ArrayAdapter<String> courseAdapter, universityAdapter;
    private int courseId, universityCode;
    private ArrayList<String> courseList;
    private ArrayList<String> universityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileActivityBinding = DataBindingUtil.setContentView(this, R.layout.profile_activity);
        clearFocus();
        mAuth = FirebaseAuth.getInstance();


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
                    profileActivityBinding.courseSpinner.setAdapter(courseAdapter);


                } else {
                    Toast.makeText(ProfileActivity.this, "Course Data doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                courseList.add("Something is wrong");
            }
        });

        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
                    profileActivityBinding.universitySpinner.setAdapter(universityAdapter);


                } else {
                    Toast.makeText(ProfileActivity.this, "Course Data doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                courseList.add("Something is wrong");
            }
        });
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        profileActivityBinding.courseSpinner.setEnabled(false);

        profileActivityBinding.universitySpinner.setEnabled(false);
        getUserPojo();

//        profileActivityBinding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //TODO: static value
//                if(position==5) {
//                    profileActivityBinding.courseEdit.setVisibility(View.VISIBLE);
//                }
//                else {
//                    profileActivityBinding.courseEdit.setVisibility(View.GONE);
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        profileActivityBinding.universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //TODO: static value
//                if(position==3) {
//                    profileActivityBinding.universityEdit.setVisibility(View.VISIBLE);
//                }
//                else {
//                    profileActivityBinding.universityEdit.setVisibility(View.GONE);
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(mAuth.getUid());

        profileActivityBinding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (somethingChanged) {
                    applyDetailsChanges();
                    getUserPojo();
                } else {
                    Toast.makeText(ProfileActivity.this, Constants.EMPTY_FIELD_TOAST, Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }


        );
        profileActivityBinding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==5){
                    profileActivityBinding.courseEditParent.setVisibility(View.VISIBLE);

                    profileActivityBinding.courseEditParent.setEnabled(true);
                }
                else {
                    profileActivityBinding.courseEditParent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profileActivityBinding.universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==universityList.size()){
                    profileActivityBinding.universityEditParent.setVisibility(View.VISIBLE);

                    profileActivityBinding.universityEditParent.setEnabled(true);
                }
                else {
                    profileActivityBinding.universityEditParent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        profileActivityBinding.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (somethingChanged) {
                    saveChangesDialog();
                } else {
                    finish();
                }
            }
        });
        profileActivityBinding.editDetailsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                somethingChanged = true;
                profileActivityBinding.nameEditText.setEnabled(true);
                profileActivityBinding.semesterEdit.setEnabled(true);
                profileActivityBinding.collegeEdit.setEnabled(true);
                profileActivityBinding.courseSpinner.setEnabled(true);
                profileActivityBinding.courseSpinner.setEnabled(true);
                profileActivityBinding.universitySpinner.setEnabled(true);
            }
        });


    }

    private void initStrings() {
        name=userPOJO.getName();
        course=userPOJO.getCourse();
        university = userPOJO.getUniversity();
        semester=userPOJO.getSemester();
        college=userPOJO.getCollege();
        courseId=userPOJO.getCourseId();
        universityCode = userPOJO.getUniversityCode();
        settingUpComponents();
    }

    private void settingUpComponents(){
        profileActivityBinding.nameEditText.setText(name);
                profileActivityBinding.semesterEdit.setText(semester);
                profileActivityBinding.collegeEdit.setText(college);
        profileActivityBinding.courseSpinner.setSelection(courseId);
        profileActivityBinding.universitySpinner.setSelection(universityCode);
        //TODO: static value
        if(courseId==courseList.size()){
                    profileActivityBinding.courseEdit.setEnabled(true);
                    profileActivityBinding.courseEdit.setVisibility(View.VISIBLE);
                    profileActivityBinding.courseEdit.setText(course);
                }
                else {
                    profileActivityBinding.courseEdit.setVisibility(View.GONE);
                }
        if(universityCode==universityList.size()){
            profileActivityBinding.universityEdit.setEnabled(true);
            profileActivityBinding.universityEdit.setVisibility(View.VISIBLE);
            profileActivityBinding.universityEdit.setText(university);
        }
        else {
            profileActivityBinding.universityEdit.setVisibility(View.GONE);
        }
    }

    private void applyDetailsChanges() {
         name = profileActivityBinding.nameEditText.getText().toString();
         semester = profileActivityBinding.semesterEdit.getText().toString();
         college = profileActivityBinding.collegeEdit.getText().toString();
         courseId=profileActivityBinding.courseSpinner.getSelectedItemPosition();
         universityCode = profileActivityBinding.universitySpinner.getSelectedItemPosition();
         //TODO: static value
         if(courseId==courseList.size()){
             course=profileActivityBinding.courseEdit.getText().toString();
         }
         else {
             course = profileActivityBinding.courseSpinner.getSelectedItem().toString();
         }
        if(universityCode==universityList.size()){
            university=profileActivityBinding.universityEdit.getText().toString();
        }
        else {
            university = profileActivityBinding.universitySpinner.getSelectedItem().toString();
        }

////                String enrolledCourse = profileActivityBinding.nameEditText.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(course) && !TextUtils.isEmpty(university) && !TextUtils.isEmpty(semester)
                && !TextUtils.isEmpty(college)/* && !TextUtils.isEmpty(enrolledCourse)*/) {

            mDatabaseReference.child(Constants.NAME).setValue(name);
            mDatabaseReference.child(Constants.COURSE).setValue(course);
            mDatabaseReference.child(Constants.UNIVERSITY).setValue(university);
            mDatabaseReference.child(Constants.SEMESTER).setValue(semester);
            mDatabaseReference.child(Constants.COLLEGE).setValue(college);
            mDatabaseReference.child(Constants.COURSE_ID).setValue(courseId);
            mDatabaseReference.child(Constants.UNIVERSITY_CODE).setValue(universityCode);
            userPOJO.setCollege(college);
            userPOJO.setCourse(course);
            userPOJO.setUniversity(university);
            userPOJO.setCourseId(profileActivityBinding.courseSpinner.getSelectedItemPosition());
            userPOJO.setUniversityCode(profileActivityBinding.universitySpinner.getSelectedItemPosition());
            userPOJO.setSemester(semester);
            userPOJO.setName(name);
            SharedPreferenceImpl.getInstance().addUserPojo(userPOJO,ProfileActivity.this);
            //          mDatabaseReference.child("enrolledCourse").setValue(enrolledCourse);


        }
        else {
        }

    }
    private void saveChangesDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(Constants.SAVE_CHANGES);
        alertDialogBuilder

                .setCancelable(false)
                .setPositiveButton(Constants.YES,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                applyDetailsChanges();
                                dialog.cancel();
                                finish();
                            }
                        })

                .setNegativeButton(Constants.NO, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    dialog.cancel();
                    finish();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    private void clearFocus() {
        View current = getCurrentFocus();
        if (current != null) {
            current.clearFocus();
        }

    }
    private void getUserPojo() {
        Gson gson = new Gson();
        userPOJO = gson.fromJson(SharedPreferenceImpl.getInstance().get(Constants.USERPOJO, this), UserPOJO.class);
        initStrings();
    }

    @Override
    public void onBackPressed() {

        if (somethingChanged){
        saveChangesDialog();
        }else {
            finish();
        }
    }
}
