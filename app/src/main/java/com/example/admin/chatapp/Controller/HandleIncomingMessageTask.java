package com.example.admin.chatapp.Controller;

import android.os.AsyncTask;

import com.example.admin.chatapp.View.ChatGUI;

/**
 * Created by ADMIN on 05-Oct-16.
 */

public class HandleIncomingMessageTask extends AsyncTask<ChatGUI,String,Void>{

    @Override
    protected Void doInBackground(ChatGUI... params) {
        ClientController.getInstance().setAdapter(params[0]);
        ClientController.getInstance().setListener(new ClientController.MessageCallback() {
            @Override
            public void callbackMessageReceiver(String message) {
                publishProgress(message);
            }
        });
        ClientController.getInstance().run();
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        ClientController.getInstance().printMessageToScreen(values[0]);
    }
}
