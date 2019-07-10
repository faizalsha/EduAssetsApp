package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishbhatia.eduassets.R;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.InnerRecyclerViewHolder> {

    Context mContext;

    public InnerRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public InnerRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_inner_recycler, null);
        return new InnerRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerRecyclerViewHolder innerRecyclerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class InnerRecyclerViewHolder extends RecyclerView.ViewHolder {
        public InnerRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
