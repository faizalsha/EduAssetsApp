package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishbhatia.eduassets.POJO.Course;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.activities.CourseActivity;

import java.util.ArrayList;

public class PurchasedCourseAdapter extends RecyclerView.Adapter<PurchasedCourseAdapter.PurchasedCourseHolder> {

    private ArrayList<String> purchasedCourseList;
    private Context mContext;


    public PurchasedCourseAdapter(Context mContext, ArrayList<String> purchasedCourseList) {
        this.mContext = mContext;
        this.purchasedCourseList = purchasedCourseList;
    }


    @NonNull
    @Override
    public PurchasedCourseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_purchased_course_recycler, viewGroup, false);
        return new PurchasedCourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PurchasedCourseHolder holder, int position) {
        String string = purchasedCourseList.get(position);
        holder.textView.setText(string);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseActivity.class);
                intent.putExtra("course", holder.textView.getText().toString());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return purchasedCourseList.size();
    }

    class PurchasedCourseHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public PurchasedCourseHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.purchased_course_textView);
        }
    }
}
