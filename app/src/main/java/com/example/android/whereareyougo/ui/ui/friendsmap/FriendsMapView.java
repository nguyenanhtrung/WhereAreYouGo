package com.example.android.whereareyougo.ui.ui.friendsmap;

import android.location.Location;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nguyenanhtrung on 18/08/2017.
 */

public interface FriendsMapView extends MvpView {
    void addFollowing(String followingId);

    void openFollowingsSelectionDialog();

    ArrayList<User> getFollowingsSelected();

    void showMaxFollowingSelectedDialog();

    void showDeleteFollowingDialog(String userId, int position);

    void removeFollowingSelected(int position);

    ArrayList<String> getFollowings();

    void setVisibleRecyclerViewFriendsMap();

    ArrayList<Marker> getFollowingMarkers();

    void moveCameraToCurrentUserLocation();

    boolean isCheckLocationUpdate();

    void setButtonUserLocationIcon(int iconId);

    boolean isFollowCurrentUser();

    HashMap<String, Integer> getFollowingSelectedIndexs();

    void setFollowCurrentUser(boolean followCurrentUser);

    void notifyFollowingSelectedAdapterChange();

    void setFollowingMarker(int index, LatLng location);

    void setBorderColorForFollowingSelected(int position, int colorId);

    void moveCamera(LatLng latLng);


}
