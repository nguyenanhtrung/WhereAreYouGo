package com.example.android.whereareyougo.ui.ui.signup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseDialogFragment;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment.Callback;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.example.android.whereareyougo.ui.utils.NetworkUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import javax.inject.Inject;


public class SignupDialogFragment extends BaseDialogFragment implements SignupView, OnClickListener {

  @Inject
  SignupMvpPresenter<SignupView> signupMvpPresenter;
  @BindView(R.id.text_signup_email)
  MaterialEditText textSignupEmail;
  @BindView(R.id.text_signup_password)
  MaterialEditText textSignupPassword;
  @BindView(R.id.text_signup_name)
  MaterialEditText textSignupName;
  @BindView(R.id.button_sign_up)
  Button buttonSignUp;
  Unbinder unbinder;
  @BindView(R.id.button_close_dialog)
  Button buttonCloseDialog;

  private InteractionWithSignupFragment interactWithLoginActivity;

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

    //
    initUiComponents();
    return view;
  }

  private void initUiComponents() {
    textSignupEmail.setBackgroundResource(R.drawable.background_edittext_selector);
    textSignupName.setBackgroundResource(R.drawable.background_edittext_selector);
    textSignupPassword.setBackgroundResource(R.drawable.background_edittext_selector);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initEvents();
  }

  private void initEvents() {
    buttonSignUp.setOnClickListener(this);
    buttonCloseDialog.setOnClickListener(this);
    //
    textSignupName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
          String helperText = getStringFromStringResource(R.string.edit_text_name_helper);
          textSignupName.setHelperText(helperText);
        }else{
          textSignupName.setHelperText(null);
        }
      }
    });

    textSignupPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
          String helperText = getStringFromStringResource(R.string.edit_text_password_helper);
          textSignupPassword.setHelperText(helperText);
        }else{
          textSignupPassword.setHelperText(null);
        }
      }
    });

    textSignupEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
          String helperText = getStringFromStringResource(R.string.edit_text_email_helper);
          textSignupEmail.setHelperText(helperText);
        }else {
          textSignupEmail.setHelperText(null);
        }
      }
    });

  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Callback callback = (Callback) context;
    callback.getActivityComponent().inject(SignupDialogFragment.this);
    signupMvpPresenter.onAttach(SignupDialogFragment.this);

    //
    interactWithLoginActivity = (InteractionWithSignupFragment) context;
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
    dismissDialog(MyKey.SIGNUP_DIALOG_FRAGMENT_TAG);
  }


  @Override
  public void updateUserInfoForLoginActivity(String email, String password) {
    interactWithLoginActivity.updateUserInfoLogin(email,password);
  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onError(int messageId) {

  }

  public interface InteractionWithSignupFragment{
    void updateUserInfoLogin(String email, String password);
  }
}
