package com.example.android.whereareyougo.ui.ui.signup;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.Validations;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class SignupPresenter<V extends SignupView> extends BasePresenter<V> implements
    SignupMvpPresenter<V> {

  @Inject
  public SignupPresenter(DataManager dataManager) {
    super(dataManager);
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

    return true;
  }

  private boolean checkPassword(String password){
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

  private boolean checkName(String name){
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
    if (checkSignup(email,password,name)){

    }else{

    }
  }
}
