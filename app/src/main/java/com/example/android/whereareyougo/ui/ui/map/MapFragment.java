package com.example.android.whereareyougo.ui.ui.map;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackSingleChoice;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.main.MainActivity;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.wang.avi.AVLoadingIndicatorView;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MapFragment extends BaseFragment implements MapMvpView, OnMapReadyCallback,
    OnClickListener {

  @Inject
  MapMvpPresenter<MapMvpView> mapMvpPresenter;
  @BindView(R.id.edit_text_search_venue)
  EditText editTextSearchVenue;
  @BindView(R.id.image_button_search)
  ImageButton imageButtonSearch;
  @BindView(R.id.search_loading)
  AVLoadingIndicatorView searchLoading;
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

    mapView = (MapView) view.findViewById(R.id.map_view);
    MapsInitializer.initialize(this.getActivity());
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);

    return view;

  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initUiEvents();
  }


  private void initUiEvents() {
    buttonMapType.setOnClickListener(this);
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

  public void showDialogChooseMapType() {
    new MaterialDialog.Builder(getActivity())
        .title(R.string.title_dialog_map_type)
        .items(mapType)
        .itemsCallbackSingleChoice(-1, new ListCallbackSingleChoice() {
          @Override
          public boolean onSelection(MaterialDialog dialog, View itemView, int which,
              CharSequence text) {
            int typeOfMap = mapMvpPresenter.getMapTypeChoose(mapType,text.toString());
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
    } else {
      // Show rationale and request permission.
      requestPermissions(new String[]{permission.ACCESS_FINE_LOCATION},
          MyKey.PERMISSIONS_REQUEST_LOCATION);
      initMap();
    }


  }

  private void initMap() {
    showCurrentLocation();
    map.getUiSettings().setMyLocationButtonEnabled(true);
    map.setPadding(0, 80, 0, 0);
  }

  private void showCurrentLocation() {
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
        if (mapMvpPresenter != null){
          mapMvpPresenter.onClickFloatButtonMapType();
        }
        break;
    }
  }
}
