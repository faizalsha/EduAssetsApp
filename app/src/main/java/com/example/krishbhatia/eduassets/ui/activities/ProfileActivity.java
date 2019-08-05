package com.example.krishbhatia.eduassets.ui.activities;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity {
    private ProfileActivityBinding profileActivityBinding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private boolean somethingChanged = false;
    String name ,course,college,semester;
    private String university;
    private UserPOJO userPOJO;
    private ArrayAdapter<CharSequence> courseAdapter, universityAdapter;
    private int courseId, universityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileActivityBinding = DataBindingUtil.setContentView(this, R.layout.profile_activity);
        clearFocus();
        mAuth = FirebaseAuth.getInstance();
        courseAdapter= ArrayAdapter.createFromResource(this,R.array.courses,android.R.layout.simple_spinner_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universityAdapter = ArrayAdapter.createFromResource(this, R.array.university, android.R.layout.simple_spinner_item);
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileActivityBinding.courseSpinner.setEnabled(false);
        profileActivityBinding.courseSpinner.setAdapter(courseAdapter);
        profileActivityBinding.universitySpinner.setEnabled(false);
        profileActivityBinding.universitySpinner.setAdapter(universityAdapter);
//        profileActivityBinding.courseEdit.setEnabled(false);
//        profileActivityBinding.universityEdit.setEnabled(false);
        getUserPojo();

        profileActivityBinding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: static value
                if(somethingChanged) {
                    if (position == 5) {
                        profileActivityBinding.courseEdit.setEnabled(true);
                    } else {
                        profileActivityBinding.courseEdit.setEnabled(false);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profileActivityBinding.universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: static value
                if(somethingChanged) {
                    if (position == 3) {
                        profileActivityBinding.universityEdit.setEnabled(true);
                    } else {
                        profileActivityBinding.universityEdit.setText("");
                        profileActivityBinding.universityEdit.setEnabled(false);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
//
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
        if(courseId==5){
            profileActivityBinding.courseEdit.setText(userPOJO.getCourse());
        }
        if(universityCode==3){
            profileActivityBinding.universityEdit.setText(userPOJO.getUniversity());
        }
        settingUpComponents();
    }

    private void settingUpComponents(){
        profileActivityBinding.nameEditText.setText(name);
                profileActivityBinding.semesterEdit.setText(semester);
                profileActivityBinding.collegeEdit.setText(college);
        profileActivityBinding.courseSpinner.setSelection(courseId);
        profileActivityBinding.universitySpinner.setSelection(universityCode);
        //TODO: static value
//        if(courseId==5){
//                    profileActivityBinding.courseEdit.setEnabled(true);
//                    profileActivityBinding.courseEdit.setVisibility(View.VISIBLE);
//                    profileActivityBinding.courseEdit.setText(course);
//                }
//                else {
//                    profileActivityBinding.courseEdit.setVisibility(View.GONE);
//                }
//        if(universityCode==3){
//            profileActivityBinding.universityEdit.setEnabled(true);
//            profileActivityBinding.universityEdit.setVisibility(View.VISIBLE);
//            profileActivityBinding.universityEdit.setText(university);
//        }
//        else {
//            profileActivityBinding.universityEdit.setVisibility(View.GONE);
//        }
    }

    private void applyDetailsChanges() {
         name = profileActivityBinding.nameEditText.getText().toString();
         semester = profileActivityBinding.semesterEdit.getText().toString();
         college = profileActivityBinding.collegeEdit.getText().toString();
         courseId=profileActivityBinding.courseSpinner.getSelectedItemPosition();
         universityCode = profileActivityBinding.universitySpinner.getSelectedItemPosition();
         //TODO: static value
         if(courseId==5){
             course=profileActivityBinding.courseEdit.getText().toString();
         }
         else {
             course = profileActivityBinding.courseSpinner.getSelectedItem().toString();
         }
        if(universityCode==3){
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
