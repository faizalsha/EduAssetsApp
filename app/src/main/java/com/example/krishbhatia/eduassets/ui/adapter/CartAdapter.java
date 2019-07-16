package com.example.krishbhatia.eduassets.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.pojo.Course;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private Context mContext;
    private ArrayList<Course> mCartList;

    public CartAdapter(Context mContext, ArrayList<Course> mCartList) {
        this.mContext = mContext;
        this.mCartList = mCartList;
    }

    @Override
    public CartHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_cart_recycler, viewGroup, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder( CartHolder holder, int position) {
        Course course = mCartList.get(position);
        holder.courseTitle.setText(course.getTitle());
        holder.courseDesc.setText(course.getDesc());
        holder.coursePrice.setText(Double.toString(course.getPrice()) + " INR");
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    class CartHolder extends RecyclerView.ViewHolder {
        private TextView courseTitle;
        private TextView courseDesc;
        private TextView coursePrice;

        public CartHolder( View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.cart_product_title_view);
            courseDesc = itemView.findViewById(R.id.cart_product_desc_view);
            coursePrice = itemView.findViewById(R.id.cart_product_price_view);
        }
    }
}
