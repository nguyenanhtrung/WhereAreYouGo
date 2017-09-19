package com.example.android.whereareyougo.ui.ui.profile;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;
import com.example.android.whereareyougo.ui.ui.base.BaseDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 10/07/2017.
 */

public class ProfileDialogFragment extends BaseDialogFragment implements ProfileView,View.OnClickListener {
    @Inject
    ProfileMvpPresenter<ProfileView> presenter;
    @BindView(R.id.circle_user_image)
    CircleImageView circleUserImage;
    @BindView(R.id.text_user_name)
    TextView textUserName;
    @BindView(R.id.text_user_email)
    TextView textUserEmail;
    @BindView(R.id.text_user_phone)
    TextView textUserPhone;
    @BindView(R.id.text_user_address)
    TextView textUserAddress;
    @BindView(R.id.text_num_of_friends)
    TextView textNumOfFriends;
    @BindView(R.id.text_num_of_follower)
    TextView textNumOfFollower;
    @BindView(R.id.text_num_of_favorite_place)
    TextView textNumOfFavoritePlace;
    Unbinder unbinder;
    @BindView(R.id.button_close_dialog)
    Button buttonCloseDialog;
    private InteractionWithProfileDialog interaction;
    private User currentUser;


    public static ProfileDialogFragment newInstance(User user) {
        ProfileDialogFragment fragment = new ProfileDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        //



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUiEvents();
    }

    private void initUiEvents() {
        buttonCloseDialog.setOnClickListener(this);
    }



    @Override
    public void onResume() {
        super.onResume();
        setSizeOfDialog();
        showUserProfile();
    }

    private void setSizeOfDialog() {
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        ;
        int height = 1100;
        getDialog().getWindow().setLayout(
                width, height
        );


    }

    public void showNumberOfFriends(String numberOfFriends){
        textNumOfFriends.setText(String.valueOf(numberOfFriends));
    }

    private void showUserProfile() {
        if (currentUser != null) {
            showUserImage(currentUser.getImageUrl());
            showUserName(currentUser.getName());
            showUserEmail(currentUser.getEmail());
            showUserPhoneNumber(currentUser.getPhoneNumber());
            presenter.getNumberOfFriends(currentUser.getUserID());
            //showNumberOfFriends();
        }
    }

    private void showUserName(String name) {
        if (name.isEmpty() || name == null) {
            textUserName.setText("");
        } else {
            textUserName.setText(name);
        }
    }

    private void showUserImage(String userImageUrl) {
        if (userImageUrl == null) {
            Glide.with(getActivity())
                    .load(R.drawable.ic_user_default)
                    .into(circleUserImage);
        } else {
            Glide.with(getActivity())
                    .load(userImageUrl)
                    .into(circleUserImage);
        }
    }

    private void showUserEmail(String email) {
        if (email.isEmpty() || email == null) {
            textUserEmail.setText("");
        } else {
            textUserEmail.setText(email);
        }
    }

    private void showUserPhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty() || phoneNumber == null) {
            textUserPhone.setText("");
        } else {
            textUserPhone.setText(phoneNumber);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            currentUser = bundle.getParcelable("user");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interaction = (InteractionWithProfileDialog) context;
        interaction.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void dismissDialog(){
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_close_dialog:
                if (presenter != null){
                    presenter.onClickButtonCloseDialog();
                }
                break;
        }
    }

    public interface InteractionWithProfileDialog {
        ActivityComponent getActivityComponent();
    }
}
