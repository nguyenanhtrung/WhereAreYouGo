package com.example.android.whereareyougo.ui.ui.signup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment.Callback;
import com.example.android.whereareyougo.ui.utils.NetworkUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import javax.inject.Inject;


public class SignupDialogFragment extends DialogFragment implements SignupView, OnClickListener {

  @Inject
  SignupMvpPresenter<SignupView> signupMvpPresenter;
  @BindView(R.id.text_signup_email)
  MaterialEditText textSignupEmail;
  @BindView(R.id.text_signup_password)
  MaterialEditText textSignupPassword;
  @BindView(R.id.text_signup_name)
  MaterialEditText textSignupName;
  @BindView(R.id.button_sign_up)
  BootstrapButton buttonSignUp;
  Unbinder unbinder;
  @BindView(R.id.button_close_dialog)
  BootstrapButton buttonCloseDialog;

  private InteractionWithSignupFragment interactWithLoginActivty;

  public static SignupDialogFragment newInstance() {
    SignupDialogFragment fragment = new SignupDialogFragment();
    fragment.setCancelable(false);

    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_signup_dialog, container, false);

    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initEvents();
  }

  private void initEvents() {
    buttonSignUp.setOnClickListener(this);
    buttonCloseDialog.setOnClickListener(this);
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Callback callback = (Callback) context;
    callback.getActivityComponent().inject(SignupDialogFragment.this);
    signupMvpPresenter.onAttach(SignupDialogFragment.this);

    //
    interactWithLoginActivty = (InteractionWithSignupFragment) context;
  }

  @Override
  public void showLoading() {

  }

  @Override
  public void hideLoading() {

  }

  @Override
  public boolean isNetworkConnected() {
    return NetworkUtil.isNetworkConnected(getContext());
  }

  @Override
  public void onError(String message, Activity activity) {

  }

  @Override
  public void hideKeyboard() {

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.button_sign_up:
        if (signupMvpPresenter != null) {
          signupMvpPresenter.onClickButtonSignup(textSignupEmail.getText().toString(),
              textSignupPassword.getText().toString(),
              textSignupName.getText().toString());
        }
        break;

      case R.id.button_close_dialog:
        dismiss();
        break;
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void showErrorOnEditTextEmail(String error) {
    if (error != null) {
      textSignupEmail.setError(error);
    }
  }

  @Override
  public void showErrorOnEditTextPassword(String error) {
    if (error != null) {
      textSignupPassword.setError(error);
    }
  }

  @Override
  public void showErrorOnEditTextName(String error) {
    if (error != null) {
      textSignupName.setError(error);
    }
  }

  @Override
  public String getStringFromStringResource(int stringId) {
    return getResources().getString(stringId);
  }

  @Override
  public void showNotification(int messageId) {
    Snackbar.make(getView(),messageId,3000).show();
  }

  @Override
  public void closeDialog() {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        dismiss();
      }
    },2000);
  }

  @Override
  public void updateUserInfoForLoginActivity(String email, String password) {
    interactWithLoginActivty.updateUserInfoLogin(email,password);
  }

  public interface InteractionWithSignupFragment{
    void updateUserInfoLogin(String email, String password);
  }
}
