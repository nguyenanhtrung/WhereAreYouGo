package com.example.android.whereareyougo.ui.ui.login;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.example.android.whereareyougo.ui.utils.StringUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class LoginPresenter<V extends LoginView> extends BasePresenter<V> implements
    LoginMvpPresenter<V> {

  @Inject
  public LoginPresenter(DataManager dataManager) {
    super(dataManager);
  }


  @Override
  public void onClickButtonSignup() {
    getMvpView().openSignupFragment();
  }

  @Override
  public void onCLickButtonSignin(String email, String password) {
    boolean isRememberLogin = getMvpView().getValueFrormCheckRemember();
    getDataManager().saveCheckRememberLogin(isRememberLogin);
    saveEmailAndPassword(email,password);
    getMvpView().hideKeyboard();
    getMvpView().showLoading();
    getMvpView().setVisibilityForComponents(View.INVISIBLE);
    loginWithEmailAndPassword(email,password);
  }

  private void loginWithEmailAndPassword(final String email, final String password){
    getDataManager().signInWithEmailAndPassworđ(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
              getMvpView().hideLoading();
              getMvpView().openMainActivity();
            }
            else{
              getMvpView().onError(R.string.login_fail);
              getMvpView().hideLoading();
              getMvpView().setVisibilityForComponents(View.VISIBLE);
            }
          }
        });
  }

  public void onClickConnectAgainDisconnectNetworkDialog(String email, String password){
      loginWithEmailAndPassword(email,password);
  }

  private void saveEmailAndPassword(String email, String password){
    if (!email.isEmpty()){
      getDataManager().saveUserEmail(email);
    }

    if (!password.isEmpty()){
      getDataManager().saveUserPassword(password);
    }
  }

  public void loginWithLoginRemember(){
    boolean isLoginRemember = getDataManager().getCheckRememberLogin();

    if (!isLoginRemember){
      getMvpView().setVisibilityForComponents(View.VISIBLE);
      return;
    }

    String email = getDataManager().getUserEmail();
    String password = getDataManager().getUserPassword();
    if (email != "" && password != ""){
      loginWithEmailAndPassword(email,password);
    }


  }
}
