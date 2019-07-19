package com.example.krishbhatia.eduassets.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.krishbhatia.eduassets.POJO.CoursePOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.CartAdapter;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private  RecyclerView coursesCheckoutCart;
    private  CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


//        final String intentExtraString = getIntent().getStringExtra("course");

        ArrayList<CoursePOJO> list = new ArrayList<>();

        list.add(new CoursePOJO(101,"economics", "just read this description",100.00));
        list.add(new CoursePOJO(103,"business", "Start your own business",200.00));
        list.add(new CoursePOJO(105,"accounts", "maintain your account",259.00));
        list.add(new CoursePOJO(107,"law", "follow the law",399.00));
        list.add(new CoursePOJO(109,"taxation", "pay your tax",129.00));


         cartAdapter = new CartAdapter(CartActivity.this, list);

         coursesCheckoutCart = findViewById(R.id.cart_recycler_view);
        coursesCheckoutCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        coursesCheckoutCart.setAdapter(cartAdapter);


//        Button checkoutButton = findViewById(R.id.checkout_button);


//        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("shadab/user/purchased_course");
//        checkoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child("new_child").setValue(intentExtraString).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(CartActivity.this, "CoursePOJO Added", Toast.LENGTH_SHORT).show();
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
