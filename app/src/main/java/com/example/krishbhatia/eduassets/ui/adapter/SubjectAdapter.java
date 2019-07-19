package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishbhatia.eduassets.POJO.SubjectPOJO;
import com.example.krishbhatia.eduassets.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>{

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the courses in a list
    private List<SubjectPOJO> subjectList;


    //getting the context and course list with constructor
    public SubjectAdapter(Context mCtx, List<SubjectPOJO> subjectList) {
        this.mCtx = mCtx;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.course_layout, null);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder subjectViewHolder, int position) {
        //getting the course of the specified position
        SubjectPOJO subject = subjectList.get(position);

        //binding the data with the viewholder views
        subjectViewHolder.textViewTitle.setText(subject.getTitle());
        subjectViewHolder.textViewCode.setText(subject.getDesc());
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewCode;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.all_courses_fragment_title_text_view);
            textViewCode = itemView.findViewById(R.id.all_courses_fragment_course_code_text_view);
        }
    }
}
