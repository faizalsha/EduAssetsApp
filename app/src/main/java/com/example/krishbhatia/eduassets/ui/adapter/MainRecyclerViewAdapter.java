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

import com.example.krishbhatia.eduassets.POJO.Topic;
import com.example.krishbhatia.eduassets.R;

import java.util.ArrayList;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder> {

    private Context mContext;
    //private List<ResourcePOJO> mList;
    private ArrayList<Topic> topicList;

    public MainRecyclerViewAdapter(Context mContext, ArrayList<Topic> topicList) {
        this.mContext = mContext;
        this.topicList = topicList;
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

        Topic topic = topicList.get(position);
        InnerRecyclerViewAdapter innerRecyclerViewAdapter = new InnerRecyclerViewAdapter(mContext, topic.getResources());
        holder.innerRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.innerRecyclerView.setAdapter(innerRecyclerViewAdapter);

        holder.topText.setText(topic.getTitle());
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    class MainRecyclerViewHolder extends RecyclerView.ViewHolder {
        RecyclerView innerRecyclerView;
        ImageView arrow;
        TextView topText;

        public MainRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            innerRecyclerView = itemView.findViewById(R.id.inner_recycler);
            arrow = itemView.findViewById(R.id.img_arrow);
            topText = itemView.findViewById(R.id.top_text);

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
