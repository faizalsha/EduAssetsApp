package com.example.krishbhatia.eduassets.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishbhatia.eduassets.POJO.Course;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.activities.CourseActivity;
import com.example.krishbhatia.eduassets.ui.adapter.CourseAdapter;
import com.google.android.exoplayer2.C;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AllCoursesFragment extends Fragment {
    private static final String TAG = "AllCoursesFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private List<Course> courseList;

    private String mParam1;
    private String mParam2;


    public AllCoursesFragment() {
    }


    public static AllCoursesFragment newInstance(String param1, String param2) {
        AllCoursesFragment fragment = new AllCoursesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TextView textVi=container.findViewById(R.id.allCoursesText);
//        textVi.setText("all courses");
        View view = inflater.inflate(R.layout.fragment_all_courses, container, false);

        recyclerView = view.findViewById(R.id.allCoursesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false));


        courseList = new ArrayList<>();

        final DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference().child("shadab/courses");


        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Course course;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    course = snapshot.getValue(Course.class);
                    Log.d(TAG, "onDataChange: snapshot is " + course.getTitle());
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

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
////            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


}
