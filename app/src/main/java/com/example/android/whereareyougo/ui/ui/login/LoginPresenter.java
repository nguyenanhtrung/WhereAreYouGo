package com.example.android.whereareyougo.ui.ui.login;

import android.support.annotation.NonNull;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
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
    getDataManager().signInWithEmailAndPassworđ(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
              getMvpView().showNotification(R.string.login_successful);
              getMvpView().openMainActivity();
            }
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {

          }
        });
  }
}
