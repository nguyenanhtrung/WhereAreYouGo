package com.example.android.whereareyougo.ui.ui.chat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.data.database.entity.ChatUser;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;
import com.example.android.whereareyougo.ui.ui.base.BaseDialogFragment;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.example.android.whereareyougo.ui.utils.NetworkUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

import static android.app.Activity.RESULT_OK;



public class ChatDialogFragment extends BaseDialogFragment implements ChatDialogView, View.OnClickListener,ChatUsersRecyclerViewAdapter.ChatUserClickListener
        {
    @Inject
    ChatDialogMvpPresenter<ChatDialogView> presenter;
    @BindView(R.id.image_user_statsus)
    CircleImageView imageUserStatsus;
    @BindView(R.id.text_friend_name)
    TextView textFriendName;
    @BindView(R.id.recyclerview_conversation)
    SuperRecyclerView recyclerviewConversation;
    @BindView(R.id.button_select_emoj)
    ImageButton buttonSelectEmoj;
    @BindView(R.id.button_select_photo)
    ImageButton buttonSelectPhoto;
    @BindView(R.id.text_input_message)
    EmojiconEditText textInputMessage;
    @BindView(R.id.button_send_message)
    ImageButton buttonSendMessage;
    EmojIconActions emojIcon;
    Unbinder unbinder;
    @BindView(R.id.image_button_close)
    ImageButton imageButtonClose;
    @BindView(R.id.recycler_view_chat_users)
    SuperRecyclerView recyclerViewChatUsers;
    private ChatUsersRecyclerViewAdapter chatUsersAdapter;
    @BindView(R.id.root_view)
    LinearLayout rootView;
    private InteractionWithChatDialogFragment interaction;
    private User friend;
    private ChatMessagesAdapter adapter;
    private List<ChatMessage> chatMessages;
    private ArrayList<ChatUser> chatUsers;
    private MaterialDialog loadingDialog;
    private String conversationId;
    private ChildEventListener childEventListener;
    private int previousPosition = 0;


    public static ChatDialogFragment newInstance(User friend) {
        ChatDialogFragment fragment = new ChatDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("friend", friend);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        Bundle bundle = getArguments();
        if (bundle != null) {
            friend = bundle.getParcelable("friend");
            conversationId = presenter.getConversationId(friend != null ? friend.getUserID() : null);
            presenter.createConversationId(conversationId);
        }
        interaction.removeMessageNotificationChildEvent();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        unbinder = ButterKnife.bind(this, view);
        iniUiComponents(view);
        //
        setupConversationRecyclerView();
        textFriendName.setText(friend.getName());
        // presenter.createConversationId(conversationId);
        presenter.updateFriendStatus(friend.getUserID());
        //
        showLoadingDialog(R.string.title_dialog_loading_messages, R.string.text_loading_messages);
        setupChatUsersRecyclerView();

        return view;
    }

    private void setupChatUsersRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerViewChatUsers.setLayoutManager(linearLayoutManager);
        //
        chatUsers = new ArrayList<>();
        ChatUser currentChatUser = new ChatUser(friend,0,true);
        chatUsers.add(currentChatUser);
        chatUsersAdapter = new ChatUsersRecyclerViewAdapter(getActivity(),chatUsers,this);
        recyclerViewChatUsers.setAdapter(chatUsersAdapter);
        //

    }

    public void addChatUserToRecyclerView(ChatUser chatUser){
        if (chatUsersAdapter != null){
            chatUsersAdapter.addItem(chatUser);
        }
    }

    private void iniUiComponents(View view) {
        emojIcon = new EmojIconActions(getActivity(), view.findViewById(R.id.root_view), textInputMessage, buttonSelectEmoj);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiEvents();
        presenter.updateMessageNotifications(chatUsers);


    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public void showEmojKeyboard() {
        emojIcon.ShowEmojIcon();
    }

    private void initUiEvents() {
        buttonSelectEmoj.setOnClickListener(this);
        buttonSelectPhoto.setOnClickListener(this);
        buttonSendMessage.setOnClickListener(this);
        imageButtonClose.setOnClickListener(this);


    }

    public void pickImageFromGallery() {
       Matisse.from(getActivity())
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(MyKey.REQUEST_CODE_PICK_IMAGE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyKey.REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            List<Uri> path = Matisse.obtainResult(data);
            if (!path.isEmpty()) {
                //get path image and upload to firebase storage
                Uri imageUri = path.get(0);

                //create chat message object and send message to firebase database
                if (presenter != null) {
                    presenter.sendMessagePhoto(imageUri.toString(), imageUri.getLastPathSegment(), conversationId);
                }
            }

        }
    }

    public void removeChildEventListener() {
        presenter.removeChilEventOnMessagesRef(childEventListener);
    }

    private void setupConversationRecyclerView() {
        recyclerviewConversation.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupDatasForConvesationAdapter(new ArrayList<ChatMessage>());
        //

    }

    public void setUserBadgeNotification(int badgeNotification, int userPosition) {
        if (chatUsers != null) {
            int badgeNumber = chatUsers.get(userPosition).getMessageNotification();
            badgeNumber += badgeNotification;
            chatUsers.get(userPosition).setMessageNotification(badgeNumber);
            chatUsersAdapter.notifyDataSetChanged();
        }
    }

    public void setupDatasForConvesationAdapter(ArrayList<ChatMessage> datas) {
        chatMessages = datas;

        //
        adapter = new ChatMessagesAdapter(getActivity(), getCurrentUserId(), chatMessages);
        recyclerviewConversation.setAdapter(adapter);
        //


    }

    public String getConversationId() {
        return conversationId;
    }

    public void addChatMessagesToAdapter(ChatMessage chatMessage) {
        adapter.addItem(chatMessage);

    }


    public void setImageUserStatsus(String status) {
        if (status.equals("ONLINE")) {
            Glide.with(getActivity())
                    .load(R.drawable.ic_online)
                    .into(imageUserStatsus);
        } else if (status.equals("OFFLINE")) {
            Glide.with(getActivity())
                    .load(R.drawable.ic_offline)
                    .into(imageUserStatsus);
        }
    }


    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interaction = (InteractionWithChatDialogFragment) context;
        interaction.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setSizeOfDialog();

        //
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                presenter.onMessagesRefChildEvent(chatMessage);
                //
                if (chatMessages != null) {
                    if (!chatMessages.isEmpty()){
                        recyclerviewConversation.setVerticalScrollbarPosition(chatMessages.size() - 1);
                    }

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        presenter.setMessagesReferenceChildEvent(childEventListener, conversationId);


    }

    public void clearDataChatMessagesAdapter(){
        if (adapter != null){
            chatMessages.clear();
            adapter.notifyDataSetChanged();
        }
    }

    public void setTextFriendName(String friendName) {
        if (friendName != null){
            textFriendName.setText(friendName);
        }

    }



    public ChildEventListener getMessageChildEvent() {
        return childEventListener;
    }

    public void setMessageChildEvent(ChildEventListener childEvent){
        childEventListener = childEvent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void showLoading() {

    }

    public void dismissLoadingDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void setSizeOfDialog() {
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = 1000;
        getDialog().getWindow().setLayout(
                width, height
        );


    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtil.isNetworkConnected(getActivity());
    }



    public void setTextInputMessage(String content) {
        textInputMessage.setText(content);
    }

    public String getCurrentUserId() {
        return interaction.getCurrentUserId();
    }

    public String getCurrentUserImageUrl() {
        return interaction.getCurrentUserImageUrl();
    }

    public String getFriendId() {
        return friend.getUserID();
    }

    public String getFriendImageUrl() {
        return friend.getImageUrl();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showLoadingDialog(int titleId, int contentId) {
        loadingDialog = new MaterialDialog.Builder(getActivity())
                .title(titleId)
                .content(contentId)
                .progress(true, 3)
                .show();


    }



    public void dismissChatDialog() {
        dismissAllowingStateLoss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_select_emoj:
                if (presenter != null) {
                    presenter.onClickButtonSelectEmoj();
                }
                break;
            case R.id.button_select_photo:
                if (presenter != null) {
                    presenter.onClickButtonSelectPhoto();
                }
                break;
            case R.id.button_send_message:
                if (presenter != null) {
                    presenter.onClickButtonSendMessage(textInputMessage.getText().toString());
                }
                break;
            case R.id.image_button_close:
                if (presenter != null) {
                    presenter.onClickButtonCloseChatDialog();
                }
                break;
        }
    }

    public void notifyDataChatUsersChange(){
        if (chatUsersAdapter != null){
            chatUsersAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickChatUserItem(View view, int position) {
        if (presenter != null){
            presenter.onClickChatUser(chatUsers.get(position),chatUsers.get(previousPosition));
            previousPosition = position;
        }
    }



    public interface InteractionWithChatDialogFragment {
        ActivityComponent getActivityComponent();

        String getCurrentUserId();

        String getCurrentUserImageUrl();

        void removeMessageNotificationChildEvent();

        void createMessageNotificationChildEvent();
    }
}
