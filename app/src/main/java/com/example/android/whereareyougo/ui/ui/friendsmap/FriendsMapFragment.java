package com.example.android.whereareyougo.ui.ui.friendsmap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 18/08/2017.
 */

public class FriendsMapFragment extends BaseFragment implements FriendsMapView, OnMapReadyCallback, View.OnClickListener,
        FriendsMapSelectedAdapter.FriendMapSelectedOnClickListener {

    @Inject
    FriendsMapMvpPresenter<FriendsMapView> presenter;
    @BindView(R.id.map_view_friends)
    MapView mapViewFriends;
    @BindView(R.id.button_show_friends_map)
    FloatingActionButton buttonShowFriendsMap;
    @BindView(R.id.recycler_view_friends_map)
    SuperRecyclerView recyclerViewFriendsMap;
    @BindView(R.id.layout_recyclerview_friends_map)
    LinearLayout layoutRecyclerviewFriendsMap;
    @BindView(R.id.button_add_friends_map)
    FloatingActionButton buttonAddFriendsMap;
    Unbinder unbinder;
    @BindView(R.id.button_user_location)
    FloatingActionButton buttonUserLocation;
    private GoogleMap map;
    private Location currentUserLocation;
    private Marker currentUserLocationMarker;
    private InteractionWithFriendsMapFragment interaction;
    private ArrayList<String> followings;
    private ArrayList<User> followingsSelected;
    private FriendsMapSelectedAdapter adapter;
    private ArrayList<Marker> followingMarkers;
    private boolean checkLocationUpdate = false;
    private boolean isFollowCurrentUser = false;
    private HashMap<String, Integer> followingSelectedIndexs;


    public static FriendsMapFragment newInstance(Location location, boolean checkLocationUpdate) {
        FriendsMapFragment friendsMapFragment = new FriendsMapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("currentlocation", location);
        bundle.putBoolean("checklocationupdate", checkLocationUpdate);
        friendsMapFragment.setArguments(bundle);

        return friendsMapFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        interaction = (InteractionWithFriendsMapFragment) context;
        presenter.onAttach(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            currentUserLocation = bundle.getParcelable("currentlocation");
            checkLocationUpdate = bundle.getBoolean("checklocationupdate");
        }
        isFollowCurrentUser = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_map, container, false);
        unbinder = ButterKnife.bind(this, view);
        //
        MapsInitializer.initialize(getActivity());
        mapViewFriends.onCreate(savedInstanceState);
        mapViewFriends.getMapAsync(FriendsMapFragment.this);
        setupFriendsMapRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        initUiEvents();

    }



    private void initUiComponents() {
        if (isFollowCurrentUser) {
            buttonUserLocation.setImageResource(R.drawable.ic_unfollow);
        }
    }

    public void addFollowing(String followingId) {
        if (followings == null) {
            followings = new ArrayList<>();
        }
        followings.add(followingId);
    }

    public ArrayList<String> getFollowings() {
        return followings;
    }

    public void removeFollowingSelected(int position) {
        if (adapter != null) {
            adapter.removeItem(position);
        }
    }

    private void setupFriendsMapRecyclerView() {
        recyclerViewFriendsMap.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //
        if (followingsSelected == null) {
            followingsSelected = new ArrayList<>();
        }
        adapter = new FriendsMapSelectedAdapter(getActivity(), followingsSelected, this);
        recyclerViewFriendsMap.setAdapter(adapter);
    }

    private void addFollowingToAdapter(User user) {
        if (user != null) {
            adapter.addItem(user);
        }
    }

    public void notifyFollowingSelectedAdapterChange() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void setFollowingMarker(int index, LatLng location) {
        if (location != null && followingMarkers != null) {
            followingMarkers.get(index).setPosition(location);
            if (!isFollowCurrentUser){
                moveCamera(location);
            }
        }
    }

    private void initUiEvents() {
        buttonAddFriendsMap.setOnClickListener(this);
        buttonShowFriendsMap.setOnClickListener(this);
        buttonUserLocation.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading();
        mapViewFriends.onStart();
        presenter.getUserFollowings();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapViewFriends.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewFriends.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapViewFriends.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewFriends.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.removeFollowingsChildEvent();
        presenter.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        // map.setMyLocationEnabled(true);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            initMap();
            // buildGoogleApiClient();
            // googleApiClient.connect();
            showCurrentLocation();

        } else {
            // Show rationale and request permission.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MyKey.PERMISSIONS_REQUEST_LOCATION);
            }
            initMap();
            // buildGoogleApiClient();
            // googleApiClient.connect();
            showCurrentLocation();

        }
    }

    private MarkerOptions createMarker(LatLng position, String title) {
        return new MarkerOptions().position(position)
                .title(title)
                .anchor(0.5f, 0.5f);
    }

    public void showCurrentLocation() {
        if (currentUserLocation != null) {
            String userImageUri = interaction.getUserImage();
            if (userImageUri == null) {
                //xet anh mac dinh cho vi tri hien tai cua nguoi dung
                addUserMarkerOnMap(interaction.getCurrentUser(),
                        true);
            } else {
                addUserMarkerOnMap(interaction.getCurrentUser(),
                        true);
            }
            moveCamera(currentUserLocation);
            //  Log.d(MyKey.FRIENDS_MAP_FRAGMENT_TAG, "showCurrentLocation null =  "  + currentUserLocationMarker.getTitle());
            //
        } else {
            Log.d(MyKey.MAP_FRAGMENT_TAG, "Current location is null. Using defaults.");
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    public void addUserMarkerOnMap(User following, boolean isCurrentUser) {
        if (following != null) {
            MarkerOptions markerOptions = createMarker(Commons.convertStringToLocation(following.getCurrentLocation()), following.getName());
            loadImageUserMarker(markerOptions, following, isCurrentUser);
        }

    }

    private void moveCamera(Location location) {
        if (location != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), MyKey.ZOOM_LEVEl_DEFAULT));
        }
    }

    public void moveCamera(LatLng latLng){
        if (latLng != null){
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,MyKey.ZOOM_LEVEl_DEFAULT));
        }
    }

    public void moveCameraToCurrentUserLocation() {
        moveCamera(currentUserLocation);
    }

    private void loadImageUserMarker(final MarkerOptions userMarkerOption, final User following, final boolean isCurrentUser) {
        if (following == null) {
            return;
        }
        if (userMarkerOption != null) {
            if (following.getImageUrl() == null) {
                userMarkerOption.icon(BitmapDescriptorFactory
                        .fromBitmap(Commons.getMarkerBitmapFromView(getCustomViewUserMarker(), null, R.drawable.ic_user_default)));
            } else {
                RequestBuilder<Bitmap> requestBuilder = Glide.with(this).asBitmap();
                requestBuilder.load(following.getImageUrl())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                userMarkerOption.icon(BitmapDescriptorFactory
                                        .fromBitmap(Commons.getMarkerBitmapFromView(getCustomViewUserMarker(), resource, MyKey.NO_DRAWABLE)));
                                if (isCurrentUser) {
                                    setCurrentUserLocationMarker(map.addMarker(userMarkerOption));
                                } else {
                                    followingMarkers.add(map.addMarker(userMarkerOption));
                                    //
                                    presenter.setupUpdateFollowingRealTime(following);
                                    Log.d(MyKey.FRIENDS_MAP_FRAGMENT_TAG, "FollowingMarkerSize Glide = " + followingMarkers.size());
                                }
                            }
                        });
            }

        }

    }

    private void setCurrentUserLocationMarker(Marker marker) {
        currentUserLocationMarker = marker;
    }

    private View getCustomViewUserMarker() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater != null ? inflater.inflate(R.layout.custom_user_marker, null) : null;
    }

    public void setButtonUserLocationIcon(int iconId) {
        if (buttonUserLocation != null) {
            buttonUserLocation.setImageResource(iconId);
        }
    }

    public void addFollowingsSelectedOnMap() {
        if (followingsSelected != null) {
            if (followingMarkers == null) {
                followingMarkers = new ArrayList<>();
            }
            for (int index = 0; index < followingsSelected.size(); index++) {
                User following = followingsSelected.get(index);
                if (following.getCurrentLocation() != null) {
                    // Log.d(MyKey.FRIENDS_MAP_FRAGMENT_TAG, "FollowingLocation = " + followingLocation.latitude);
                    addUserMarkerOnMap(following, false);
                    Log.d(MyKey.FRIENDS_MAP_FRAGMENT_TAG, "FollowingMarkerSize = " + followingMarkers.size());
                    addFollowingSelectedIndexs(following.getUserID(), index);
                } else {
                    Log.d(MyKey.FRIENDS_MAP_FRAGMENT_TAG, "FollowingLocation null");
                }
                // else show message: current following not update location
            }
            //
        }
    }

    private void initMap() {
        hideLoading();
    }

    public boolean isCheckLocationUpdate() {
        return checkLocationUpdate;
    }

    public boolean isFollowCurrentUser() {
        return isFollowCurrentUser;
    }

    public void setFollowCurrentUser(boolean followCurrentUser) {
        isFollowCurrentUser = followCurrentUser;
    }

    public void updateUserLocation(Location location) {
        if (location != null) {
            currentUserLocation = location;
            if (currentUserLocationMarker == null) {
                showCurrentLocation();
            } else {
                currentUserLocationMarker.setPosition(new LatLng(currentUserLocation.getLatitude(), currentUserLocation.getLongitude()));
                if (isFollowCurrentUser) {
                    moveCamera(currentUserLocation);
                }
            }
        }
    }

    public ArrayList<User> getFollowingsSelected() {
        return followingsSelected;
    }

    public void setFollowingsSelected(HashMap<String, User> users) {
        if (followingsSelected == null) {
            followingsSelected = new ArrayList<>();
        }
        if (followingMarkers == null) {
            followingMarkers = new ArrayList<>();
        }

        int numberOfFollowingId = followings.size();
        for (int i = 0; i < numberOfFollowingId; i++) {
            String followingId = followings.get(i);
            if (users.containsKey(followingId)) {
                addFollowingToAdapter(users.get(followingId));
                followings.remove(i);
                numberOfFollowingId--;
                i--;
            }
        }
        addFollowingsSelectedOnMap();
    }

    private void addFollowingSelectedIndexs(String followingId, int index) {
        if (followingSelectedIndexs == null) {
            followingSelectedIndexs = new HashMap<>();
        }
        followingSelectedIndexs.put(followingId, index);
    }

    public HashMap<String, Integer> getFollowingSelectedIndexs() {
        return followingSelectedIndexs;
    }

    public void showMaxFollowingSelectedDialog() {
        new MaterialDialog.Builder(getActivity())
                .titleColorRes(R.color.colorAccent)
                .title(R.string.title_max_following_selected_dialog)
                .contentColorRes(R.color.colorSecondaryText)
                .content(R.string.content_max_following_selected_dialog)
                .positiveText(R.string.button_ok)
                .build().show();
    }

    public void openFollowingsSelectionDialog() {
        if (followings != null) {
            interaction.openFollowingsSelectionDialogFragment(followings);
        } else {
            followings = new ArrayList<>();
            interaction.openFollowingsSelectionDialogFragment(followings);
        }
    }

    public void setVisibleRecyclerViewFriendsMap() {
        if (layoutRecyclerviewFriendsMap.getVisibility() != View.VISIBLE) {
            layoutRecyclerviewFriendsMap.setVisibility(View.VISIBLE);
        } else {
            layoutRecyclerviewFriendsMap.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_friends_map:
                if (presenter != null) {
                    presenter.onClickButtonAddFollowings();
                    Toast.makeText(getActivity(), "Hello anh em", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_show_friends_map:
                if (presenter != null) {
                    presenter.onClickButtonShowRecyclerViewFriendsMap();
                }
                break;
            case R.id.button_user_location:
                if (presenter != null) {
                    presenter.onClickButtonUserLocation();
                }
                break;
        }
    }

    public ArrayList<Marker> getFollowingMarkers() {
        return followingMarkers;
    }

    public void setBorderColorForFollowingSelected(int position, int colorId){
        if (recyclerViewFriendsMap != null){
            CircleImageView followingImageSelected = (CircleImageView) recyclerViewFriendsMap.getRecyclerView()
                    .findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.image_user);
            followingImageSelected.setBorderColor(ContextCompat.getColor(getActivity(), colorId));
        }
    }

    //Click item on Recycler View
    @Override
    public void onItemClick(View v, int position) {
        switch (v.getId()) {
            case R.id.button_delete:
                if (presenter != null) {
                    presenter.onClickButtonDeleteFollowingSelected(followingsSelected.get(position).getUserID(), position);
                }
                break;
            case R.id.button_find_location:
                break;
            case R.id.image_user:
                if (presenter != null) {
                    presenter.onClickFollowingSelectedItem(position);
                }
                break;

        }
    }

    public void showDeleteFollowingDialog(final String userId, final int position) {
        new MaterialDialog.Builder(getActivity())
                .titleColorRes(R.color.colorAccent)
                .title(R.string.title_delete_following_dialog)
                .contentColorRes(R.color.colorSecondaryText)
                .content(R.string.content_delete_following_dialog)
                .positiveText(R.string.text_agree)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //delete following from following selected recyclerview
                        if (presenter != null) {
                            presenter.onClickButtonAgreeDeleteFollowingDialog(userId, position);
                        }
                    }
                })
                .negativeText(R.string.text_disagree)
                .build().show();
    }

    public interface InteractionWithFriendsMapFragment {
        String getUserImage();

        void openFollowingsSelectionDialogFragment(ArrayList<String> followingIds);

        User getCurrentUser();
    }
}
