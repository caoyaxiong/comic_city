package com.bawei.caoyaxiong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.caoyaxiong.R;
import com.bawei.caoyaxiong.db_help.UserDao;

/**
 * Created by dell on 2017/1/10.
 */
public class Login_Sign extends AppCompatActivity implements View.OnClickListener{

    private EditText user_name;
    private EditText user_pwd;
    private CheckBox remeber_pwd;
    private CheckBox automatic_login;
    private ImageView login;
    private TextView sign;
    private UserDao dao;
    private String name;
    private String pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign);
        //获取控件id
        init();
        dao = new UserDao(this);
        name = user_name.getText().toString();
        pwd = user_pwd.getText().toString();
        sign.setOnClickListener(this);
        login.setOnClickListener(this);
        remeber_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                
            }
        });
    }

    private void init() {
        user_name = (EditText) findViewById(R.id.user_name);
        user_pwd = (EditText) findViewById(R.id.user_pwd);
        remeber_pwd = (CheckBox) findViewById(R.id.remember_pwd);
        automatic_login = (CheckBox) findViewById(R.id.automatic_login);
        login = (ImageView) findViewById(R.id.login);
        sign = (TextView) findViewById(R.id.sign);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign:
                dao.add(name,pwd);
                break;
            case R.id.login:
                boolean flag=dao.find(name,pwd);
                if(flag){
                    Intent intent=new Intent(Login_Sign.this,HomePage.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"账号或密码不正确",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
