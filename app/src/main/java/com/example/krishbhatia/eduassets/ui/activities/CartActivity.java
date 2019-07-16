package com.example.krishbhatia.eduassets.ui.activities;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.pojo.Course;
import com.example.krishbhatia.eduassets.ui.adapter.CartAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        final String intentExtraString = getIntent().getStringExtra("course");

        ArrayList<Course> list = new ArrayList<>();

        list.add(new Course(101,"economics", "just read this description",100.00));
        list.add(new Course(103,"business", "Start your own business",200.00));
        list.add(new Course(105,"accounts", "maintain your account",259.00));
        list.add(new Course(107,"law", "follow the law",399.00));
        list.add(new Course(109,"taxation", "pay your tax",129.00));


        CartAdapter adapter = new CartAdapter(CartActivity.this, list);

        RecyclerView recyclerView = findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        recyclerView.setAdapter(adapter);


        Button checkoutButton = findViewById(R.id.checkout_button);


//        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("shadab/user/purchased_course");
//        checkoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child("new_child").setValue(intentExtraString).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(CartActivity.this, "Course Added", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(CartActivity.this, PurchasedCourseActivity.class));
//                        finish();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(CartActivity.this, "Some error occured in checking out", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }
}
