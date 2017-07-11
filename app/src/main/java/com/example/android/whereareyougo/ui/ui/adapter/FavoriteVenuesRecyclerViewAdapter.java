package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.ui.swipe.SwipeableRecyclerViewTouchListener;

import java.util.List;

/**
 * Created by nguyenanhtrung on 30/06/2017.
 */

public class FavoriteVenuesRecyclerViewAdapter extends UltimateViewAdapter<FavoriteVenuesRecyclerViewAdapter.FavoriteVenueViewHolder>
        implements SwipeableRecyclerViewTouchListener.SwipeListener {

    private Context context;
    private List<FavoriteVenue> favoriteVenues;
    private MyItemClickListener myItemClickListener;

    public interface MyItemClickListener {
        void onButtonClick(View v, int position);
    }

    public FavoriteVenuesRecyclerViewAdapter(Context context, List<FavoriteVenue> favoriteVenues, MyItemClickListener myItemClickListener) {
        this.context = context;
        this.favoriteVenues = favoriteVenues;
        this.myItemClickListener = myItemClickListener;
    }

    @Override
    public FavoriteVenueViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public FavoriteVenueViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public FavoriteVenueViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_favoritevenue_row, parent, false);
        return new FavoriteVenueViewHolder(view);
    }


    public void setOnDragStartListener(OnStartDragListener
                                               dragStartListener) {
        mDragStartListener = dragStartListener;

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //super.onItemMove(fromPosition, toPosition);
        swapPositions(favoriteVenues, fromPosition, toPosition);
        super.onItemMove(fromPosition, toPosition);
    }

    @Override
    public int getAdapterItemCount() {
        return favoriteVenues.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(FavoriteVenueViewHolder holder, int position) {
        FavoriteVenue currentVenue = favoriteVenues.get(position);
        if (currentVenue != null) {
            holder.textVenueName.setText(currentVenue.getName());
            Glide.with(context)
                    .load(currentVenue.getVenueCategoryIcon())
                    .into(holder.imageVenueCategory);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void removeItem(int position){
        favoriteVenues.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void removeAllItems(){
        favoriteVenues.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean canSwipe(int position) {
        return true;
    }

    @Override
    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {

    }

    @Override
    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {

    }

    @Override
    public void onItemDismiss(int position) {
        super.onItemDismiss(position);
        if (position > 0) {
            removeInternal(favoriteVenues, position);
            notifyDataSetChanged();
            notifyItemRemoved(position);
        }
        // notifyItemRemoved(position);
//        notifyDataSetChanged();

    }

    public FavoriteVenue getItem(int position) {
        return favoriteVenues.get(position);
    }

    public class FavoriteVenueViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener {
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
