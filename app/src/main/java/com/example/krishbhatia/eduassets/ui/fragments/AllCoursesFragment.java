package com.example.krishbhatia.eduassets.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishbhatia.eduassets.pojo.Course;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.CourseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AllCoursesFragment extends Fragment {

    private static final String TAG = "AllCoursesFragment";

    private RecyclerView recyclerView;
    private List<Course> courseList;

    public AllCoursesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_courses, container, false);

        recyclerView = view.findViewById(R.id.allCourse_fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false));
        courseList = new ArrayList<>();



        //creating recyclerview adapter
        CourseAdapter adapter = new CourseAdapter(getContext(), courseList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
        final DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference().child("shadab/courses");

        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Course course;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    course = snapshot.getValue(Course.class);
                    courseList.add(course);
                }
                CourseAdapter adapter = new CourseAdapter(getContext(), courseList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
