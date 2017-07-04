package com.example.android.whereareyougo.ui.ui.addfriend;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 02/07/2017.
 */

public class AddFriendPresenter<V extends AddFriendView> extends BasePresenter<V> implements AddFriendMvpPresenter<V> {
    @Inject
    public AddFriendPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void onClickButtonSearch(String content) {
        if (content.isEmpty()) {
            //show message content is empty
            getMvpView().showMessage(R.string.error_empty);
            //
            return;
        }

        //
        getMvpView().clearDataForFriendsRecyclerView();
        if (getMvpView().checkSearchByName()) {
            searchUsersByName(content);
            getMvpView().clearEditTextName();
        } else {
            searchUsersByPhoneNumber(content);
            getMvpView().clearEditTextPhone();
        }


    }

    @Override
    public void onClickButtonCloseDialog() {
        getMvpView().closeDialog();
    }

    @Override
    public void onClickButtonAddFriend(String receiverId) {
        getDataManager().sendRequestAddFriend(receiverId);
        getMvpView().setButtonAddFriendEnable(R.string.text_sended_request,false);
    }

    private void searchUsersByPhoneNumber(String phoneNumber) {
        getDataManager().getUsersByPhoneNumber(phoneNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> users = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            users.add(user);
                        }
                        //set data for recyclerview adapter
                        getMvpView().setupUsersRecyclerViewAdapter(users);
                        if (users.isEmpty() || users == null){
                            getMvpView().showMessage(R.string.label_search_result_empty);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void searchUsersByName(String name) {
        getDataManager().getUsersByName(name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> users = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            users.add(user);
                        }
                        //set data for recyclerview adapter
                        getMvpView().setupUsersRecyclerViewAdapter(users);
                        if (users.isEmpty() || users == null){
                            getMvpView().showMessage(R.string.label_search_result_empty);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


}
