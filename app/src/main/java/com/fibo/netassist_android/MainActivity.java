package com.fibo.netassist_android;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btn_connect;
    private EditText edit_ip;
    private EditText edit_port;
    String server_ip;
    String tmp;
    Integer server_port;

    public static Socket_Assistor clientTread=null;
    private Handler recv_handler=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_connect=(Button)findViewById(R.id.btn_connect);
        edit_ip=(EditText)findViewById(R.id.Edit_IP);
        edit_port=(EditText)findViewById(R.id.Edit_PORT);
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                server_ip=edit_ip.getText().toString();
                tmp=edit_port.getText().toString();

                if(server_ip.equals("")){
                    Toast.makeText(MainActivity.this,"please input ip address",Toast.LENGTH_SHORT).show();
                }
                else if(tmp.equals("")){
                    Toast.makeText(MainActivity.this,"please input port",Toast.LENGTH_SHORT).show();
                }
                else{
                    server_port=Integer.valueOf(tmp);
                    if(socketInit()){
                        Toast.makeText(MainActivity.this,"server connected",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,TrafficActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"something wrong",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    private boolean socketInit(){
        config.isConnected=false;
        try{
            clientTread = new Socket_Assistor(recv_handler,server_ip,server_port);
            config.isConnected=true;


        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        new Thread(clientTread).start();
        return true;
    }
}
