package com.example.android.whereareyougo.ui.ui.map;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackSingleChoice;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.main.MainActivity;
import com.example.android.whereareyougo.ui.ui.map.clusteritem.VenueMarkerItem;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MapFragment extends BaseFragment implements MapMvpView, OnMapReadyCallback,
        OnClickListener, ClusterManager.OnClusterItemInfoWindowClickListener<VenueMarkerItem> {

    private final String[] mapType = new String[]{"NORMAL",
            "HYBRID",
            "SATELLITE",
            "TERRAIN",
            "NONE"};
    @Inject
    MapMvpPresenter<MapMvpView> mapMvpPresenter;
    @BindView(R.id.edit_text_search_venue)
    EditText editTextSearchVenue;
    @BindView(R.id.image_button_search)
    ImageButton imageButtonSearch;
    @BindView(R.id.button_send_message)
    FloatingActionButton buttonSendMessage;
    @BindView(R.id.button_call)
    FloatingActionButton buttonCall;
    @BindView(R.id.float_button_help)
    FloatingActionMenu floatButtonHelp;
    Unbinder unbinder;
    @BindView(R.id.button_map_type)
    FloatingActionButton buttonMapType;
    private GoogleMap map;
    private MapView mapView;
    private Location lastKnownLocation;
    private Marker currentLocationMarker;
    private MaterialDialog searchLoadingDialog;
    private InteractionWithMapFragment interactionWithMapFragment;
    private boolean locationPermissionGranted = false;
    private VenueMarkerItem clickedClusterItem;
    private ClusterManager<VenueMarkerItem> clusterManager;
    private ArrayList<VenueMarkerItem> venueMarkerItems;
    private MaterialDialog loadingDialog;
    private Bundle bundleSearchVenue;
    private boolean checkRequestLocationUpdate = false;

    public static MapFragment newInstance(Location currentUserLocation) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("currentlocation", currentUserLocation);
        mapFragment.setArguments(bundle);

        return mapFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_map, container, false);
        unbinder = ButterKnife.bind(this, view);

        initUiComponents();


        MapsInitializer.initialize(getActivity());
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(MapFragment.this);

        /*if (bundleSearchVenue != null) {
            mapMvpPresenter.getVenuesByRadiusAndCategory(bundleSearchVenue);

        }*/
        return view;

    }


    public void updateUserLocationOnMap(Location location) {
        lastKnownLocation = location;
        if (currentLocationMarker == null) {
            showCurrentLocation();
        } else {
            currentLocationMarker.setPosition(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()), MyKey.ZOOM_LEVEl_DEFAULT));
        }


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*bundleSearchVenue = getArguments();
        if (bundleSearchVenue == null) {
            Toast.makeText(getActivity(), "Bundle Search Null", Toast.LENGTH_SHORT).show();
        } else {
            //Log.d(MyKey.MAP_FRAGMENT_TAG,"current location = " + lastKnownLocation.toString());
        }*/
        Bundle bundle = getArguments();
        if (bundle != null) {
            lastKnownLocation = bundle.getParcelable("currentlocation");
            //Toast.makeText(getActivity(), "UserLocation = " + lastKnownLocation.getLatitude(), Toast.LENGTH_SHORT).show();
        }
        //
    }

    public void drawPolyLineOnMap(LatLng destination) {
        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(getLatLngCurrentUser());
        latLngs.add(destination);

        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.BLUE);
        polyOptions.width(5);
        polyOptions.addAll(latLngs);
        map.addPolyline(polyOptions);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : latLngs) {
            builder.include(latLng);
        }

    }

    public LatLng getLatLngCurrentUser() {
        if (lastKnownLocation != null) {
            LatLng latLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            return latLng;
        }

        return null;
    }

    private void showLoadingDialog() {
        loadingDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.title_loading_dialog)
                .titleColorRes(R.color.colorAccent)
                .content(R.string.content_loading_dialog)
                .contentColorRes(R.color.colorSecondaryText)
                .progress(true, 3)
                .build();
        loadingDialog.show();
    }

    private void hideLoadingDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.hide();
        }
    }


    private void setupClusterManager() {
        clusterManager = new ClusterManager<VenueMarkerItem>(getActivity(), map);
        clusterManager.setOnClusterItemInfoWindowClickListener(MapFragment.this);
        map.setOnMarkerClickListener(clusterManager);
        map.setInfoWindowAdapter(clusterManager.getMarkerManager());
        map.setOnInfoWindowClickListener(clusterManager);

        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<VenueMarkerItem>() {
            @Override
            public boolean onClusterItemClick(VenueMarkerItem venueMarkerItem) {
                clickedClusterItem = venueMarkerItem;

                return false;
            }
        });

        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(new VenueClusterInfoWindow());

    }

    @Override
    public void onClusterItemInfoWindowClick(VenueMarkerItem venueMarkerItem) {
        mapMvpPresenter.onClusterItemInfoWindowClick(venueMarkerItem.getVenueId());
    }

    public void addVenueMarkerItems(ArrayList<Result> results) {
        if (clusterManager == null) {
            return;
        }

        venueMarkerItems = new ArrayList<>();

        for (Result result : results) {
            double lat = result.getGeometry().getLocation().getLat();
            double lng = result.getGeometry().getLocation().getLng();
            LatLng position = new LatLng(lat, lng);
            //
            String title = result.getName();

            VenueMarkerItem venueMarkerItem = new VenueMarkerItem(position, title, "");
            venueMarkerItem.setVenueCategoryImage(result.getIcon());
            venueMarkerItem.setVenueId(result.getPlaceId());

            venueMarkerItems.add(venueMarkerItem);

            clusterManager.addItem(venueMarkerItem);

        }


        clusterManager.cluster();
    }

    public void removeAllVenueMarkerItems() {
        if (venueMarkerItems != null) {
            for (VenueMarkerItem item : venueMarkerItems) {
                clusterManager.removeItem(item);
            }
        }
    }

    private void initUiComponents() {

    }

    public void showLoadingDialog(int titleId, int contentId) {
        searchLoadingDialog = new MaterialDialog.Builder(getActivity())
                .title(titleId)
                .content(contentId)
                .progress(true, 3)
                .show();


    }

    private void dismissSearchLoadingDialog() {
        if (searchLoadingDialog != null && searchLoadingDialog.isShowing()) {
            searchLoadingDialog.dismiss();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiEvents();
        showLoadingDialog();
    }

    private void initUiEvents() {
        buttonMapType.setOnClickListener(this);
        imageButtonSearch.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (MainActivity) context;
        callback.getActivityComponent().inject(this);
        mapMvpPresenter.onAttach(this);
        interactionWithMapFragment = (InteractionWithMapFragment) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void showMessage(int messageId) {
        Snackbar.make(getView(), messageId, 2000).show();
    }

    public void showDialogChooseMapType() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.title_dialog_map_type)
                .items(mapType)
                .itemsCallbackSingleChoice(-1, new ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which,
                                               CharSequence text) {
                        int typeOfMap = mapMvpPresenter.getMapTypeChoose(mapType, text.toString());
                        map.setMapType(typeOfMap);

                        return true;
                    }
                })
                .positiveText(R.string.text_choose)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MyKey.PERMISSIONS_REQUEST_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        map.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        hideLoadingDialog();
        // map.setMyLocationEnabled(true);
        if (ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            initMap();
            // turnOnMyLocationLayer();
            // buildGoogleApiClient();
            // googleApiClient.connect();
            showCurrentLocation();

        } else {
            // Show rationale and request permission.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{permission.ACCESS_FINE_LOCATION},
                        MyKey.PERMISSIONS_REQUEST_LOCATION);
            }
            initMap();
            //   turnOnMyLocationLayer();
            // buildGoogleApiClient();
            // googleApiClient.connect();
            showCurrentLocation();


        }


    }

    private void initMap() {

        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setPadding(0, 80, 0, 0);
        setupClusterManager();

    }

    private void turnOnMyLocationLayer() {
        if (map == null) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MyKey.REQUEST_PERMISSION_ACCESS_FINE);
        }

        if (locationPermissionGranted) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            map.setMyLocationEnabled(false);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            lastKnownLocation = null;
        }
    }

    public void showCurrentLocation() {
        if (lastKnownLocation != null) {

            LayoutInflater inflater = (LayoutInflater) interactionWithMapFragment.getContextOfFragment()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mCustomMarkerView = inflater.inflate(R.layout.custom_user_marker, null);

            //
            String userImageUri = interactionWithMapFragment.getUserImage();

            if (userImageUri == null) {
                //xet anh mac dinh cho vi tri hien tai cua nguoi dung
                currentLocationMarker = createMarker(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()),
                        Commons.getMarkerBitmapFromView(mCustomMarkerView, null, R.drawable.ic_user_default), "Me");
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastKnownLocation.getLatitude(),
                                lastKnownLocation.getLongitude()), MyKey.ZOOM_LEVEl_DEFAULT));
                return;
            }


            Glide.with(interactionWithMapFragment.getContextOfFragment()).
                    load(userImageUri)
                    .asBitmap()
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap,
                                                    GlideAnimation<? super Bitmap> glideAnimation) {
                            currentLocationMarker = createMarker(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()),
                                    Commons.getMarkerBitmapFromView(mCustomMarkerView, bitmap, MyKey.NO_DRAWABLE), "Me");


                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), MyKey.ZOOM_LEVEl_DEFAULT));


                        }
                    });
            //

        } else {
            Log.d(MyKey.MAP_FRAGMENT_TAG, "Current location is null. Using defaults.");
            //map.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        loadingDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        loadingDialog.hide();
        //


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.removeAllViews();
        unbinder.unbind();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_map_type:
                if (mapMvpPresenter != null) {
                    mapMvpPresenter.onClickFloatButtonMapType();
                }
                break;
            case R.id.image_button_search:
                if (mapMvpPresenter != null) {
                    mapMvpPresenter.onClickButtonSearchVenue(editTextSearchVenue.getText().toString());
                }
                break;
        }
    }

    public void openListVenueDialogFragment(final ArrayList<Result> results) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissSearchLoadingDialog();
                interactionWithMapFragment.openListVenueDialogFragment(results);
            }
        }, 3000);

    }

    public String getCurrentUserLocation() {
        if (lastKnownLocation != null) {
            String builder = String.valueOf(lastKnownLocation.getLatitude()) +
                    "," +
                    String.valueOf(lastKnownLocation.getLongitude());

            return builder;
        }

        return null;
    }

    public void openVenueDetailDialogFragment(String venueId) {
        interactionWithMapFragment.openVenueDetailDialogFragment(venueId);
    }

    private Marker createMarker(LatLng position, Bitmap imageMarker, String title) {
        return map.addMarker(new MarkerOptions().position(position)
                .title(title)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(imageMarker)));
    }

    public interface InteractionWithMapFragment {
        String getUserImage();

        void openListVenueDialogFragment(ArrayList<Result> results);

        void openVenueDetailDialogFragment(String venueId);

        Context getContextOfFragment();

        boolean getCheckRequestLocationUpdate();
    }

    public class VenueClusterInfoWindow implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        VenueClusterInfoWindow() {
            myContentsView = getActivity().getLayoutInflater().inflate(
                    R.layout.custom_venue_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;

        }

        @Override
        public View getInfoContents(Marker marker) {
            TextView title = ((TextView) myContentsView
                    .findViewById(R.id.text_venue_name));
            title.setText(clickedClusterItem.getTitle());


            return myContentsView;
        }
    }

}
