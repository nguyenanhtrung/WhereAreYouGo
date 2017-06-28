package com.example.android.whereareyougo.ui.ui.map.clusteritem;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by nguyenanhtrung on 27/06/2017.
 */

public class VenueMarkerItem implements ClusterItem {
    private final LatLng position;
    private final String title;
    private final String snippet;
    private String venueCategoryImage;

    public VenueMarkerItem(LatLng position, String title, String snippet) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
    }

    public String getVenueCategoryImage() {
        return venueCategoryImage;
    }

    public void setVenueCategoryImage(String venueCategoryImage) {
        this.venueCategoryImage = venueCategoryImage;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }
}
