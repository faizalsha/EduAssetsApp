package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.POJO.CourseBasicDetails;
import com.example.krishbhatia.eduassets.POJO.CoursePOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.activities.CartActivity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    //this context we will use to inflate the layout
    private Context mContext;

    //we are storing all the courses in a list
    private List<CourseBasicDetails> courseList;


    //getting the context and course list with constructor
    public CourseAdapter(Context mContext, List<CourseBasicDetails> courseList) {
        this.mContext = mContext;
        this.courseList = courseList;

    }


    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.course_layout, null);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder courseViewHolder, int position) {
        //getting the course of the specified position
        final CourseBasicDetails course = courseList.get(position);

        //binding the data with the viewHolder views
        courseViewHolder.textViewTitle.setText(course.getCourseName());
        courseViewHolder.textViewCode.setText(String.valueOf((int)course.getCourseId()));
        courseViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "njocsnaphndcpa", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, CartActivity.class);
                intent.putExtra("course", course.getCourseName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView textViewCode;
        View view;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.all_courses_fragment_title_text_view);
//            textViewDesc = itemView.findViewById(R.id.textViewDesc);


            textViewCode = itemView.findViewById(R.id.all_courses_fragment_course_code_text_view);
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
