package com.example.android.whereareyougo.ui.ui.map;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackSingleChoice;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.main.MainActivity;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MapFragment extends BaseFragment implements MapMvpView, OnMapReadyCallback,
        OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

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
    private GoogleApiClient googleApiClient;
    private Location lastKnownLocation;
    private Marker currentLocationMarker;
    private InteractionWithMapFragment interactionWithMapFragment;
    private boolean locationPermissionGranted = false;
    private final String[] mapType = new String[]{"NORMAL",
            "HYBRID",
            "SATELLITE",
            "TERRAIN",
            "NONE"};


    public static MapFragment newInstance() {
        MapFragment mapFragment = new MapFragment();

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


        return view;

    }

    private void initUiComponents() {

    }

    public void showLoadingDialog(int titleId, int contentId){
       final MaterialDialog dialog =  new MaterialDialog.Builder(getActivity())
                .title(titleId)
                .content(contentId)
                .progress(true,3)
                .show();

       Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               dialog.dismiss();
           }
       },3000);
    }

    protected synchronized void buildGoogleApiClient() {
        //setup google api client
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiEvents();
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
        interactionWithMapFragment = (InteractionWithMapFragment) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapMvpPresenter.onAttach(MapFragment.this);
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
                    if (ContextCompat.checkSelfPermission(getContext(),
                            permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        map.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // map.setMyLocationEnabled(true);
        if (ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            initMap();
            turnOnMyLocationLayer();
            buildGoogleApiClient();
            googleApiClient.connect();

        } else {
            // Show rationale and request permission.
            requestPermissions(new String[]{permission.ACCESS_FINE_LOCATION},
                    MyKey.PERMISSIONS_REQUEST_LOCATION);
            initMap();
            turnOnMyLocationLayer();
            buildGoogleApiClient();
            googleApiClient.connect();


        }


    }

    private void initMap() {

        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setPadding(0, 80, 0, 0);
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

    private void showCurrentLocation() {

        if (locationPermissionGranted) {

            if (ActivityCompat.checkSelfPermission(getActivity(), permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(googleApiClient);

        }

        if (lastKnownLocation != null) {

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View mCustomMarkerView = inflater.inflate(R.layout.custom_user_marker, null);

            //
            String userImageUri = interactionWithMapFragment.getUserImage();

            if (userImageUri == null) {
                //xet anh mac dinh cho vi tri hien tai cua nguoi dung
                currentLocationMarker = createMarker(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()),
                        getMarkerBitmapFromView(mCustomMarkerView, null, R.drawable.ic_user_default), "Me");
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastKnownLocation.getLatitude(),
                                lastKnownLocation.getLongitude()), MyKey.ZOOM_LEVEl_DEFAULT));
                return;
            }


            Glide.with(getActivity().getApplicationContext()).
                    load(userImageUri)
                    .asBitmap()
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap,
                                                    GlideAnimation<? super Bitmap> glideAnimation) {
                            currentLocationMarker = createMarker(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()),
                                    getMarkerBitmapFromView(mCustomMarkerView, bitmap, MyKey.NO_DRAWABLE), "Me");


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
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
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
                if(mapMvpPresenter != null){
                    mapMvpPresenter.onClickButtonSearchVenue(editTextSearchVenue.getText().toString());
                }
                break;
        }
    }

    public void openListVenueDialogFragment(ArrayList<Result> results){
        interactionWithMapFragment.openListVenueDialogFragment(results);
    }

    public String getCurrentUserLocation(){
        if (lastKnownLocation != null){
            StringBuilder builder = new StringBuilder();
            builder.append(String.valueOf(lastKnownLocation.getLatitude()))
                   .append(",")
                   .append(String.valueOf(lastKnownLocation.getLongitude()));

            return builder.toString();
        }

        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        showCurrentLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
    }

    private Bitmap getMarkerBitmapFromView(View view, Bitmap bitmap, int drawableId) {
        CircleImageView mMarkerImageView = (CircleImageView) view.findViewById(R.id.image_user);
        if (drawableId == MyKey.NO_DRAWABLE) {
            mMarkerImageView.setImageBitmap(bitmap);
        } else {
            mMarkerImageView.setImageResource(drawableId);
        }

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        }
        view.draw(canvas);
        return returnedBitmap;
    }


}
