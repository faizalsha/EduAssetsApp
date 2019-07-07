package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.pojo.Subject;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>{

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the courses in a list
    private List<Subject> subjectList;


    //getting the context and course list with constructor
    public SubjectAdapter(Context mCtx, List<Subject> subjectList) {
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
        Subject subject = subjectList.get(position);

        //binding the data with the viewholder views
        subjectViewHolder.textViewTitle.setText(subject.getTitle());
        subjectViewHolder.textViewDesc.setText(subject.getDesc());
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewDesc;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
        }
    }
}
