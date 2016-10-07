package com.example.admin.chatapp.View;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.admin.chatapp.Controller.HandleIncomingMessageTask;
import com.example.admin.chatapp.R;

public class ChatActivity extends FragmentActivity{
    private ChatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChatGUI frag = new ChatGUI();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, frag);
        transaction.commit();

        HandleIncomingMessageTask msgTask = new HandleIncomingMessageTask();
        msgTask.execute(frag);
    }
}
