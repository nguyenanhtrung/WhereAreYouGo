package com.example.android.whereareyougo.ui.ui.favoritevenues;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.List;

/**
 * Created by nguyenanhtrung on 30/06/2017.
 */

public class FavoriteVenuesRecyclerViewAdapter extends BaseRecyclerViewAdapter<FavoriteVenue, RecyclerView.ViewHolder> {
    private Context context;
    private MyItemClickListener myItemClickListener;

    public FavoriteVenuesRecyclerViewAdapter(Context context, List<FavoriteVenue> favoriteVenues, MyItemClickListener myItemClickListener) {
        super(favoriteVenues);
        this.context = context;
        this.myItemClickListener = myItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoriteVenueViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_favoritevenue_row, parent, false));
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FavoriteVenue currentVenue = getItem(position);
        if (currentVenue != null) {
            FavoriteVenueViewHolder viewHolder = (FavoriteVenueViewHolder) holder;
            viewHolder.textVenueName.setText(currentVenue.getName());
            Glide.with(context)
                    .load(currentVenue.getVenueCategoryIcon())
                    .into(viewHolder.imageVenueCategory);
        }
    }

    public interface MyItemClickListener {
        void onButtonClick(View v, int position);
    }

    public class FavoriteVenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageVenueCategory;
        TextView textVenueName;
        ImageButton buttonFindLocation;
        ImageButton buttonDelete;

        public FavoriteVenueViewHolder(View itemView) {
            super(itemView);
            imageVenueCategory = (ImageView) itemView.findViewById(R.id.image_venue_category);
            textVenueName = (TextView) itemView.findViewById(R.id.text_venue_name);
            buttonFindLocation = (ImageButton) itemView.findViewById(R.id.button_venue_location);
            buttonDelete = (ImageButton) itemView.findViewById(R.id.button_delete);

            //
            buttonFindLocation.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myItemClickListener.onButtonClick(v, getAdapterPosition());
        }
    }


}
