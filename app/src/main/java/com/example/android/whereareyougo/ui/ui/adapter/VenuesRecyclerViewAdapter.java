package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.OpeningHour;
import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

/**
 * Created by nguyenanhtrung on 26/06/2017.
 */

public class VenuesRecyclerViewAdapter extends UltimateViewAdapter<VenuesRecyclerViewAdapter.VenueViewHolder> {

    private List<Result> results;
    private Context context;

    public VenuesRecyclerViewAdapter(List<Result> results, Context context, OnItemCheckListener onItemCheckListener) {
        this.results = results;
        this.context = context;
        this.onItemCheckListener = onItemCheckListener;
    }

    public interface OnItemCheckListener {
        void onItemCheck(Result result);

        void onItemUncheck(Result result);
    }

    private OnItemCheckListener onItemCheckListener;


    @Override
    public VenueViewHolder newFooterHolder(View view) {
        return new VenueViewHolder(view);
    }

    @Override
    public VenueViewHolder newHeaderHolder(View view) {
        return new VenueViewHolder(view);
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_venues_row, parent, false);

        return new VenueViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return results.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }


    @Override
    public void onBindViewHolder(final VenueViewHolder holder, int position) {
        final Result result = results.get(position);
        if (result != null) {
            holder.textVenueName.setText(result.getName());

            //
            OpeningHour openingHour = result.getOpeningHour();
            if (openingHour == null) {
                holder.textVenueStatus.setText(R.string.opening_hour_null);
                holder.textVenueStatus.setTextColor(ContextCompat.getColor(context, R.color.color_venue_status_empty));
            } else {
                if (openingHour.isOpenNow()) {
                    holder.textVenueStatus.setText(R.string.text_venue_open_time);
                    holder.textVenueStatus.setTextColor(ContextCompat.getColor(context, R.color.color_venue_status_open));
                } else {
                    holder.textVenueStatus.setText(R.string.text_venue_close_time);
                    holder.textVenueStatus.setTextColor(ContextCompat.getColor(context, R.color.color_venue_status_close));
                }
            }
            //
            Glide.with(context)
                    .load(result.getIcon())
                    .into(holder.imageVenueCategory);

            if (result.isChecked()){
                holder.checkChooseVenue.setChecked(true);
                holder.checkChooseVenue.setVisibility(View.VISIBLE);
            }else{
                holder.checkChooseVenue.setChecked(false);
                holder.checkChooseVenue.setVisibility(View.INVISIBLE);
            }

            ((VenueViewHolder) holder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((VenueViewHolder) holder).checkChooseVenue.setChecked(
                            !((VenueViewHolder) holder).checkChooseVenue.isChecked());
                    if (((VenueViewHolder) holder).checkChooseVenue.isChecked()) {
                        holder.checkChooseVenue.setVisibility(View.VISIBLE);
                        result.setChecked(true);
                        onItemCheckListener.onItemCheck(result);
                    } else {
                        holder.checkChooseVenue.setVisibility(View.INVISIBLE);
                        result.setChecked(false);
                        onItemCheckListener.onItemUncheck(result);
                    }
                }
            });




        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class VenueViewHolder extends UltimateRecyclerviewViewHolder {
        ImageView imageVenueCategory;
        TextView textVenueStatus;
        TextView textVenueName;
        CheckBox checkChooseVenue;

        public VenueViewHolder(View itemView) {
            super(itemView);

            imageVenueCategory = (ImageView) itemView.findViewById(R.id.image_venue_category);
            textVenueStatus = (TextView) itemView.findViewById(R.id.text_venue_status);
            textVenueName = (TextView) itemView.findViewById(R.id.text_venue_name);
            checkChooseVenue = (CheckBox) itemView.findViewById(R.id.check_box_choose);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
