package com.example.android.whereareyougo.ui.ui.requestaddfriend;

import android.support.annotation.NonNull;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class RequestAddFriendPresenter<V extends RequestAddFriendView> extends BasePresenter<V> implements RequestAddFriendMvpPresenter<V> {
    @Inject
    public RequestAddFriendPresenter(DataManager dataManager) {
        super(dataManager);
    }


    public void onClickButtonAccept(final String friendId, final int position){
        if (friendId != null){
            getDataManager().saveUserFriend(friendId)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                //show message add friend successful to user
                                getMvpView().showMessage(R.string.text_add_friend_successful);
                                //remove request on firebase database
                                getDataManager().removeRequestAddFriend(friendId);
                                //remove request inside recyclerview
                                getMvpView().removeRequestInRecyclerView(position);
                                //save user to list friend of friend
                                getDataManager().saveUserToListOfUserFriend(friendId);


                            }
                        }
                    });
        }
    }

    @Override
    public void onClickButtonCancel(String friendId, int position) {
        if (friendId != null){
            getMvpView().removeRequestInRecyclerView(position);
            getDataManager().removeRequestAddFriend(friendId);
        }
    }
}
