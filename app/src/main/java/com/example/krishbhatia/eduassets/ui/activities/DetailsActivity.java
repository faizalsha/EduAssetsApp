package com.example.krishbhatia.eduassets.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.POJO.User;
import com.example.krishbhatia.eduassets.Utils.NetworkUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DetailsActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText courseEditText;
    private EditText collegeEditText;
    private EditText semesterEditText;

    private Button doneButton;

    private Context mContext;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mContext = DetailsActivity.this;

        nameEditText = findViewById(R.id.nameEditText);
        courseEditText = findViewById(R.id.courseEditText);
        collegeEditText = findViewById(R.id.collegeEditText);
        semesterEditText = findViewById(R.id.semEditText);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String course = courseEditText.getText().toString();
                String college = collegeEditText.getText().toString();
                String semester = semesterEditText.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(course) && !TextUtils.isEmpty(college) && !TextUtils.isEmpty(semester)){

                    if (NetworkUtils.isConnectedToInternert(mContext)){

                        User user = new User(name, course, college, semester, mAuth.getUid(), mAuth.getCurrentUser().getEmail(), null);

                        mDatabaseReference.child("users").child(mAuth.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DetailsActivity.this, "Details Saved Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DetailsActivity.this, HomePageActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DetailsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(mContext, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsActivity.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
