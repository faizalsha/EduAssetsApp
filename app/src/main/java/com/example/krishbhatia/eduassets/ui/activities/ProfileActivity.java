package com.example.krishbhatia.eduassets.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileActivityBinding = DataBindingUtil.setContentView(this, R.layout.profile_activity);
        clearFocus();

        mAuth = FirebaseAuth.getInstance();

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
                profileActivityBinding.courseEdit.setEnabled(true);
                profileActivityBinding.semesterEdit.setEnabled(true);
                profileActivityBinding.collegeEdit.setEnabled(true);
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
        String name = profileActivityBinding.nameEditText.getText().toString();
        String course = profileActivityBinding.courseEdit.getText().toString();
        String semester = profileActivityBinding.semesterEdit.getText().toString();
        String college = profileActivityBinding.collegeEdit.getText().toString();
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
