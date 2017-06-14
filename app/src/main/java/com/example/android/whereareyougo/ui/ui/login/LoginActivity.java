package com.example.android.whereareyougo.ui.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseActivity;
import com.example.android.whereareyougo.ui.ui.main.MainActivity;
import com.example.android.whereareyougo.ui.ui.signup.SignupDialogFragment;
import com.example.android.whereareyougo.ui.ui.signup.SignupDialogFragment.InteractionWithSignupFragment;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wang.avi.AVLoadingIndicatorView;
import javax.inject.Inject;


public class LoginActivity extends BaseActivity implements LoginView, OnClickListener,
    InteractionWithSignupFragment {

  @Inject
  LoginMvpPresenter<LoginView> loginMvpPresenter;
  @BindView(R.id.edit_text_email)
  MaterialEditText editTextEmail;
  @BindView(R.id.edit_text_password)
  MaterialEditText editTextPassword;
  @BindView(R.id.button_sign_in)
  BootstrapButton buttonSignIn;
  @BindView(R.id.button_sign_up)
  BootstrapButton buttonSignUp;
  @BindView(R.id.check_save_account)
  CheckBox checkSaveAccount;
  @BindView(R.id.text_forgot_password)
  TextView textForgotPassword;
  @BindView(R.id.loading_login)
  AVLoadingIndicatorView loadingLogin;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    getActivityComponent().inject(this);
    loginMvpPresenter.onAttach(this);

    //
    initEvents();

  }

  private void initEvents() {
    buttonSignIn.setOnClickListener(this);
    buttonSignUp.setOnClickListener(this);
  }

  @Override
  public void setTitleToolbar(String title) {

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.button_sign_in:
        if (loginMvpPresenter != null) {
          loginMvpPresenter.onCLickButtonSignin(editTextEmail.getText().toString(),
              editTextPassword.getText().toString());
        }
        break;
      case R.id.button_sign_up:
        if (loginMvpPresenter != null) {
          loginMvpPresenter.onClickButtonSignup();
        }
        break;
    }
  }

  private void setVisibilityForComponents(int visibility){
    editTextEmail.setVisibility(visibility);
    editTextPassword.setVisibility(visibility);
    textForgotPassword.setVisibility(visibility);
    checkSaveAccount.setVisibility(visibility);
    buttonSignUp.setVisibility(visibility);
    buttonSignIn.setVisibility(visibility);
  }

  @Override
  public void showLoading() {
    setVisibilityForComponents(View.GONE);
    loadingLogin.show();
  }

  @Override
  public void hideLoading() {
    loadingLogin.hide();
  }

  @Override
  public void openSignupFragment() {
    SignupDialogFragment dialogFragment = SignupDialogFragment.newInstance();
    dialogFragment.show(getSupportFragmentManager(), MyKey.SIGNUP_DIALOG_FRAGMENT_TAG);

  }

  @Override
  public void openMainActivity() {
    final Intent intent = new Intent(LoginActivity.this, MainActivity.class);

    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        hideLoading();
        startActivity(intent);
      }
    },3000);

  }

  @Override
  public void showNotification(int messageId) {
    Snackbar snackbar = Snackbar.make(textForgotPassword,messageId,2000);
    snackbar.show();
  }


  @Override
  public void updateUserInfoLogin(String email, String password) {
    editTextEmail.setText(email);
    editTextPassword.setText(password);
  }


}
