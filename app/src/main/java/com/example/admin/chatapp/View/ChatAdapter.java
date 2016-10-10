package com.example.admin.chatapp.View;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.chatapp.Model.ChatMessage;
import com.example.admin.chatapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.security.AccessController.getContext;

/**
 * Created by ADMIN on 05-Oct-16.
 */

public class ChatAdapter extends BaseAdapter{
    private ViewWrapper wrapper;
    private ArrayList<ChatMessage> list;
    private LayoutInflater inflater;
    private Activity context;
    private DateFormat dateFormat;

    public ChatAdapter(Activity context, ArrayList<ChatMessage> list){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        dateFormat = new SimpleDateFormat("HH:mm");
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null){
            row = inflater.inflate(R.layout.chat_bubble, null);
            wrapper = new ViewWrapper(row);
            row.setTag(wrapper);
        }else
            wrapper = (ViewWrapper)row.getTag();

        TextView msgArea = wrapper.getMessageArea();
        TextView time = wrapper.getTimeArea();
        LinearLayout container = wrapper.getContainer();
        LinearLayout container_parent = wrapper.getContainerParent();

        if (list.get(position).isFromMe() == true) {
            container.setBackgroundResource(R.drawable.bubble_send);
            container_parent.setGravity(Gravity.RIGHT);
            msgArea.setText(list.get(position).getMessage());
            msgArea.setTextColor(Color.WHITE);
            time.setText(setTime());
            time.setTextColor(Color.WHITE);
        }else{
            container.setBackgroundResource(R.drawable.bubble_receive);
            container_parent.setGravity(Gravity.LEFT);
            msgArea.setText(list.get(position).getMessage());
            msgArea.setTextColor(Color.BLACK);
            time.setText(setTime());
            time.setTextColor(Color.BLACK);
        }

        return row;
    }


    public void updateList(ArrayList<ChatMessage> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public String setTime(){
        String date = dateFormat.format(Calendar.getInstance().getTime());
        return date;
    }

    class ViewWrapper{
        private View base;
        private TextView msg;
        private TextView time;
        private LinearLayout container;
        private LinearLayout container_parent;

        public ViewWrapper(View base){
            this.base = base;
        }

        public TextView getMessageArea(){
            if (msg == null)
                msg = (TextView) base.findViewById(R.id.message_text);

            return msg;
        }

        public TextView getTimeArea(){
            if (time == null)
                time = (TextView) base.findViewById(R.id.time);

            return time;
        }

        public LinearLayout getContainer(){
            if (container == null)
                container = (LinearLayout)base.findViewById(R.id.bubble_layout);

            return container;
        }

        public LinearLayout getContainerParent(){
            if (container_parent == null)
                container_parent = (LinearLayout)base.findViewById(R.id.bubble_layout_parent);

            return container_parent;
        }

    }
}
