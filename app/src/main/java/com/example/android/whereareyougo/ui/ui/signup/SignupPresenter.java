package com.example.android.whereareyougo.ui.ui.signup;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.Validations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.ProviderQueryResult;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class SignupPresenter<V extends SignupView> extends BasePresenter<V> implements
    SignupMvpPresenter<V> {

  private boolean isEmailRegistered = false;

  public void setEmailRegistered(boolean emailRegistered) {
    isEmailRegistered = emailRegistered;
  }

  @Inject
  public SignupPresenter(DataManager dataManager) {
    super(dataManager);
  }


  private void checkEmailRegistered(String email) {
    getDataManager().getProviderForEmail(email)
        .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
          @Override
          public void onComplete(@NonNull Task<ProviderQueryResult> task) {
            if (!task.getResult().getProviders().isEmpty()) {
              getMvpView().showErrorOnEditTextEmail(
                  getMvpView().getStringFromStringResource(R.string.error_email_registered));
              setEmailRegistered(true);
            }
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            //show network error...
          }
        });
  }


  private boolean checkSignup(String email, String password, String name) {
    boolean checkEmail = checkEmail(email);
    boolean checkPassword = checkPassword(password);
    boolean checkName = checkName(name);

    return checkEmail && checkPassword && checkName;
  }

  private boolean checkEmail(String email) {
    if (email.isEmpty()) {
      getMvpView().showErrorOnEditTextEmail(
          getMvpView().getStringFromStringResource(R.string.error_email_empty));
      return false;
    }

    if (!Validations.checkEmail(email)) {
      getMvpView().showErrorOnEditTextEmail(
          getMvpView().getStringFromStringResource(R.string.error_email_invalid));
      return false;
    }

    //check if email already registered here
    checkEmailRegistered(email);

    if (isEmailRegistered){
      return false;
    }



    return true;
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


  @Override
  public void onClickButtonSignup(String email, String password, String name) {
    //kiem tra network
    if (getMvpView().isNetworkConnected()) {
      if (checkSignup(email,password,name)){
        signupWithEmailAndPassword(email,password,name);

      }
    } else {
      //show network error on snack bar or toast
    }

  }

  private void signupWithEmailAndPassword(final String email, final String password, final String name) {
    getDataManager().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            //show notification signup successful
            if (task.isSuccessful()){
              getMvpView().showNotification(R.string.register_successful);
              String userId = task.getResult().getUser().getUid();
              if (userId != null){
                getDataManager().writeNewUser(userId,email,password,name);
              }

              getMvpView().updateUserInfoForLoginActivity(email,password);
              getMvpView().closeDialog();


            }else{
              getMvpView().showNotification(R.string.error_register_fail);
            }

          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            //show notification signup failure
            getMvpView().showNotification(R.string.error_register_fail);
          }
        });
  }


}
