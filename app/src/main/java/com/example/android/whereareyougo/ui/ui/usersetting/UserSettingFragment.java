package com.example.android.whereareyougo.ui.ui.usersetting;

import static android.app.Activity.RESULT_OK;


import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.main.MainActivity;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import de.hdodenhof.circleimageview.CircleImageView;


import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;


public class UserSettingFragment extends BaseFragment implements UserSettingView,
    View.OnClickListener {


  @Inject
  UserSettingMvpPresenter<UserSettingView> userSettingMvpPresenter;

  Unbinder unbinder;
  @BindView(R.id.image_user)
  CircleImageView imageUser;
  @BindView(R.id.button_select_image)
  BootstrapButton buttonSelectImage;
  @BindView(R.id.edit_text_user_name)
  MaterialEditText editTextUserName;
  @BindView(R.id.edit_text_phone)
  MaterialEditText editTextPhone;
  @BindView(R.id.edit_text_email)
  MaterialEditText editTextEmail;
  @BindView(R.id.edit_text_password)
  MaterialEditText editTextPassword;
  @BindView(R.id.button_save)
  BootstrapButton buttonSave;
  @BindView(R.id.button_edit)
  BootstrapButton buttonEdit;

  private User currentUser;
  private String oldPassword;

  public static UserSettingFragment getInstance(User currentUser) {
    UserSettingFragment fragment = new UserSettingFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(MyKey.USER_PARCELABLE_KEY,currentUser);

    fragment.setArguments(bundle);

    return fragment;
  }

  public UserSettingFragment() {

  }




  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_account_setting, container, false);
    unbinder = ButterKnife.bind(this, view);

    initUiComponents();
    showCurrentUserInfo();
    checkPermisstionsRead();

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    userSettingMvpPresenter.onAttach(this);
    initUiEvents();
  }

  private void initUiComponents(){
    setEnabledForEditTexts(false);
    buttonSelectImage.setEnabled(false);
  }

  public void showErrorOnEditTextEmail(String error){
    if (error != null){
      editTextEmail.setError(error);
    }
  }

  public String getStringFromStringResource(int stringId) {
    return getResources().getString(stringId);
  }

  public void showErrorOnEditTextName(String error){
    if (error != null){
      editTextUserName.setError(error);
    }
  }

  public void showErrorOnEditTextPassword(String error){
    if (error != null){
      editTextPassword.setError(error);
    }
  }

  public void showErrorOnEditTextPhone(String error){
    if (error != null){
      editTextPhone.setError(error);
    }
  }

  public void setEnabledForEditTexts(boolean isEnable){
    editTextEmail.setEnabled(isEnable);
    editTextUserName.setEnabled(isEnable);
    editTextPassword.setEnabled(isEnable);
    editTextPhone.setEnabled(isEnable);
  }

  private void initUiEvents(){
    buttonSelectImage.setOnClickListener(this);
    buttonEdit.setOnClickListener(this);
    buttonSave.setOnClickListener(this);
  }

  public void setButtonEdiVisibility(boolean isVisible){
    if (isVisible){
      buttonEdit.setVisibility(View.VISIBLE);
    }else{
      buttonEdit.setVisibility(View.INVISIBLE);
    }
  }

  public void setEnableForButtonSelectImage(boolean isEnable){
    buttonSelectImage.setEnabled(isEnable);
  }

  public void setButtonSaveVisibility(boolean isVisible){
    if (isVisible){
      buttonSave.setVisibility(View.VISIBLE);
    }else{
      buttonSave.setVisibility(View.INVISIBLE);
    }
  }

  private void showCurrentUserInfo(){
    Bundle bundle = getArguments();
    currentUser = bundle.getParcelable(MyKey.USER_PARCELABLE_KEY);
    if (currentUser != null){
      setUserInfo(editTextUserName,currentUser.getName());
      setUserInfo(editTextEmail,currentUser.getEmail());
      setUserInfo(editTextPassword,currentUser.getPassword());
      setUserInfo(editTextPhone,currentUser.getPhoneNumber());
      setUserImage(imageUser,currentUser.getImageUrl());
      //
      oldPassword = currentUser.getPassword();
    }
  }

  private void setUserInfo(MaterialEditText editText, String content){
    if (content != null){
      editText.setText(content);
    }else{
      editText.setText("");
    }
  }

  private void setUserImage(CircleImageView userImage, String userImageUri){
    if (userImageUri == null){
      Glide.with(getActivity())
           .load(R.drawable.ic_user_default)
           .into(userImage);
    }else{
      //set user image by imageUri from firebase database
      Glide.with(this)
           .load(userImageUri)
           .into(userImage);
    }
  }

  public void pickImageFromGallery(){
    Matisse.from(this)
            .choose(MimeType.allOf())
            .countable(true)
            .maxSelectable(1)
            .thumbnailScale(0.85f)
            .imageEngine(new GlideEngine())
            .forResult(MyKey.REQUEST_CODE_PICK_IMAGE);


  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == MyKey.REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK){
      List<Uri> path = Matisse.obtainResult(data);
      if (!path.isEmpty()|| path != null){
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
          @Override
          public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            imageUser.setImageBitmap(resource);
          }
        };

        Glide.with(this)
                .load(path.get(0))
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(target);
       currentUser.setImageUrl(path.get(0).toString());
      }

    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.button_edit:
        if (userSettingMvpPresenter != null){
          userSettingMvpPresenter.onClickButtonEdit();
        }
        break;
      case R.id.button_save:
        if (userSettingMvpPresenter != null){
          //User user = new User();
          currentUser.setName(editTextUserName.getText().toString());
          currentUser.setEmail(editTextEmail.getText().toString());
          currentUser.setPassword(editTextPassword.getText().toString());
          currentUser.setPhoneNumber(editTextPhone.getText().toString());

          //
          userSettingMvpPresenter.onClickButtonSave(currentUser,oldPassword);
        }
        break;
      case R.id.button_select_image:
        if (userSettingMvpPresenter != null){
          userSettingMvpPresenter.onClickButtonSelectImage();
        }
        break;
    }
  }

  public void checkPermisstionsRead(){
    if (ContextCompat.checkSelfPermission(getActivity(),permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED ) {
      requestPermissions(new String[]{permission.READ_EXTERNAL_STORAGE},
          MyKey.REQUEST_PERMISSION_READ_EXTERNAL);

      // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
      // app-defined int constant

      return;
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Callback callback = (Callback) context;
    callback.getActivityComponent().inject(this);
  }
}
