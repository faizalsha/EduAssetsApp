package com.example.krishbhatia.eduassets.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText courseEditText;
    private EditText semesterEditText;
    private EditText collegeEditText;
    private EditText enrolledCourseEditText;
    private Button saveButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        nameEditText = findViewById(R.id.profile_name_edit_text);
        courseEditText = findViewById(R.id.profile_course_edit_text);
        semesterEditText = findViewById(R.id.profile_semester_edit_text);
        collegeEditText = findViewById(R.id.profile_college_edit_text);
        enrolledCourseEditText = findViewById(R.id.profile_enrolled_course_edit_text);

        mAuth = FirebaseAuth.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());

        saveButton = findViewById(R.id.profile_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String course = courseEditText.getText().toString();
                String semester = semesterEditText.getText().toString();
                String college = collegeEditText.getText().toString();
                String enrolledCourse = enrolledCourseEditText.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(course) && !TextUtils.isEmpty(semester)
                            && !TextUtils.isEmpty(college) && !TextUtils.isEmpty(enrolledCourse)){

                    mDatabaseReference.child("name").setValue(name);
                    mDatabaseReference.child("course").setValue(course);
                    mDatabaseReference.child("semester").setValue(semester);
                    mDatabaseReference.child("college").setValue(college);
                    mDatabaseReference.child("enrolledCourse").setValue(enrolledCourse);

                } else{
                    Toast.makeText(ProfileActivity.this, "Fields must not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String course = dataSnapshot.child("course").getValue().toString();
                String semester = dataSnapshot.child("semester").getValue().toString();
                String college = dataSnapshot.child("college").getValue().toString();
                String enrolledCourse = dataSnapshot.child("enrolledCourse").getValue().toString();

                nameEditText.setText(name);
                courseEditText.setText(course);
                semesterEditText.setText(semester);
                collegeEditText.setText(college);
                enrolledCourseEditText.setText(enrolledCourse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
