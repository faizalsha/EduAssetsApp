package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.POJO.CourseBasicInfoPOJO;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.activities.CourseSubjectsActivity;

import java.util.ArrayList;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    //this context we will use to inflate the layout
    private Context mContext;

    //we are storing all the courses in a list
    private ArrayList<CourseBasicInfoPOJO> courseList;


    //getting the context and course list with constructor
    public CourseAdapter(Context mContext, ArrayList<CourseBasicInfoPOJO> courseList) {
        this.mContext = mContext;
        this.courseList = courseList;

    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.course_layout, null);
        return new CourseViewHolder(view, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, int position) {
        //getting the course of the specified position
        final CourseBasicInfoPOJO course = courseList.get(position);

        //binding the data with the viewHolder views
        holder.courseTitle.setText(course.getCourseFullTitle());
        holder.textViewCode.setText(String.valueOf(course.getCourseId()));
//        Picasso.with(mContext)
//                .load(course.getCourseUrl())
//                .fit()
//                .centerCrop()
//                .placeholder(R.drawable.com_facebook_button_background)
//                .into(holder.courseImageTextView);
        holder.courseImageTextView.setText(course.getCourseName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, CourseSubjectsActivity.class);
                intent.putExtra(Constants.COURSE, course.getCourseFullTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView courseTitle, textViewCode;
        TextView courseImageTextView;
        ProgressBar progressBar;

        public CourseViewHolder(@NonNull View itemView, ViewGroup viewGroup) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.courseFullTitle);
//            textViewDesc = itemView.findViewById(R.id.textViewDesc);


            textViewCode = itemView.findViewById(R.id.all_courses_fragment_course_code_text_view);

            courseImageTextView = itemView.findViewById(R.id.courseImageTextView);
            progressBar = viewGroup.findViewById(R.id.allCourseProgressBar);
        }
    }
}
