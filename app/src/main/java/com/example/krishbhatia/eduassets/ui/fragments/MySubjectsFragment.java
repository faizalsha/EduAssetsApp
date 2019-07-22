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

import com.example.krishbhatia.eduassets.POJO.CourseBasicDetails;
import com.example.krishbhatia.eduassets.POJO.SubjectBasicDetail;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.CourseAdapter;
import com.example.krishbhatia.eduassets.ui.adapter.SubjectAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MySubjectsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private ArrayList<SubjectBasicDetail> subjectList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_subjects, container, false);

        recyclerView = view.findViewById(R.id.mySubject_fragment_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        subjectList = new ArrayList<>();

        DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference().child("subjectDB/BBA/subjects");
        subjectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SubjectBasicDetail subject;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    subject = snapshot.getValue(SubjectBasicDetail.class);
                    subjectList.add(subject);
                }
                SubjectAdapter adapter = new SubjectAdapter(getContext(), subjectList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
