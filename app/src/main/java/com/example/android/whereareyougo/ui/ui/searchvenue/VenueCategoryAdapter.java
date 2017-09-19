package com.example.android.whereareyougo.ui.ui.searchvenue;

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
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 11/07/2017.
 */

public class VenueCategoryAdapter extends BaseRecyclerViewAdapter<VenueCategory, RecyclerView.ViewHolder> {
    private Context context;
    private MyCardClickListener myCardClickListener;

    public VenueCategoryAdapter(Context context, List<VenueCategory> venueCategories, MyCardClickListener myCardClickListener) {
        super(venueCategories);
        this.context = context;
        this.myCardClickListener = myCardClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VenueCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_venue_category_row, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VenueCategory venueCategory = getItem(position);
        if (venueCategory != null) {
            VenueCategoryViewHolder viewHolder = (VenueCategoryViewHolder) holder;
            viewHolder.textCategoryName.setText(venueCategory.getCategoryName());
            Glide.with(context)
                    .load(venueCategory.getCategoryIconId())
                    .into(viewHolder.categoryImage);
            if (venueCategory.isChoose()) {
                viewHolder.textCategoryChoose.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                viewHolder.textCategoryChoose.setText(R.string.text_already_choose);
            } else {
                viewHolder.textCategoryChoose.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryText));
                viewHolder.textCategoryChoose.setText(R.string.text_not_choose);
            }
        }
    }

    public interface MyCardClickListener {
        void onCardClick(View v, int position);
    }

    public class VenueCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
