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

public class ProfileActivity extends AppCompatActivity {
    private ProfileActivityBinding profileActivityBinding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private boolean somethingChanged = false;
    String name ,course,college,semester;
    private UserPOJO userPOJO;
    private ArrayAdapter<CharSequence> courseAdapter;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileActivityBinding = DataBindingUtil.setContentView(this, R.layout.profile_activity);
        clearFocus();
        mAuth = FirebaseAuth.getInstance();
        courseAdapter= ArrayAdapter.createFromResource(this,R.array.courses,android.R.layout.simple_spinner_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileActivityBinding.courseSpinner.setEnabled(false);
        profileActivityBinding.courseSpinner.setAdapter(courseAdapter);
        getUserPojo();

        profileActivityBinding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==5) {
                    profileActivityBinding.courseEdit.setVisibility(View.VISIBLE);
                }
                else {
                    profileActivityBinding.courseEdit.setVisibility(View.GONE);

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
                                                                         Toast.makeText(ProfileActivity.this, "Fields must not be empty", Toast.LENGTH_SHORT).show();
                                                                     }
                                                                     finish();
                                                                 }

                                                             }


        );
        profileActivityBinding.courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==5){
                    profileActivityBinding.courseEdit.setVisibility(View.VISIBLE);

                    profileActivityBinding.courseEdit.setEnabled(true);
                }
                else {
                    profileActivityBinding.courseEdit.setVisibility(View.GONE);
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
            }
        });


    }

    private void initStrings() {
        name=userPOJO.getName();
        course=userPOJO.getCourse();
        semester=userPOJO.getSemester();
        college=userPOJO.getCollege();
        courseId=userPOJO.getCourseId();
        settingUpComponents();
    }

    private void settingUpComponents(){
        profileActivityBinding.nameEditText.setText(name);
                profileActivityBinding.semesterEdit.setText(semester);
                profileActivityBinding.collegeEdit.setText(college);
        profileActivityBinding.courseSpinner.setSelection(courseId);



        if(courseId==5){
                    profileActivityBinding.courseEdit.setEnabled(true);
                    profileActivityBinding.courseEdit.setVisibility(View.VISIBLE);
                    profileActivityBinding.courseEdit.setText(course);
                }
                else {
                    profileActivityBinding.courseEdit.setVisibility(View.GONE);
                }
    }

    private void applyDetailsChanges() {
         name = profileActivityBinding.nameEditText.getText().toString();
         semester = profileActivityBinding.semesterEdit.getText().toString();
         college = profileActivityBinding.collegeEdit.getText().toString();
         courseId=profileActivityBinding.courseSpinner.getSelectedItemPosition();
         if(courseId==5){
             course=profileActivityBinding.courseEdit.getText().toString();
         }
         else {
             course = profileActivityBinding.courseSpinner.getSelectedItem().toString();
         }

////                String enrolledCourse = profileActivityBinding.nameEditText.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(course) && !TextUtils.isEmpty(semester)
                && !TextUtils.isEmpty(college)/* && !TextUtils.isEmpty(enrolledCourse)*/) {

            mDatabaseReference.child(Constants.NAME).setValue(name);
            mDatabaseReference.child(Constants.COURSE).setValue(course);
            mDatabaseReference.child(Constants.SEMESTER).setValue(semester);
            mDatabaseReference.child(Constants.COLLEGE).setValue(college);
            mDatabaseReference.child(Constants.COURSE_ID).setValue(courseId);
            userPOJO.setCollege(college);
            userPOJO.setCourse(course);
            userPOJO.setCourseId(profileActivityBinding.courseSpinner.getSelectedItemPosition());
            userPOJO.setSemester(semester);
            userPOJO.setName(name);
            SharedPreferenceImpl.getInstance().addUserPojo(userPOJO,ProfileActivity.this);
            //          mDatabaseReference.child("enrolledCourse").setValue(enrolledCourse);
            Toast.makeText(this, "jlnvpanpndpa", Toast.LENGTH_SHORT).show();

        }
        else {
        }

    }
    private void saveChangesDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(Constants.SAVE_CHANGES);
        alertDialogBuilder

                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                applyDetailsChanges();
                                dialog.cancel();
                                finish();
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
        super.onBackPressed();
        saveChangesDialog();
    }
}
