package com.example.admin.chatapp.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.admin.chatapp.Model.ChatMessage;
import com.example.admin.chatapp.R;
import com.example.admin.chatapp.View.ChatAdapter;
import com.example.admin.chatapp.View.ChatGUI;
import com.example.admin.chatapp.View.ConnectActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by ADMIN on 07-Oct-16.
 */




/**
 * Created by ADMIN on 05-Oct-16.
 */

public class MessageController implements Runnable{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private ChatAdapter adapter;
    private ChatGUI gui;
    private static MessageController controller;
    private boolean mRun;

    private MessageController(){
        mRun = true;
    }

    public static MessageController getInstance(){
        if (controller == null)
            controller = new MessageController();

        return controller;
    }

    public void setSocket(Socket s){
        socket = s;
    }

    public void initializeIO(){
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void terminateConnection(){
        try{
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        }catch (IOException e){
            System.out.println("Cannot close socket");
        }
    }

    public void setControllerStatus(boolean status){
        mRun = status;
    }

    public void setGUI(ChatGUI g){
        gui = g;
    }

    public void sendMessage(String msg){
        writer.println(msg + " [-r-]");
    }

    public void run(){
        initializeIO();

        while(true) {
            namePrompt();

            if (checkUserName())
                break;
        }

        try {
            while (mRun) {
                String msg;
                if ((msg = reader.readLine()) == null)
                    continue;

                update(msg);

            }
        }catch (IOException e){
            System.out.println("Cannot read from socket");
        }catch (NullPointerException npe){
            System.out.println("Server crashed");
        }

        if (gui.getActivity() != null)
            gui.getActivity().finish();
    }

    public void namePrompt(){
        gui.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = LayoutInflater.from(gui.getActivity());
                View view = inflater.inflate(R.layout.name_alert, null);
                final EditText text = (EditText)view.findViewById(R.id.user_name);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gui.getActivity());
                alertDialogBuilder.setView(view);
                alertDialogBuilder
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });


                final AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Boolean wantToCloseDialog = false;

                        if (!text.getText().toString().equals("")){
                            sendMessage(":user " + text.getText().toString() + " [-r-]");
                            wantToCloseDialog = true;
                        }

                        text.setText("");

                        if(wantToCloseDialog)
                            alertDialog.dismiss();
                    }
                });

            }
        });
    }

    public void update(String msg){
        final String m = msg;
        gui.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gui.getMessageToPrint(new ChatMessage(m, false));
            }
        });
    }

    public boolean checkUserName(){
        try{
            String s = reader.readLine();
            if (!s.equals("invalid")) {
                update(s);
                return true;
            }
        }catch(IOException ioe){
            System.out.println("Cannot read from BufferedReader");
        }
        update("Invalid Username. Please try another one.");
        return false;
    }

    public void backToConnectActivity(){
        Intent go_back_to_connect = new Intent(gui.getActivity(), ConnectActivity.class);
        gui.getActivity().startActivity(go_back_to_connect);
        gui.getActivity().finish();
    }
}

