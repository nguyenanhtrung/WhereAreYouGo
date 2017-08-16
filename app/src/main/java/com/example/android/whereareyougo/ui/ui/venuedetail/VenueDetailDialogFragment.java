package com.example.android.whereareyougo.ui.ui.venuedetail;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.VenuePhoto;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;
import com.example.android.whereareyougo.ui.ui.adapter.VenuePhotosRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.example.android.whereareyougo.ui.utils.NetworkUtil;
import com.google.android.gms.maps.model.LatLng;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 28/06/2017.
 */

public class VenueDetailDialogFragment extends DialogFragment implements VenueDetailView,View.OnClickListener {

    @Inject
    VenueDetailMvpPresenter<VenueDetailView> venueDetailPresenter;
    InteractionWithVenueDetailFragment interaction;
    @BindView(R.id.recycler_venue_photos)
    UltimateRecyclerView recyclerVenuePhotos;
    @BindView(R.id.text_venue_name)
    TextView textVenueName;
    @BindView(R.id.text_venue_address)
    TextView textVenueAddress;
    @BindView(R.id.text_price_level)
    TextView textPriceLevel;
    @BindView(R.id.text_point)
    TextView textPoint;
    @BindView(R.id.text_venue_phone)
    TextView textVenuePhone;
    @BindView(R.id.text_venue_status)
    TextView textVenueStatus;
    @BindView(R.id.image_button_call)
    ImageButton imageButtonCall;
    @BindView(R.id.image_button_find_way)
    ImageButton imageButtonFindWay;
    @BindView(R.id.image_button_save)
    ImageButton imageButtonSave;
    @BindView(R.id.button_close)
    BootstrapButton buttonClose;
    Unbinder unbinder;

    private List<VenuePhoto> venuePhotos;




    private String venueId;


    public static VenueDetailDialogFragment newInstance(String venueId) {
        VenueDetailDialogFragment fragment = new VenueDetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("venueid", venueId);
        fragment.setArguments(bundle);

        return fragment;
    }

    public void showVenueName(String venueName) {
        if (venueName == null | venueName.isEmpty()) {
            textVenueName.setText("");
        }else{
            textVenueName.setText(venueName);
        }
    }

    public void showVenueAddress(String venueAddress){
        if (venueAddress == null | venueAddress.isEmpty()) {
            textVenueAddress.setText("");
        }else{
            textVenueAddress.setText(venueAddress);
        }
    }

    public void setupRecyclerViewVenuePhotos(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerVenuePhotos.setLayoutManager(linearLayoutManager);

        VenuePhotosRecyclerViewAdapter adapter = new VenuePhotosRecyclerViewAdapter(getActivity(),venuePhotos);
        recyclerVenuePhotos.setAdapter(adapter);
    }

    public void setDataForRecyclerViewVenuePhotos(List<VenuePhoto> venuePhotos){
        this.venuePhotos = venuePhotos;

        //set image empty if venuePhotos is empty or null
        if (venuePhotos.isEmpty()){
            
        }
    }

    public void showVenuePriceLevel(int priceLevel){
        if (priceLevel == -1){
            textPriceLevel.setText("");
        }else if (priceLevel == 0){
            textPriceLevel.setText(R.string.price_level_free);
        }else if (priceLevel == 1){
            textPriceLevel.setText(R.string.price_level_inexpensive);
        }else if (priceLevel == 2){
            textPriceLevel.setText(R.string.price_level_moderate);
        }else if (priceLevel == 3){
            textPriceLevel.setText(R.string.price_level_expensive);
        }else if (priceLevel == 4){
            textPriceLevel.setText(R.string.price_level_veryexpensive);
        }
    }

    public void showVenueRating(double rating){
        if (rating == -1){
            textPoint.setText("");
        }else{
            textPoint.setText(String.valueOf(rating));
        }
    }

    public void showVenuePhoneNumber(String phoneNumber){
        if (phoneNumber == null) {
            textVenuePhone.setText("");
        }else{
            if (phoneNumber.isEmpty()){
                textVenuePhone.setText("");
                return;
            }
            textVenuePhone.setText(phoneNumber);
        }
    }

    public void showVenueStatus(boolean status){
        if (status){
            textVenueStatus.setText(R.string.text_venue_open_time);
            textVenueStatus.setTextColor(ContextCompat.getColor(getActivity(),R.color.color_venue_status_open));
        }else{
            textVenueStatus.setText(R.string.text_venue_close_time);
            textVenueStatus.setTextColor(ContextCompat.getColor(getActivity(),R.color.color_venue_status_close));
        }
    }

    private void showVenueDetail(String venueId){
        if (venueDetailPresenter != null){
            venueDetailPresenter.showVenueDetailByVenueId(venueId);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setSizeOfDialog();
        showVenueDetail(venueId);
    }

    private void setSizeOfDialog() {
        int width = 750;
        int height = 1100;
        getDialog().getWindow().setLayout(
                width, height
        );
        getDialog().getWindow().setGravity(Gravity.CENTER);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_detail_dialog, container, false);

        Bundle bundle = getArguments();
        venueId = bundle.getString("venueid");

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        venueDetailPresenter.onAttach(VenueDetailDialogFragment.this);
        initEvents();

    }

    private void initEvents() {
        imageButtonCall.setOnClickListener(this);
        imageButtonFindWay.setOnClickListener(this);
        imageButtonSave.setOnClickListener(this);
        buttonClose.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interaction = (InteractionWithVenueDetailFragment) context;
        interaction.getActivityComponent().inject(this);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtil.isNetworkConnected(getActivity());
    }

    @Override
    public void onError(String message, Activity activity) {

    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void drawPolyLineOnMap(LatLng destination){
        interaction.drawPolyLineOnMap(destination);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_button_call:
                if (venueDetailPresenter != null){
                    venueDetailPresenter.onClickImageButtonCallPhone();
                }
                break;
            case R.id.image_button_find_way:
                if(venueDetailPresenter != null){
                    venueDetailPresenter.onClickButtonFindWay();
                }
                break;
            case R.id.image_button_save:
                if (venueDetailPresenter != null){
                    venueDetailPresenter.onClickButtonSaveVenue();
                }
                break;
            case R.id.button_close:
                if (venueDetailPresenter != null){
                    venueDetailPresenter.onClickButtonCloseDialog();
                }
                break;
        }
    }

    public void showMessage(int messageId){
        Snackbar snackbar = Snackbar.make(getView(),messageId,2000);
        snackbar.show();
    }



    @Override
    public void dismissDialog() {
        dismiss();
    }

    public void checkCallPhonePermissions(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        MyKey.MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public void callPhone(){
        String phoneNumber = "tel:" + textVenuePhone.getText().toString(); // fake data
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
        startActivity(intent);
    }

    public interface InteractionWithVenueDetailFragment {
        ActivityComponent getActivityComponent();
        void drawPolyLineOnMap(LatLng destination);
    }
}
