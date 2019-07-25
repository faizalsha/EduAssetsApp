package com.example.krishbhatia.eduassets.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.krishbhatia.eduassets.POJO.SubjectBasicInfoPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.CourseSubjectsAdapter;
import com.example.krishbhatia.eduassets.ui.adapter.SubjectAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseSubjectsActivity extends AppCompatActivity {

    private ArrayList<SubjectBasicInfoPOJO> subjectList;
    private Context mContext;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_subjects);
        mContext = CourseSubjectsActivity.this;

        recyclerView = findViewById(R.id.subjectsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String course = getIntent().getStringExtra("course");
        subjectList = new ArrayList<>();
        DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference().child("MyRoot/subjectBasicInfo");
        Query query = subjectRef.orderByChild("courseName").equalTo(course);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SubjectBasicInfoPOJO subject;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    subject = snapshot.getValue(SubjectBasicInfoPOJO.class);
                    subjectList.add(subject);
                }
                SubjectAdapter adapter = new SubjectAdapter(mContext, subjectList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
