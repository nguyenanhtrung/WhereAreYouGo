package com.example.android.whereareyougo.ui.ui.messages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.UserMessage;
import com.example.android.whereareyougo.ui.ui.adapter.UserMessagesRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class MessagesFragment extends BaseFragment implements MessagesView {

    @Inject
    MessagesMvpPresenter<MessagesView> presenter;
    @BindView(R.id.recycler_view_messages)
    UltimateRecyclerView recyclerViewMessages;
    UserMessagesRecyclerViewAdapter adapter;
    Unbinder unbinder;
    private HashMap<String, Boolean> messageNotificationMap;


    public static MessagesFragment newInstance(ArrayList<String> messageNotifications) {
        MessagesFragment fragment = new MessagesFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("messagenotifications", messageNotifications);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<String> messageNotifications = bundle.getStringArrayList("messagenotifications");
            //put String to messageNotificationMap
            if (messageNotificationMap == null) {
                messageNotificationMap = new HashMap<>();
            }
            for (String s : messageNotifications) {
                messageNotificationMap.put(s, true);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        //
        //test get conversations


        //setup recyclerview
        setupRecyclerViewUserMessages();
        presenter.getUserConversations();

        return view;
    }

    private void setupRecyclerViewUserMessages() {
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMessages.showEmptyView();
        //setup adapter
        //adapter = new UserMessagesRecyclerViewAdapter(getActivity(),new ArrayList<UserMessage>());
        ///recyclerViewMessages.setAdapter(adapter);

    }

    public void setDatasForUserMessagesAdapter(ArrayList<UserMessage> userMessages){
        if(adapter == null){
            adapter = new UserMessagesRecyclerViewAdapter(getActivity(),userMessages);
            recyclerViewMessages.setAdapter(adapter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
