package com.example.admin.chatapp.Model;

/**
 * Created by ADMIN on 06-Oct-16.
 */

public class ChatMessage {
    private String msg;
    private boolean fromMe;

    public ChatMessage(String msg, boolean fromMe){
        this.msg = msg;
        this.fromMe = fromMe;
    }

    public String getMessage(){
        return msg;
    }

    public boolean isFromMe(){
        return fromMe;
    }
}
