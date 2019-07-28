package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krishbhatia.eduassets.POJO.SectionPOJO;
import com.example.krishbhatia.eduassets.POJO.SubjectResPOJO;
import com.example.krishbhatia.eduassets.R;

import java.util.ArrayList;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder> {

    private Context mContext;
    //private List<ResourcePOJO> mList;
    private SubjectResPOJO subjectRes;
    private ArrayList<SectionPOJO> sectionList;

    public MainRecyclerViewAdapter(Context mContext, SubjectResPOJO subjectRes) {
        this.mContext = mContext;
        this.subjectRes = subjectRes;
        this.sectionList = this.subjectRes.getSection();
    }

    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_main_recycler,null);
        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewHolder holder, int position) {

        SectionPOJO section = sectionList.get(position);
        InnerRecyclerViewAdapter innerRecyclerViewAdapter = new InnerRecyclerViewAdapter(mContext, section.getResource());
        holder.innerRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.innerRecyclerView.setAdapter(innerRecyclerViewAdapter);

        holder.sectionName.setText(section.getSectionName());
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    class MainRecyclerViewHolder extends RecyclerView.ViewHolder {
        RecyclerView innerRecyclerView;
        ImageView arrow;
        TextView sectionName;

        public MainRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            innerRecyclerView = itemView.findViewById(R.id.inner_recycler);
            arrow = itemView.findViewById(R.id.arrow);
            sectionName = itemView.findViewById(R.id.sectionName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(innerRecyclerView.getVisibility() == View.VISIBLE){
                        innerRecyclerView.setVisibility(View.GONE);
                        arrow.setImageResource(R.drawable.arrow_down);
                    }else {
                        innerRecyclerView.setVisibility(View.VISIBLE);
                        arrow.setImageResource(R.drawable.arrow_up);
                    }
                }
            });
        }
    }
}
