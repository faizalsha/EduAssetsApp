package com.example.krishbhatia.eduassets.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.example.krishbhatia.eduassets.POJO.SubjectBasicInfoPOJO;
import com.example.krishbhatia.eduassets.R;

import com.example.krishbhatia.eduassets.ui.adapter.SubjectAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MySubjectsFragment extends Fragment {
    private static final String TAG = "MySubjectsFragment";
    private static final String SELECTED_COURSE = "BBA";

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<SubjectBasicInfoPOJO> subjectList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_subjects, container, false);
        progressBar = view.findViewById(R.id.mySubjectProgressBar);
        recyclerView = view.findViewById(R.id.mySubject_fragment_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        subjectList = new ArrayList<>();

        DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference().child("MyRoot/subjectBasicInfo");
        Query selectedCourseSubjectsQuery = subjectRef.orderByChild("courseName").equalTo(SELECTED_COURSE);
        selectedCourseSubjectsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SubjectBasicInfoPOJO subject;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    subject = snapshot.getValue(SubjectBasicInfoPOJO.class);
                    subjectList.add(subject);
                }
                SubjectAdapter adapter = new SubjectAdapter(getContext(), subjectList);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }
}
