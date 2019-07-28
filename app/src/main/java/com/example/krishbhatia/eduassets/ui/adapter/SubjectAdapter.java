package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.krishbhatia.eduassets.POJO.SubjectBasicInfoPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.activities.SubjectResourceActivity;

import java.util.ArrayList;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>{

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the courses in a list
    private ArrayList<SubjectBasicInfoPOJO> subjectList;


    //getting the context and course list with constructor
    public SubjectAdapter(Context mCtx, ArrayList<SubjectBasicInfoPOJO> subjectList) {
        this.mCtx = mCtx;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_subjects_recyler, viewGroup, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder subjectViewHolder, int position) {
        //getting the course of the specified position
        final SubjectBasicInfoPOJO subject = subjectList.get(position);

        //binding the data with the viewholder views
        subjectViewHolder.textViewTitle.setText(subject.getSubjectName());
        subjectViewHolder.textViewCode.setText(String.valueOf(subject.getSubjectCode()));
        subjectViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, SubjectResourceActivity.class);
                intent.putExtra("SELECTED_SUBJECT", subject.getSubjectName());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewCode;
        ImageView imageView;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.subjectTitle);
            textViewCode = itemView.findViewById(R.id.subjectCode);
            imageView = itemView.findViewById(R.id.subjectImage);
        }
    }
}
