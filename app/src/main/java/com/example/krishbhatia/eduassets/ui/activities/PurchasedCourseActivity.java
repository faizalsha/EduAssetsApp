package com.example.krishbhatia.eduassets.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishbhatia.eduassets.POJO.Course;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.PurchasedCourseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PurchasedCourseActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_course);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("shadab/user/purchased_course");
        arrayList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String s = snapshot.getValue(String.class);
                    arrayList.add(s);
                }

                recyclerView = findViewById(R.id.purchased_course_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(PurchasedCourseActivity.this));
                PurchasedCourseAdapter adapter = new PurchasedCourseAdapter(PurchasedCourseActivity.this, arrayList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
