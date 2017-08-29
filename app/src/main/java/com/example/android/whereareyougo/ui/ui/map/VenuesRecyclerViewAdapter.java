package com.example.android.whereareyougo.ui.ui.map;

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
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.List;

/**
 * Created by nguyenanhtrung on 26/06/2017.
 */

public class VenuesRecyclerViewAdapter extends BaseRecyclerViewAdapter<Result, RecyclerView.ViewHolder> {

    private Context context;
    private OnItemCheckListener onItemCheckListener;

    public VenuesRecyclerViewAdapter(List<Result> results, Context context, OnItemCheckListener onItemCheckListener) {
        super(results);
        this.context = context;
        this.onItemCheckListener = onItemCheckListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MyKey.VIEW_TYPE_NORMAL:
                return new VenueViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_venues_row, parent, false));
            case MyKey.VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_empty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Result result = getItem(position);
        if (result != null) {

            ((VenueViewHolder)holder).textVenueName.setText(result.getName());

            //
            OpeningHour openingHour = result.getOpeningHour();
            if (openingHour == null) {
                ((VenueViewHolder)holder).textVenueStatus.setText(R.string.opening_hour_null);
                ((VenueViewHolder)holder).textVenueStatus.setTextColor(ContextCompat.getColor(context, R.color.color_venue_status_empty));
            } else {
                if (openingHour.isOpenNow()) {
                    ((VenueViewHolder)holder).textVenueStatus.setText(R.string.text_venue_open_time);
                    ((VenueViewHolder)holder).textVenueStatus.setTextColor(ContextCompat.getColor(context, R.color.color_venue_status_open));
                } else {
                    ((VenueViewHolder)holder).textVenueStatus.setText(R.string.text_venue_close_time);
                    ((VenueViewHolder)holder).textVenueStatus.setTextColor(ContextCompat.getColor(context, R.color.color_venue_status_close));
                }
            }
            //
            Glide.with(context)
                    .load(result.getIcon())
                    .into(((VenueViewHolder)holder).imageVenueCategory);

            if (result.isChecked()) {
                ((VenueViewHolder)holder).checkChooseVenue.setChecked(true);
                ((VenueViewHolder)holder).checkChooseVenue.setVisibility(View.VISIBLE);
            } else {
                ((VenueViewHolder)holder).checkChooseVenue.setChecked(false);
                ((VenueViewHolder)holder).checkChooseVenue.setVisibility(View.INVISIBLE);
            }

            ((VenueViewHolder)holder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((VenueViewHolder)holder).checkChooseVenue.setChecked(
                            !((VenueViewHolder)holder).checkChooseVenue.isChecked());
                    if (((VenueViewHolder)holder).checkChooseVenue.isChecked()) {
                        ((VenueViewHolder)holder).checkChooseVenue.setVisibility(View.VISIBLE);
                        result.setChecked(true);
                        onItemCheckListener.onItemCheck(result);
                    } else {
                        ((VenueViewHolder)holder).checkChooseVenue.setVisibility(View.INVISIBLE);
                        result.setChecked(false);
                        onItemCheckListener.onItemUncheck(result);
                    }
                }
            });

        }
    }

    public interface OnItemCheckListener {
        void onItemCheck(Result result);

        void onItemUncheck(Result result);
    }

    public static class VenueViewHolder extends RecyclerView.ViewHolder {
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
