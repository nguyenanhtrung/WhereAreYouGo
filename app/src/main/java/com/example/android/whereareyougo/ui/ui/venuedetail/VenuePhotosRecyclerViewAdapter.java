package com.example.android.whereareyougo.ui.ui.venuedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.VenuePhoto;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.List;

/**
 * Created by nguyenanhtrung on 29/06/2017.
 */

public class VenuePhotosRecyclerViewAdapter extends BaseRecyclerViewAdapter<VenuePhoto, RecyclerView.ViewHolder> {
    private Context context;

    public VenuePhotosRecyclerViewAdapter(Context context, List<VenuePhoto> venuePhotos) {
        super(venuePhotos);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VenuePhotosViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_venuephoto_row, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VenuePhoto currentVenuePhoto = getItem(position);
        if (currentVenuePhoto != null) {
            VenuePhotosViewHolder viewHolder = (VenuePhotosViewHolder) holder;
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
                    .into(viewHolder.imageVenuePhoto);


        }
    }

    public class VenuePhotosViewHolder extends RecyclerView.ViewHolder {
        ImageView imageVenuePhoto;

        public VenuePhotosViewHolder(View itemView) {
            super(itemView);
            imageVenuePhoto = (ImageView) itemView.findViewById(R.id.image_venue_photo);
        }
    }


}
