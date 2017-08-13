package com.example.android.whereareyougo.ui.ui.locationupdatesetting;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public class LocationUpdateSettingFragment extends BaseFragment implements LocationUpdateSettingView {

    @Inject
    LocationUpdateSettingMvpPresenter<LocationUpdateSettingView> presenter;
    @BindView(R.id.label_update_location_interval)
    TextView labelUpdateLocationInterval;
    @BindView(R.id.edit_text_update_interval)
    MaterialEditText editTextUpdateInterval;
    @BindView(R.id.label_minute)
    TextView labelMinute;
    @BindView(R.id.divider_location_distance)
    View dividerLocationDistance;
    @BindView(R.id.label_update_location)
    TextView labelUpdateLocation;
    @BindView(R.id.image_button_edit_interval)
    ImageButton imageButtonEditInterval;
    @BindView(R.id.switch_update_location)
    Switch switchUpdateLocation;
    @BindView(R.id.divider_update_location)
    View dividerUpdateLocation;
    @BindView(R.id.label_location_priority)
    TextView labelLocationPriority;
    @BindView(R.id.radio_button_balance)
    RadioButton radioButtonBalance;
    @BindView(R.id.radio_button_high_accurancy)
    RadioButton radioButtonHighAccurancy;
    @BindView(R.id.radio_button_low_power)
    RadioButton radioButtonLowPower;
    @BindView(R.id.radio_group_priority)
    RadioGroup radioGroupPriority;
    @BindView(R.id.divider_priority_location)
    View dividerPriorityLocation;
    @BindView(R.id.label_location_distance)
    TextView labelLocationDistance;
    @BindView(R.id.edit_text_distance)
    MaterialEditText editTextDistance;
    @BindView(R.id.label_meter)
    TextView labelMeter;
    @BindView(R.id.image_button_edit_distance)
    ImageButton imageButtonEditDistance;
    Unbinder unbinder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_update_location, container, false);
        unbinder = ButterKnife.bind(this, view);
        //

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
