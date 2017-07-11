package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.VenueCategory;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 11/07/2017.
 */

public class VenueCategoryAdapter extends UltimateViewAdapter<VenueCategoryAdapter.VenueCategoryViewHolder> {
    private Context context;
    private List<VenueCategory> venueCategories;
    private MyCardClickListener myCardClickListener;

    public VenueCategoryAdapter(Context context, List<VenueCategory> venueCategories, MyCardClickListener myCardClickListener) {
        this.context = context;
        this.venueCategories = venueCategories;
        this.myCardClickListener = myCardClickListener;
    }

    public interface MyCardClickListener{
        void onCardClick(View v, int position);
    }

    @Override
    public VenueCategoryViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public VenueCategoryViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public VenueCategoryViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_venue_category_row,parent,false);

        return new VenueCategoryViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return venueCategories.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(VenueCategoryViewHolder holder, int position) {
        VenueCategory venueCategory = venueCategories.get(position);
        if (venueCategory != null){
            holder.textCategoryName.setText(venueCategory.getCategoryName());
            Glide.with(context)
                 .load(venueCategory.getCategoryIconId())
                 .into(holder.categoryImage);
            if (venueCategory.isChoose()){
                holder.textCategoryChoose.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                holder.textCategoryChoose.setText(R.string.text_already_choose);
            }else{
                holder.textCategoryChoose.setTextColor(ContextCompat.getColor(context,R.color.colorSecondaryText));
                holder.textCategoryChoose.setText(R.string.text_not_choose);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class VenueCategoryViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener{
        TextView textCategoryName;
        CircleImageView categoryImage;
        TextView textCategoryChoose;

        public VenueCategoryViewHolder(View itemView) {
            super(itemView);
            textCategoryName = (TextView) itemView.findViewById(R.id.text_venue_category);
            textCategoryChoose = (TextView) itemView.findViewById(R.id.text_category_choose);
            categoryImage = (CircleImageView) itemView.findViewById(R.id.image_venue_category);
            //
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myCardClickListener.onCardClick(v, getAdapterPosition());
        }
    }
}
