package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.POJO.ResourcePOJO;
import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.activities.PdfViewerActivity;
import com.example.krishbhatia.eduassets.ui.activities.VideoPlayerActivity;

import java.util.List;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.InnerRecyclerViewHolder> {

    Context mContext;
    private List<ResourcePOJO> mList;

    public InnerRecyclerViewAdapter(Context mContext, List<ResourcePOJO> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public InnerRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_inner_recycler, null);
        return new InnerRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InnerRecyclerViewHolder innerRecyclerViewHolder, int position) {
        final ResourcePOJO res = mList.get(position);
        innerRecyclerViewHolder.textView.setText(res.getName());
        if (res.getResType() == "pdf"){
            innerRecyclerViewHolder.imageView.setImageResource(R.drawable.ic_pdf);
        } else if (res.getResType() == "video"){
            innerRecyclerViewHolder.imageView.setImageResource(R.drawable.ic_video);
        }

        innerRecyclerViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (res.getResType() == "pdf"){
                    Intent intent = new Intent(mContext, PdfViewerActivity.class);
                    mContext.startActivity(intent);
                } else if (res.getResType() == "video"){
                    mContext.startActivity(new Intent(mContext, VideoPlayerActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
