package com.bawei.caoyaxiong.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.caoyaxiong.R;
import com.bawei.caoyaxiong.activity.Chapter;
import com.bawei.caoyaxiong.activity.GridViewType;
import com.bawei.caoyaxiong.bean.TypeGrid;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/10.
 */
public class Fragment3 extends Fragment {
    private  final String  name= "耽美漫画";
    private View view;
    private ImageView find_image;
    private EditText find_edit;
    private TextView tv;
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment3, null);
        find_image = (ImageView) view.findViewById(R.id.find);
        find_edit = (EditText) view.findViewById(R.id.ed);
        imageView = (ImageView) view.findViewById(R.id.f3_iv);
        tv = (TextView) view.findViewById(R.id.f3_tv);
        tv.setText(name);
        find_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = find_edit.getText().toString();
                if(a.equals("")){
                    Toast.makeText(getActivity(),"搜索内容为空",Toast.LENGTH_SHORT).show();
                    
                }else{
                    Toast.makeText(getActivity(),"搜索内容为空",Toast.LENGTH_SHORT).show();
                   
                }


            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),GridViewType.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
       
        return view;
    }

    
    
   
   


}
