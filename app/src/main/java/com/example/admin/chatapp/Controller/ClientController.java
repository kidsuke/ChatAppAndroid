package com.example.admin.chatapp.Controller;

import com.example.admin.chatapp.View.ChatAdapter;
import com.example.admin.chatapp.View.ChatGUI;
import com.example.admin.chatapp.View.ChatMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ADMIN on 05-Oct-16.
 */

public class ClientController {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private ChatAdapter adapter;
    private ChatGUI gui;
    private static ClientController controller;
    private boolean mRun;
    private MessageCallback listener;

    private ClientController(){
        mRun = true;
    }

    public boolean establishConnection(int port){
        try{
            System.out.println("begin to establish");
            InetAddress serverAddress = InetAddress.getByName("10.112.214.238");
            socket = new Socket(serverAddress, port);
            System.out.println(socket.toString());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            return false;
        }
    }

    public void terminateConnection(){
        try{
            socket.close();
        }catch (IOException e){
            System.out.println("Cannot close socket");
        }
    }

    public void sendMessage(String msg){
        writer.println(msg + " [-r-]");
    }

    public void printMessageToScreen(String msg){
        gui.getMessageToPrint(new ChatMessage(msg, false));
    }

    public void setAdapter(ChatGUI gui){
        this.gui = gui;
    }

    public void setListener(MessageCallback listener){
        this.listener = listener;
    }

    public static ClientController getInstance(){
        if (controller == null)
            controller = new ClientController();

        return controller;
    }

    public void run(){
        establishConnection(9000);

        try {
            //String msg;
            while (mRun) {
                String msg;
                if ((msg = reader.readLine()) == null)
                    continue;
                System.out.println(msg);
                listener.callbackMessageReceiver(msg);
            }
        }catch (IOException e){
            System.out.println("Cannot read from socket");
        }

        terminateConnection();
    }

    public interface MessageCallback {
        void callbackMessageReceiver(String message);
    }

}

