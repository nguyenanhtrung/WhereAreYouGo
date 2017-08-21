package com.example.android.whereareyougo.ui.ui.friendsmap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.adapter.FriendsMapSelectedAdapter;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.map.MapFragment;
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
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 18/08/2017.
 */

public class FriendsMapFragment extends BaseFragment implements FriendsMapView, OnMapReadyCallback, View.OnClickListener {

    @Inject
    FriendsMapMvpPresenter<FriendsMapView> presenter;
    @BindView(R.id.map_view_friends)
    MapView mapViewFriends;
    @BindView(R.id.button_show_friends_map)
    FloatingActionButton buttonShowFriendsMap;
    @BindView(R.id.recycler_view_friends_map)
    UltimateRecyclerView recyclerViewFriendsMap;
    @BindView(R.id.layout_recyclerview_friends_map)
    LinearLayout layoutRecyclerviewFriendsMap;
    @BindView(R.id.button_add_friends_map)
    FloatingActionButton buttonAddFriendsMap;
    Unbinder unbinder;
    private GoogleMap map;
    private Location currentUserLocation;
    private Marker currentUserLocationMarker;
    private InteractionWithFriendsMapFragment interaction;
    private ArrayList<String> followings;
    private ArrayList<User> followingsSelected;
    private FriendsMapSelectedAdapter adapter;


    public static FriendsMapFragment newInstance(Location location) {
        FriendsMapFragment friendsMapFragment = new FriendsMapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("currentlocation", location);
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

        }
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
        initUiEvents();
    }

    public void addFollowing(String followingId) {
        if (followings == null) {
            followings = new ArrayList<>();
        }
        if (followingId != null) {
            followings.add(followingId);
        }
    }

    public void setFollowingsSelected(HashMap<String, User> users) {
        if (followingsSelected == null) {
            followingsSelected = new ArrayList<>();
        }
        int numberOfFollowingId = followings.size();
        for (int i = 0; i < numberOfFollowingId; i++) {
            String followingId = followings.get(i);
            if (users.containsKey(followingId)) {
                addFollowingToAdapter(users.get(followingId));
                followings.remove(followingId);
                numberOfFollowingId--;
                i--;
            }
        }
    }

    private void setupFriendsMapRecyclerView() {
        recyclerViewFriendsMap.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFriendsMap.showEmptyView();
        //
        if (followingsSelected == null) {
            followingsSelected = new ArrayList<>();
        }
        adapter = new FriendsMapSelectedAdapter(getActivity(), followingsSelected);
        recyclerViewFriendsMap.setAdapter(adapter);
    }

    private void addFollowingToAdapter(User user) {
        if (user != null) {
            adapter.addFollowing(user);
        }
    }

    private void initUiEvents() {
        buttonAddFriendsMap.setOnClickListener(this);
        buttonShowFriendsMap.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
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


    private Marker createMarker(LatLng position, Bitmap imageMarker, String title) {
        return map.addMarker(new MarkerOptions().position(position)
                .title(title)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(imageMarker)));
    }

    public void showCurrentLocation() {
        if (currentUserLocation != null) {

            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mCustomMarkerView = inflater.inflate(R.layout.custom_user_marker, null);

            //
            String userImageUri = interaction.getUserImage();

            if (userImageUri == null) {
                //xet anh mac dinh cho vi tri hien tai cua nguoi dung
                currentUserLocationMarker = createMarker(new LatLng(currentUserLocation.getLatitude(), currentUserLocation.getLongitude()),
                        Commons.getMarkerBitmapFromView(mCustomMarkerView, null, R.drawable.ic_user_default), "Me");
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(currentUserLocation.getLatitude(),
                                currentUserLocation.getLongitude()), MyKey.ZOOM_LEVEl_DEFAULT));
                return;
            }


            Glide.with(getActivity()).
                    load(userImageUri)
                    .asBitmap()
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap,
                                                    GlideAnimation<? super Bitmap> glideAnimation) {
                            currentUserLocationMarker = createMarker(new LatLng(currentUserLocation.getLatitude(), currentUserLocation.getLongitude()),
                                    Commons.getMarkerBitmapFromView(mCustomMarkerView, bitmap, MyKey.NO_DRAWABLE), "Me");


                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentUserLocation.getLatitude(),
                                            currentUserLocation.getLongitude()), MyKey.ZOOM_LEVEl_DEFAULT));


                        }
                    });
            //

        } else {
            Log.d(MyKey.MAP_FRAGMENT_TAG, "Current location is null. Using defaults.");
            //map.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void initMap() {

    }

    public ArrayList<User> getFollowingsSelected(){
        return followingsSelected;
    }

    public void showMaxFollowingSelectedDialog(){
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
                break;
        }
    }

    public interface InteractionWithFriendsMapFragment {
        String getUserImage();

        void openFollowingsSelectionDialogFragment(ArrayList<String> followingIds);
    }
}
