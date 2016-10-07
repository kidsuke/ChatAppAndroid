package com.example.admin.chatapp.View;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.chatapp.Controller.HandleIncomingMessageTask;
import com.example.admin.chatapp.R;

/**
 * Created by ADMIN on 07-Oct-16.
 */

public class ConnectActivity extends AppCompatActivity {
    private ChatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


    }
}
