package com.example.krishbhatia.eduassets.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.krishbhatia.eduassets.POJO.ResourcePOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.MainRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Toolbar toolbar = findViewById(R.id.course_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        List<ResourcePOJO> resourcePOJOList = new ArrayList<>();

        resourcePOJOList.add(new ResourcePOJO("pdf", "Foundatation of Computer Science", "url", "OOP using C++", "211"));
        resourcePOJOList.add(new ResourcePOJO("video", "Computer Science", "url", "OOP using C++", "211"));

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(this, resourcePOJOList);
        recyclerView.setAdapter(adapter);
    }
}
