package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishbhatia.eduassets.POJO.Course;
import com.example.krishbhatia.eduassets.R;

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
        Course course = courseList.get(position);

        //binding the data with the viewholder views
        courseViewHolder.textViewTitle.setText(course.getTitle());
        courseViewHolder.textViewDesc.setText(course.getDesc());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewDesc;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
        }
    }
}
