package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.krishbhatia.eduassets.POJO.ResourcePOJO;
import com.example.krishbhatia.eduassets.R;

import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder> {

    private Context mContext;
    private List<ResourcePOJO> mList;

    public MainRecyclerViewAdapter(Context mContext, List<ResourcePOJO> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_main_recycler,null);
        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewHolder mainRecyclerViewHolder, int i) {

        InnerRecyclerViewAdapter innerRecyclerViewAdapter = new InnerRecyclerViewAdapter(mContext, mList);
        mainRecyclerViewHolder.innerRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mainRecyclerViewHolder.innerRecyclerView.setAdapter(innerRecyclerViewAdapter);

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MainRecyclerViewHolder extends RecyclerView.ViewHolder {
        RecyclerView innerRecyclerView;
        ImageView arrow;

        public MainRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            innerRecyclerView = itemView.findViewById(R.id.inner_recycler);
            arrow = itemView.findViewById(R.id.img_arrow);

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
