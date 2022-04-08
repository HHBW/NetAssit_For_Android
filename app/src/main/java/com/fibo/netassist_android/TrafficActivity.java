package com.fibo.netassist_android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Lenovo on 3/29/2022.
 */

public class TrafficActivity extends AppCompatActivity{

    public Socket_Assistor tclientTread=null;
    public Handler trecv_handler=null;

    private EditText edit_msg;
    private Button btn_send;
    private TextView view_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        edit_msg=(EditText)findViewById(R.id.edit_msgto);
        btn_send=(Button)findViewById(R.id.btn_sendmsg);
        view_msg=(TextView)findViewById(R.id.view_MSG);
        tclientTread=MainActivity.clientTread;
        trecv_handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==2){
                    String tmp=msg.obj.toString();
                    if(!tmp.equals("")){
                        view_msg.append(tmp+"\n");
                    }
                }
            }
        };
        tclientTread.recv_handler=trecv_handler;
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp=edit_msg.getText().toString();
                if(!tmp.equals("")){
                    Message msg=tclientTread.send_handler.obtainMessage();
                    msg.what=1;
                    msg.obj=tmp;
                    tclientTread.send_handler.sendMessage(msg);
                }
            }
        });
    }
}
