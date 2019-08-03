package com.example.krishbhatia.eduassets.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.POJO.SubjectResPOJO;
import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.SectionsRecyclerViewAdapter;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class SubjectResourceActivity extends AppCompatActivity {

    private static final String TAG = "SubjectResourceActivity";
    private SubjectResPOJO subjectRes;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private UserPOJO userPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_resource);

        Toolbar toolbar = findViewById(R.id.course_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDatabase();
        // TODO: update value of course using shared preferences
        String course = userPOJO.getCourse();
        String selectedSubject = getIntent().getStringExtra(Constants.SELECTED_SUBJECT);

        getSupportActionBar().setTitle(selectedSubject);

        progressBar = findViewById(R.id.subjectResourceProgressBar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        recyclerView = findViewById(R.id.main_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        selectedSubject = selectedSubject.replaceAll(" ", "_");

        DatabaseReference resRef = FirebaseDatabase.getInstance().getReference().child("MyRoot/res").child(course + "_" + selectedSubject);
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Constants.MY_ROOT).child(Constants.RES);
//        Query query = dbRef.orderByChild(Constants.SUBJECT_CODE).equalTo(subjectCode);

        resRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    subjectRes = dataSnapshot.getValue(SubjectResPOJO.class);
                    SectionsRecyclerViewAdapter adapter = new SectionsRecyclerViewAdapter(SubjectResourceActivity.this, subjectRes);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(SubjectResourceActivity.this, "Resources Coming Soon...", Toast.LENGTH_SHORT).show();
                }
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDatabase() {
        Gson gson = new Gson();
        userPOJO = gson.fromJson(SharedPreferenceImpl.getInstance().get(Constants.USERPOJO, this), UserPOJO.class);

    }
}
