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

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.databinding.ProfileActivityBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private ProfileActivityBinding profileActivityBinding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private boolean somethingChanged = false;
    String name ,course,college,semester;
    private ArrayAdapter<CharSequence> courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileActivityBinding = DataBindingUtil.setContentView(this, R.layout.profile_activity);
        clearFocus();

        mAuth = FirebaseAuth.getInstance();
        courseAdapter= ArrayAdapter.createFromResource(this,R.array.courses,android.R.layout.simple_spinner_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileActivityBinding.courseSpinner.setAdapter(courseAdapter);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());

        profileActivityBinding.doneButton.setOnClickListener(new View.OnClickListener() {

                                                                 @Override
                                                                 public void onClick(View v) {
                                                                     if (somethingChanged) {
                                                                         applyDetailsChanges();
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
                    profileActivityBinding.courseEdit.setEnabled(true);
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
            }
        });
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String course = dataSnapshot.child("course").getValue().toString();
                String semester = dataSnapshot.child("semester").getValue().toString();
                String college = dataSnapshot.child("college").getValue().toString();
//                String enrolledCourse = dataSnapshot.child("enrolledCourse").getValue().toString();

                profileActivityBinding.nameEditText.setText(name);
                profileActivityBinding.courseEdit.setText(course);
                profileActivityBinding.semesterEdit.setText(semester);
                profileActivityBinding.collegeEdit.setText(college);
//                enrolledCourseEditText.setText(enrolledCourse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void applyDetailsChanges() {
         name = profileActivityBinding.nameEditText.getText().toString();
         semester = profileActivityBinding.semesterEdit.getText().toString();
         college = profileActivityBinding.collegeEdit.getText().toString();
         if(profileActivityBinding.courseSpinner.getSelectedItemPosition()==5){
             course=profileActivityBinding.courseEdit.getText().toString();
         }
         else {
             course = profileActivityBinding.courseSpinner.getSelectedItem().toString();
         }
//                String enrolledCourse = profileActivityBinding.nameEditText.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(course) && !TextUtils.isEmpty(semester)
                && !TextUtils.isEmpty(college)/* && !TextUtils.isEmpty(enrolledCourse)*/) {

            mDatabaseReference.child("name").setValue(name);
            mDatabaseReference.child("course").setValue(course);
            mDatabaseReference.child("semester").setValue(semester);
            mDatabaseReference.child("college").setValue(college);
//                    mDatabaseReference.child("enrolledCourse").setValue(enrolledCourse);

        }

    }
    private void saveChangesDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Save Changes?");
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
}
