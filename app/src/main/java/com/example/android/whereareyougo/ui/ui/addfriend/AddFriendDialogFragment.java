package com.example.android.whereareyougo.ui.ui.addfriend;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;
import com.example.android.whereareyougo.ui.ui.adapter.UsersRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.custom.DividerItemDecoration;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 02/07/2017.
 */

public class AddFriendDialogFragment extends DialogFragment implements AddFriendView,UsersRecyclerViewAdapter.OnItemClickListener {
    @Inject
    AddFriendMvpPresenter<AddFriendView> presenter;
    @BindView(R.id.recycler_view_seach_friends)
    UltimateRecyclerView recyclerViewSeachFriends;
    @BindView(R.id.edit_text_search_name)
    MaterialEditText editTextSearchName;
    @BindView(R.id.edit_text_search_phone)
    MaterialEditText editTextSearchPhone;
    @BindView(R.id.button_search)
    BootstrapButton buttonSearch;
    @BindView(R.id.radio_button_name)
    RadioButton radioButtonName;
    @BindView(R.id.radio_button_phone)
    RadioButton radioButtonPhone;
    @BindView(R.id.button_close_dialog)
    Button buttonCloseDialog;
    Unbinder unbinder;
    @BindView(R.id.text_label_name)
    TextView textLabelName;
    @BindView(R.id.text_label_phone)
    TextView textLabelPhone;
    private InteractionWithAddFriendFragment interaction;
    private UsersRecyclerViewAdapter adapter;
    private List<User> users;
    private BootstrapButton buttonAddFriend;



    public static AddFriendDialogFragment newInstance() {
        AddFriendDialogFragment fragment = new AddFriendDialogFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_friend, container, false);
        unbinder = ButterKnife.bind(this, view);
        //init Ui Component here
        initUiComponents();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiEvents();
        setupUsersRecyclerView();
    }

    private void setupUsersRecyclerView() {
        users = new ArrayList<>();
        //
        recyclerViewSeachFriends.setLayoutManager(new LinearLayoutManager(getActivity()));


        recyclerViewSeachFriends.showEmptyView();
        Drawable divider = ContextCompat.getDrawable(getActivity(), R.drawable.divider_recycler_view);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(divider);
        recyclerViewSeachFriends.addItemDecoration(itemDecoration);

        //


    }

    public void setupUsersRecyclerViewAdapter(List<User> userList) {
        users = userList;
        if (adapter == null){
            adapter = new UsersRecyclerViewAdapter(getActivity(), users,this);
        }else{
            adapter.swapDatas(users);
        }

        recyclerViewSeachFriends.setAdapter(adapter);
        //


    }

    public void clearDataForFriendsRecyclerView(){
        if (users != null || !users.isEmpty()){

            if (adapter != null){
                adapter.removeAllInternal(users);
                adapter.notifyDataSetChanged();
                users.clear();
            }
        }
    }

    public void clearEditTextName(){
        editTextSearchName.setText("");
    }

    public void clearEditTextPhone(){
        editTextSearchPhone.setText("");
    }

    public boolean checkSearchByName(){
        return radioButtonName.isChecked();
    }

    private void initUiEvents() {
        radioButtonName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonPhone.setChecked(false);
                }
                setupTypeSearch(isChecked, editTextSearchName, textLabelName);
            }
        });

        radioButtonPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonName.setChecked(false);
                }
                setupTypeSearch(isChecked, editTextSearchPhone, textLabelPhone);
            }
        });

    }

    private void setupTypeSearch(boolean isCheck, MaterialEditText editText, TextView textView) {
        if (isCheck) {
            editText.setBackgroundResource(R.drawable.background_text_search);
            editText.setEnabled(true);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            editText.setEnabled(false);
            editText.setBackgroundResource(R.drawable.background_text_search_default);
            textView.setTypeface(Typeface.DEFAULT);
        }
    }

    private void initUiComponents() {
        initRadioButtons();
        initTextViews();
        initEditTexts();
    }

    private void initEditTexts() {
        editTextSearchName.setBackgroundResource(R.drawable.background_text_search);
        //
        editTextSearchPhone.setEnabled(false);
    }

    private void initTextViews() {
        if (radioButtonName.isChecked()) {
            textLabelName.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (radioButtonPhone.isChecked()) {
            textLabelPhone.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    private void initRadioButtons() {
        radioButtonName.setChecked(true);
        radioButtonPhone.setChecked(false);
    }

    public void showMessage(int messageId) {
        Snackbar.make(getView(), messageId, 2000).show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interaction = (InteractionWithAddFriendFragment) context;
        interaction.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setSizeOfDialog();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void onError(String message, Activity activity) {

    }

    @Override
    public void hideKeyboard() {

    }

    private void setSizeOfDialog() {
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = 1100;
        getDialog().getWindow().setLayout(
                width, height
        );


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private String getContentSearch() {
        String content = "";
        if (radioButtonName.isChecked()) {
            content = editTextSearchName.getText().toString();
        } else if (radioButtonPhone.isChecked()) {
            content = editTextSearchPhone.getText().toString();
        }

        return content;
    }

    @OnClick({R.id.button_search, R.id.button_close_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_search:
                if (presenter != null) {
                    presenter.onClickButtonSearch(getContentSearch());
                }
                break;
            case R.id.button_close_dialog:
                if (presenter != null){
                    presenter.onClickButtonCloseDialog();
                }
                break;
        }
    }

    public void closeDialog(){
        dismiss();
    }


    //onClick button Them ban
    @Override
    public void onButtonClick(View view,int position) {
        buttonAddFriend = (BootstrapButton) view;
        //
        if (presenter != null){
            presenter.onClickButtonAddFriend(users.get(position).getUserID());
        }
    }

    public void setButtonAddFriendEnable(int idContent, boolean isEnable){
        if (buttonAddFriend != null){
            buttonAddFriend.setText(idContent);
            buttonAddFriend.setEnabled(isEnable);
        }
    }



    public interface InteractionWithAddFriendFragment {
        ActivityComponent getActivityComponent();
        String getCurrentUserId();
    }
}
