package com.example.krishbhatia.eduassets.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.krishbhatia.eduassets.POJO.SubjectResPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.SectionsRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubjectResourceActivity extends AppCompatActivity {
    // TODO: update value of ENROLLED_COURSE using shared preferences
    private static final String ENROLLED_COURSE = "BBA";

    private static final String TAG = "SubjectResourceActivity";
    private SubjectResPOJO subjectRes;
    private RecyclerView recyclerView;
    private String selectedSubject;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_resource);

        Toolbar toolbar = findViewById(R.id.course_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        selectedSubject = getIntent().getStringExtra("SELECTED_SUBJECT");
        getSupportActionBar().setTitle(selectedSubject);
        progressBar = findViewById(R.id.subjectResourceProgressBar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        recyclerView = findViewById(R.id.main_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //TODO: in firebase store two fields subjectName and course_subject
        DatabaseReference resRef = FirebaseDatabase.getInstance().getReference().child("MyRoot/res").child(ENROLLED_COURSE + "_" + selectedSubject);


        resRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot);
                subjectRes = dataSnapshot.getValue(SubjectResPOJO.class);
                SectionsRecyclerViewAdapter adapter = new SectionsRecyclerViewAdapter(SubjectResourceActivity.this, subjectRes);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SubjectResourceActivity.this, "Query failed", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }
}
