package com.example.android.whereareyougo.ui.ui.chat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;
import com.example.android.whereareyougo.ui.ui.adapter.ChatMessagesAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
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

/**
 * Created by nguyenanhtrung on 16/07/2017.
 */

public class ChatDialogFragment extends DialogFragment implements ChatDialogView, View.OnClickListener {
    @Inject
    ChatDialogMvpPresenter<ChatDialogView> presenter;
    @BindView(R.id.image_user_statsus)
    CircleImageView imageUserStatsus;
    @BindView(R.id.text_friend_name)
    TextView textFriendName;
    @BindView(R.id.recyclerview_conversation)
    UltimateRecyclerView recyclerviewConversation;
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
    private InteractionWithChatDialogFragment interaction;
    private User friend;
    private ChatMessagesAdapter adapter;
    private List<ChatMessage> chatMessages;
    private MaterialDialog loadingDialog;
    private String conversationId;
    private ChildEventListener childEventListener;


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
            conversationId = presenter.getConversationId(friend.getUserID());
            presenter.createConversationId(conversationId);
        }

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


        return view;
    }

    private void iniUiComponents(View view) {
        emojIcon = new EmojIconActions(getActivity(), view.findViewById(R.id.root_view), textInputMessage, buttonSelectEmoj);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiEvents();


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
        Matisse.from(this)
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
            if (!path.isEmpty() || path != null) {
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
        recyclerviewConversation.showEmptyView();

        setupDatasForConvesationAdapter(new ArrayList<ChatMessage>());
        //

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
                if(chatMessages != null || !chatMessages.isEmpty()){
                    recyclerviewConversation.scrollVerticallyToPosition(chatMessages.size() - 1);
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
        return false;
    }

    @Override
    public void onError(String message, Activity activity) {

    }

    @Override
    public void hideKeyboard() {

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
        loadingDialog = new MaterialDialog.Builder(getContext())
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

    public interface InteractionWithChatDialogFragment {
        ActivityComponent getActivityComponent();

        String getCurrentUserId();

        String getCurrentUserImageUrl();
    }
}
