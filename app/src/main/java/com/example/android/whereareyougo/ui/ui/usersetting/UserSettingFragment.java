package com.example.android.whereareyougo.ui.ui.usersetting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.rengwuxian.materialedittext.MaterialEditText;
import de.hdodenhof.circleimageview.CircleImageView;
import javax.inject.Inject;


public class UserSettingFragment extends BaseFragment implements UserSettingView,
    View.OnClickListener {


  @Inject
  UserSettingMvpPresenter<UserSettingView> userSettingMvpPresenter;

  Unbinder unbinder;
  @BindView(R.id.image_user)
  CircleImageView imageUser;
  @BindView(R.id.button_select_image)
  BootstrapButton buttonSelectImage;
  @BindView(R.id.edit_text_user_name)
  MaterialEditText editTextUserName;
  @BindView(R.id.edit_text_phone)
  MaterialEditText editTextPhone;
  @BindView(R.id.edit_text_email)
  MaterialEditText editTextEmail;
  @BindView(R.id.edit_text_password)
  MaterialEditText editTextPassword;
  @BindView(R.id.button_save)
  BootstrapButton buttonSave;
  @BindView(R.id.button_edit)
  BootstrapButton buttonEdit;


  public static UserSettingFragment getInstance() {
    return new UserSettingFragment();
  }

  public UserSettingFragment() {
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_account_setting, container, false);
    unbinder = ButterKnife.bind(this, view);



    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    userSettingMvpPresenter.onAttach(this);
  }

  @Override
  public void onClick(View v) {

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Callback callback = (Callback) context;
    callback.getActivityComponent().inject(this);
  }
}
