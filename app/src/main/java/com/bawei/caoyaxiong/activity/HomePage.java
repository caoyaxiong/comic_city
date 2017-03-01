package com.bawei.caoyaxiong.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.caoyaxiong.R;
import com.bawei.caoyaxiong.fragment.Fragment1;
import com.bawei.caoyaxiong.fragment.Fragment2;
import com.bawei.caoyaxiong.fragment.Fragment3;
import com.bawei.caoyaxiong.fragment.Fragment4;

/**
 * Created by dell on 2017/1/10.
 */
public class HomePage extends AppCompatActivity implements View.OnClickListener{

    private Fragment1 f1;
    private Fragment2 f2;
    private Fragment3 f3;
    private Fragment4 f4;
    private FrameLayout fl;
    private TextView iv1;
    private TextView iv2;
    private TextView iv3;
    private TextView iv4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepager);
        //获取资源、id
        init();
    }

    private void init() {
        fl = (FrameLayout) findViewById(R.id.fl);
        iv1 = (TextView) findViewById(R.id.iv1);
        iv2 = (TextView) findViewById(R.id.iv2);
        iv3 = (TextView) findViewById(R.id.iv3);
        iv4 = (TextView) findViewById(R.id.iv4);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        f1 = new Fragment1();
        f2 = new Fragment2();
        f3 = new Fragment3();
        f4 = new Fragment4();
        getSupportFragmentManager().beginTransaction().add(R.id.fl,f1).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,f1).commit();
                
                break;
            case R.id.iv2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,f2).commit();
                break;
            case R.id.iv3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,f3).commit();
                break;
            case R.id.iv4:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,f4).commit();
                break;
        }
    }
}
