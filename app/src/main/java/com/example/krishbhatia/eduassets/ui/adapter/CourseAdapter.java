package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishbhatia.eduassets.pojo.Course;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.activities.CartActivity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the courses in a list
    private List<Course> courseList;


    //getting the context and course list with constructor
    public CourseAdapter(Context mCtx, List<Course> courseList) {
        this.mCtx = mCtx;
        this.courseList = courseList;

    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.course_layout, null);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder courseViewHolder, int position) {
        //getting the course of the specified position
        final Course course = courseList.get(position);

        //binding the data with the viewHolder views
        courseViewHolder.textViewTitle.setText(course.getTitle());
        courseViewHolder.textViewCode.setText(String.valueOf(course.getCode()));
        courseViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, CartActivity.class);
                intent.putExtra("course", course.getTitle());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewDesc;
        TextView /*textViewTitle*/ textViewCode;
        View view;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
//            textViewDesc = itemView.findViewById(R.id.textViewDesc);


//            textViewCode = itemView.findViewById(R.id.text);
            view = itemView;
        }


//        @Override
//        public void onClick(View v) {
//            onCourseClickListener.onCourseClick();
//        }
    }

//    public interface OnCourseClickListener{
//        void onCourseClick();
//    }
}
