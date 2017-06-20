package com.example.android.whereareyougo.ui.ui.usersetting;



import android.net.Uri;
import android.support.annotation.NonNull;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;

import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.Validations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 13/04/2017.
 */

public class UserSettingPresenter<V extends UserSettingView> extends BasePresenter<V> implements
    UserSettingMvpPresenter<V> {


  @Inject
  public UserSettingPresenter(
      DataManager dataManager) {
    super(dataManager);

  }


  @Override
  public void onClickButtonEdit() {
    getMvpView().setButtonEdiVisibility(false);
    getMvpView().setButtonSaveVisibility(true);
    getMvpView().setEnabledForEditTexts(true);
    getMvpView().setEnableForButtonSelectImage(true);
  }

  @Override
  public void onClickButtonSave(User user, String oldPassword) {
    if (user != null){
      if (checkUserInfoBeforeSave(user)){
        //save data
        saveUserData(user,oldPassword);
        getMvpView().setButtonSaveVisibility(false);
        getMvpView().setButtonEdiVisibility(true);
        getMvpView().setEnableForButtonSelectImage(false);
        getMvpView().setEnabledForEditTexts(false);
      }else{
        // show error
      }
    }
  }

  @Override
  public void onClickButtonSelectImage() {
    getMvpView().pickImageFromGallery();
  }

  private boolean checkUserInfoBeforeSave(User user){
    boolean checkUserName = checkName(user.getName());
    boolean checkUserPassword = checkPassword(user.getPassword());
    boolean checkUserPhone = checkPhoneNumber(user.getPhoneNumber());

    return checkUserName && checkUserPassword && checkUserPhone;
  }

  private boolean checkPassword(String password) {
    if (password.isEmpty()) {
      getMvpView().showErrorOnEditTextPassword(
          getMvpView().getStringFromStringResource(R.string.error_password_empty));
      return false;
    }

    if (!Validations.checkPassword(password)) {
      getMvpView().showErrorOnEditTextPassword(
          getMvpView().getStringFromStringResource(R.string.error_password));
      return false;
    }

    return true;
  }

  private boolean checkName(String name) {
    if (name.isEmpty()) {
      getMvpView().showErrorOnEditTextName(
          getMvpView().getStringFromStringResource(R.string.error_name_empty));
      return false;
    }

    if (!Validations.checkFullName(name)) {
      getMvpView().showErrorOnEditTextName(
          getMvpView().getStringFromStringResource(R.string.error_name_invalid));
      return false;
    }

    return true;
  }

  private boolean checkPhoneNumber(String phoneNumber) {
    if (!Validations.checkPhoneNumber(phoneNumber)) {
      getMvpView().showErrorOnEditTextPhone(
          getMvpView().getStringFromStringResource(R.string.error_phonenumber_invalid));
      return false;
    }

    return true;
  }

  public void saveUserData(final User user, final String oldPassword){
    final DatabaseReference userRef = getDataManager().getUserReference();
    final Uri userImageUri = Commons.convertStringToUri(user.getImageUrl());
    StorageReference photosRef = getDataManager().getUserPhotoReference().child(userImageUri.getLastPathSegment());

    photosRef.putFile(userImageUri)
             .addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
               @Override
               public void onSuccess(TaskSnapshot taskSnapshot) {
                 @SuppressWarnings("VisibleForTests")Uri userPhotoUri = taskSnapshot.getDownloadUrl();
                 user.setImageUrl(userPhotoUri.toString());
                 //change user password
                 getDataManager().changeUserPassword(user.getEmail(),oldPassword,user.getPassword());
                 if (!getDataManager().getUserPassword().isEmpty()){
                   getDataManager().saveUserPassword(user.getPassword());
                 }
                 userRef.setValue(user);
               }
             });


    //
  }



}
