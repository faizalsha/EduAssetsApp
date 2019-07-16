package com.example.krishbhatia.eduassets.ui.activities;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        TextView textView = findViewById(R.id.cart_product_title_view);
        final String extraString = getIntent().getStringExtra("course");
        textView.setText(extraString);

        Button checkoutButton = findViewById(R.id.checkout_button);


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("shadab/user/purchased_course");
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("new_child").setValue(extraString).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CartActivity.this, "Course Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CartActivity.this, PurchasedCourseActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CartActivity.this, "Some error occured in checking out", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
