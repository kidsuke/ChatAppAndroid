package com.example.admin.chatapp.Model;

import android.os.AsyncTask;

import com.example.admin.chatapp.Controller.MessageController;

/**
 * Created by ADMIN on 10-Oct-16.
 */

public class DisconnectTask extends AsyncTask<Void,Void,Void>{
    @Override
    protected Void doInBackground(Void... params) {
        MessageController.getInstance().terminateConnection();
        return null;
    }
}
