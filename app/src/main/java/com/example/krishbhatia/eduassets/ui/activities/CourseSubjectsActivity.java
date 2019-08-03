package com.example.krishbhatia.eduassets.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.POJO.SubjectBasicInfoPOJO;
import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.CourseSubjectsAdapter;
import com.example.krishbhatia.eduassets.ui.adapter.SubjectAdapter;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CourseSubjectsActivity extends AppCompatActivity {

    private static final String TAG = "CourseSubjectsActivity";
    private ArrayList<SubjectBasicInfoPOJO> subjectList;
    private Context mContext;
    RecyclerView recyclerView;
    private ProgressBar progressBar;
    private UserPOJO userPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_subjects);
        mContext = CourseSubjectsActivity.this;
        String course = getIntent().getStringExtra(Constants.COURSE);
        int courseId = getIntent().getIntExtra(Constants.COURSE_ID, 0);
//        Log.d(TAG, "onCreate:courseid : "+ courseId);
        progressBar = findViewById(R.id.subjectProgressBar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle(course);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        recyclerView = findViewById(R.id.subjectsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        subjectList = new ArrayList<>();
        DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference().child(Constants.MY_ROOT).child(Constants.SUBJECT_BASIC_INFO);
        Query query = subjectRef.orderByChild(Constants.COURSE_ID).equalTo(courseId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    SubjectBasicInfoPOJO subject;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        subject = snapshot.getValue(SubjectBasicInfoPOJO.class);
                        subjectList.add(subject);
                    }
                    SubjectAdapter adapter = new SubjectAdapter(mContext, subjectList);
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(mContext, "Data Coming Soon", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
