package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishbhatia.eduassets.POJO.SubjectBasicInfoPOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.activities.CourseSubjectsActivity;
import com.example.krishbhatia.eduassets.ui.activities.SubjectResourceActivity;

import java.util.ArrayList;

public class CourseSubjectsAdapter extends RecyclerView.Adapter<CourseSubjectsAdapter.CourseSubjectsHolder> {
    private Context mContext;
    private ArrayList<SubjectBasicInfoPOJO> subjectList;

    public CourseSubjectsAdapter(Context mContext, ArrayList<SubjectBasicInfoPOJO> subjectList) {
        this.mContext = mContext;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public CourseSubjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_course_subjects_recyler_view, parent, false);
        return new CourseSubjectsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseSubjectsHolder holder, int position) {
        final SubjectBasicInfoPOJO subject = subjectList.get(position);
        holder.subjectName.setText(subject.getSubjectName());
        holder.subjectCode.setText(String.valueOf(subject.getSubjectCode()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SubjectResourceActivity.class);
                intent.putExtra("SELECTED_SUBJECT", subject.getSubjectName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    class CourseSubjectsHolder extends RecyclerView.ViewHolder {
        private TextView subjectName;
        private TextView subjectCode;
        public CourseSubjectsHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.textViewCourseSubjectsTitle);
            subjectCode = itemView.findViewById(R.id.textViewCourseSubjectsCode);
        }
    }
}
