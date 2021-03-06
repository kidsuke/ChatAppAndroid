package com.example.admin.chatapp.View;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.chatapp.Controller.MessageController;
import com.example.admin.chatapp.R;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ADMIN on 07-Oct-16.
 */

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView app_name;
    private TextView login_status;
    private EditText ip_address_input;
    private Button submit_ip_button;
    private Socket socket;
    private boolean checkConnectResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        login_status = (TextView) findViewById(R.id.login_status);
        ip_address_input = (EditText) findViewById(R.id.ip_address_input);
        submit_ip_button = (Button) findViewById(R.id.submit);

        submit_ip_button.setOnClickListener(this);

        //Change app name's font
        app_name = (TextView) findViewById(R.id.app_name);
        Typeface face = Typeface.createFromAsset(getResources().getAssets(),"Artbrush.ttf");
        app_name.setTypeface(face);

        //Set animation for app name
        Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.app_name_animation);
        app_name.startAnimation(bounce);

        //Set animtion for icon
        ImageView rocket = (ImageView) findViewById(R.id.rocket_img) ;
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rocket_animation);
        rocket.startAnimation(rotate);

        ImageView planet = (ImageView) findViewById(R.id.planet_img);
        Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.planet_animation);
        planet.startAnimation(fade_in);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                String ip_address = ip_address_input.getText().toString();
                ConnectTask ct = new ConnectTask();
                ct.execute(ip_address);
        }
    }

    public void toChatActivity(boolean result){
        checkConnectResult = result;
        if (checkConnectResult){
            Intent intent = new Intent(this, ChatActivity.class);
            MessageController.getInstance().setSocket(socket);
            startActivity(intent);
            login_status.setText("");
        }else
            login_status.setText("Cannot connect to this IP Address");
    }

    class ConnectTask extends AsyncTask<String,String,Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                InetAddress serverAddress = InetAddress.getByName(params[0]);
                if (serverAddress.isReachable(1000)) {
                    socket = new Socket(serverAddress, 9000);
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
            toChatActivity(result);
            this.cancel(true);
        }
    }
}
