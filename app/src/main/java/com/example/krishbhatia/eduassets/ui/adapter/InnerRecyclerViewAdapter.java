package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krishbhatia.eduassets.POJO.ResourcePOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.activities.PdfViewerActivity;
import com.example.krishbhatia.eduassets.ui.activities.VideoPlayerActivity;

import java.util.ArrayList;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.InnerRecyclerViewHolder> {

    Context mContext;
    //private List<ResourcePOJO> mList;
    private ArrayList<ResourcePOJO> resourceList;

    public InnerRecyclerViewAdapter(Context mContext, ArrayList<ResourcePOJO> resourcePOJOS) {
        this.mContext = mContext;
        this.resourceList = resourcePOJOS;
    }

    @NonNull
    @Override
    public InnerRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_inner_recycler, null);
        return new InnerRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InnerRecyclerViewHolder holder, int position) {
        final ResourcePOJO res = resourceList.get(position);
        holder.textView.setText(res.getName());
        if (res.getResType().equals("pdf")){
            holder.imageView.setImageResource(R.drawable.ic_pdf);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_video);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (res.getResType().equals("pdf")){
                    Intent intent = new Intent(mContext, PdfViewerActivity.class);
                    intent.putExtra("url", res.getUrl());
                    mContext.startActivity(intent);
                } else if (res.getResType().equals("video")){
                    Intent intent = new Intent(mContext, VideoPlayerActivity.class);
                    intent.putExtra("url", res.getUrl());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    class InnerRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        View view;
        public InnerRecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.res_icon);
            textView = itemView.findViewById(R.id.res_title);
            view = itemView;

        }
    }

}
