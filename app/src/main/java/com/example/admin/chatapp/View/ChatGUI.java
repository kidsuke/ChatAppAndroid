package com.example.admin.chatapp.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.admin.chatapp.Controller.MessageController;
import com.example.admin.chatapp.Model.ChatMessage;
import com.example.admin.chatapp.R;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by ADMIN on 05-Oct-16.
 */

public class ChatGUI extends Fragment implements View.OnClickListener{
    private EditText inputArea;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> list;
    private PrintWriter writer;

    public ChatGUI(){
        list = new ArrayList<ChatMessage>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.chat_layout, container, false);
        inputArea = (EditText) view.findViewById(R.id.inputArea);
        ImageButton sendMsgButton = (ImageButton) view.findViewById(R.id.sendMsgButton);
        ListView msgDisplay = (ListView) view.findViewById(R.id.msgDisplay);

        adapter = new ChatAdapter(getActivity(), list);
        msgDisplay.setAdapter(adapter);
        msgDisplay.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        sendMsgButton.setOnClickListener(this);

        try{
            Thread t = new Thread(MessageController.getInstance());
            t.start();
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    public void getMessageToPrint(ChatMessage msgObj){
        list.add(msgObj);
        adapter.updateList(list);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sendMsgButton:
                String msg = inputArea.getText().toString();
                if (!msg.equals("")){
                    MessageController.getInstance().sendMessage(msg);
                    getMessageToPrint(new ChatMessage(msg, true));
                    inputArea.setText("");
                }
        }
    }

}
