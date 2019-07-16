package com.example.krishbhatia.eduassets.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.krishbhatia.eduassets.POJO.Topic;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.MainRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    private String clickedCourse;
    private ArrayList<Topic> topicList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Toolbar toolbar = findViewById(R.id.course_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        topicList = new ArrayList<>();
        clickedCourse = getIntent().getStringExtra("course");
        DatabaseReference resRef = FirebaseDatabase.getInstance().getReference().child("shadab/res/" + clickedCourse);

        recyclerView = findViewById(R.id.main_recycler_view);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Topic topic = snapshot.getValue(Topic.class);
                    topicList.add(topic);
                }
                MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(CourseActivity.this, topicList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
