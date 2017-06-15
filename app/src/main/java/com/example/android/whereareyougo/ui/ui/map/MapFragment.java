package com.example.android.whereareyougo.ui.ui.map;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.main.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MapFragment extends BaseFragment implements MapMvpView, OnMapReadyCallback {

  @Inject
  MapMvpPresenter<MapMvpView> mapMvpPresenter;
  private GoogleMap map;
  private MapView mapView;

  public static MapFragment newInstance() {
    MapFragment mapFragment = new MapFragment();

    return mapFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_venue_map, container, false);

    MapsInitializer.initialize(this.getActivity());
    mapView = (MapView) view.findViewById(R.id.map_view);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);

    return view;

  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Callback callback = (MainActivity) context;
    callback.getActivityComponent().inject(this);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mapMvpPresenter.onAttach(MapFragment.this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    map = googleMap;
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
    map.setMyLocationEnabled(true);
    map.getUiSettings().setMyLocationButtonEnabled(true);
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

}
