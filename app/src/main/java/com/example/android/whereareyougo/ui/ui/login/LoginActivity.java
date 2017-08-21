package com.example.android.whereareyougo.ui.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

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
  Button buttonSignIn;
  @BindView(R.id.button_sign_up)
  Button buttonSignUp;
  @BindView(R.id.check_save_account)
  CheckBox checkSaveAccount;
  @BindView(R.id.text_forgot_password)
  TextView textForgotPassword;
  @BindView(R.id.loading_login)
  AVLoadingIndicatorView loadingLogin;
  private MaterialDialog disconnectNetworkDialog;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    getActivityComponent().inject(this);
    loginMvpPresenter.onAttach(this);

    //
    initUiComponents();
    initEvents();
    setVisibilityForComponents(View.INVISIBLE);
    loginMvpPresenter.loginWithLoginRemember();

    //

  }





  private void initUiComponents() {
    hideLoading();
    editTextEmail.setBackgroundResource(R.drawable.background_edittext_selector);
    editTextPassword.setBackgroundResource(R.drawable.background_edittext_selector);

  }

  private void initEvents() {
    buttonSignIn.setOnClickListener(this);
    buttonSignUp.setOnClickListener(this);
  }

  @Override
  public void setTitleToolbar(String title) {

  }

  @Override
  protected void onResume() {
    super.onResume();

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

  public void showDisconnectNetworkDialog(final String email, final String password){
    disconnectNetworkDialog = new MaterialDialog.Builder(this)
            .title(R.string.title_disconnect_network_dialog)
            .titleColorRes(R.color.colorAccent)
            .content(R.string.content_disconnect_network_dialog)
            .contentColorRes(R.color.colorSecondaryText)
            .positiveText(R.string.text_connect_again)
            .onPositive(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (loginMvpPresenter != null){
                  loginMvpPresenter.onClickConnectAgainDisconnectNetworkDialog(email,password);
                }
              }
            }).build();
    disconnectNetworkDialog.show();
  }

  public void dismissDisconnectNetworkDialog(){
    if (disconnectNetworkDialog.isShowing()){
      disconnectNetworkDialog.dismiss();
    }
  }

  public void setVisibilityForComponents(int visibility){
    editTextEmail.setVisibility(visibility);
    editTextPassword.setVisibility(visibility);
    textForgotPassword.setVisibility(visibility);
    checkSaveAccount.setVisibility(visibility);
    buttonSignUp.setVisibility(visibility);
    buttonSignIn.setVisibility(visibility);
  }

  @Override
  public void showLoading() {
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
        finish();
      }
    },3000);

  }

  @Override
  public void showNotification(int messageId) {
    Snackbar snackbar = Snackbar.make(textForgotPassword,messageId,2000);
    snackbar.show();
  }

  @Override
  public boolean getValueFrormCheckRemember() {
    return checkSaveAccount.isChecked();
  }


  @Override
  public void updateUserInfoLogin(String email, String password) {
    editTextEmail.setText(email);
    editTextPassword.setText(password);
  }


}
