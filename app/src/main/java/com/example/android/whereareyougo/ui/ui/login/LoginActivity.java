package com.example.android.whereareyougo.ui.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseActivity;
import com.example.android.whereareyougo.ui.ui.signup.SignupDialogFragment;
import com.example.android.whereareyougo.ui.ui.signup.SignupDialogFragment.InteractionWithSignupFragment;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.rengwuxian.materialedittext.MaterialEditText;
import javax.inject.Inject;


public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener,
    InteractionWithSignupFragment{

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

  private void initEvents(){
    buttonSignIn.setOnClickListener(this);
    buttonSignUp.setOnClickListener(this);
  }

  @Override
  public void setTitleToolbar(String title) {

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.button_sign_in:

        break;
      case R.id.button_sign_up:
        if (loginMvpPresenter != null){
          loginMvpPresenter.onClickButtonSignup();
        }
        break;
    }
  }

  @Override
  public void openSignupFragment() {
    SignupDialogFragment dialogFragment = SignupDialogFragment.newInstance();
    dialogFragment.show(getSupportFragmentManager(), MyKey.SIGNUP_DIALOG_FRAGMENT_TAG);

  }

  @Override
  public void updateUserInfoLogin(String email, String password) {
    editTextEmail.setText(email);
    editTextPassword.setText(password);
  }
}
