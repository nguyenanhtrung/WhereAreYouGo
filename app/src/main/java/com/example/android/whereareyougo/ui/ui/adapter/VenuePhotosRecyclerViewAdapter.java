package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.VenuePhoto;
import com.example.android.whereareyougo.ui.utils.MyKey;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

/**
 * Created by nguyenanhtrung on 29/06/2017.
 */

public class VenuePhotosRecyclerViewAdapter extends UltimateViewAdapter<VenuePhotosRecyclerViewAdapter.VenuePhotosViewHolder> {
    private Context context;
    private List<VenuePhoto> venuePhotos;

    public VenuePhotosRecyclerViewAdapter(Context context, List<VenuePhoto> venuePhotos) {
        this.context = context;
        this.venuePhotos = venuePhotos;
    }

    @Override
    public VenuePhotosViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public VenuePhotosViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public VenuePhotosViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_venuephoto_row,parent,false);
        return new VenuePhotosViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return venuePhotos.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(VenuePhotosViewHolder holder, int position) {
        VenuePhoto currentVenuePhoto = venuePhotos.get(position);
        if(currentVenuePhoto != null){
            StringBuilder photoUrl = new StringBuilder();
            int maxWidth = 300;
            int maxHeight = 300;
            photoUrl.append("https://maps.googleapis.com/maps/api/place/photo?")
                    .append("maxwidth=")
                    .append(maxWidth)
                    .append("&")
                    .append("maxheight=")
                    .append(maxHeight)
                    .append("&")
                    .append("photoreference=")
                    .append(currentVenuePhoto.getPhotoReference())
                    .append("&")
                    .append("key=")
                    .append(MyKey.GOOGLE_PLACES_KEY);

            Glide.with(context)
                 .load(photoUrl.toString())
                 .centerCrop()
                 .into(holder.imageVenuePhoto);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class VenuePhotosViewHolder extends UltimateRecyclerviewViewHolder{
        ImageView imageVenuePhoto;

        public VenuePhotosViewHolder(View itemView) {
            super(itemView);
            imageVenuePhoto = (ImageView) itemView.findViewById(R.id.image_venue_photo);

        }
    }
}
