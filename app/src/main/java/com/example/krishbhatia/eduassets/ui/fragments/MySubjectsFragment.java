package com.example.krishbhatia.eduassets.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SafeBrowsingResponse;
import android.widget.ProgressBar;


import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.POJO.SubjectBasicInfoPOJO;
import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.example.krishbhatia.eduassets.R;

import com.example.krishbhatia.eduassets.ui.adapter.SubjectAdapter;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MySubjectsFragment extends Fragment {
    private static final String TAG = "MySubjectsFragment";
    //TODO: get selected subject from spinner
    private String selectedCourse;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<SubjectBasicInfoPOJO> subjectList;
    private boolean isListenerAttached = false;
    Query selectedCourseSubjectsQuery;
    private UserPOJO userPOJO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_subjects, container, false);
        progressBar = view.findViewById(R.id.mySubjectProgressBar);
        recyclerView = view.findViewById(R.id.mySubject_fragment_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        subjectList = new ArrayList<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        getSubjectDatabase();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSubjectDatabase();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        if (isListenerAttached){
            selectedCourseSubjectsQuery.removeEventListener(valueEventListener);
            isListenerAttached = false;
        }
    }

    public void getSubjectDatabase(){
        getDatabase();
        DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference().child(Constants.MY_ROOT).child(Constants.SUBJECT_BASIC_INFO);
        selectedCourseSubjectsQuery = subjectRef.orderByChild(Constants.COURSE_ID).equalTo(userPOJO.getCourseId());
        if (!isListenerAttached) {
            Log.d(TAG, "getSubjectDatabase: Listener Attached");
            selectedCourseSubjectsQuery.addValueEventListener(valueEventListener);
            isListenerAttached = true;
        }
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.d(TAG, "onDataChange: ");
            SubjectBasicInfoPOJO subject;
            subjectList.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                subject = snapshot.getValue(SubjectBasicInfoPOJO.class);
                subjectList.add(subject);
            }
            SubjectAdapter adapter = new SubjectAdapter(getContext(), subjectList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressBar.setVisibility(View.GONE);
        }
    };

    private void getDatabase() {
        Gson gson = new Gson();
        userPOJO = gson.fromJson(SharedPreferenceImpl.getInstance().get(Constants.USERPOJO, getContext()), UserPOJO.class);

    }
}
