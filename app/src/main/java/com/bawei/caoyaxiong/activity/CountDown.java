package com.bawei.caoyaxiong.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.caoyaxiong.R;

/**
 * Created by dell on 2017/1/9.
 */
public class CountDown extends AppCompatActivity{

    private ImageView imageView;
    private TextView textView;
    private int a=3;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    textView.setText(a+"s");
                    textView.setTextColor(Color.RED);
                    a--;
                    if(a==0){
                        Intent intent=new Intent(CountDown.this,Login_Sign.class);
                        startActivity(intent);
                        finish();
                    }
                    handler.sendEmptyMessageDelayed(0,1000);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);
        imageView = (ImageView) findViewById(R.id.count_iv);
        textView = (TextView) findViewById(R.id.tv);
       handler.sendEmptyMessageDelayed(0,1000);
    }
}
