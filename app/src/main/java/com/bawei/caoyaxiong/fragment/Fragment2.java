package com.bawei.caoyaxiong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.caoyaxiong.R;
import com.bawei.caoyaxiong.activity.GridViewType;
import com.bawei.caoyaxiong.xlist_view.XListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dell on 2017/1/10.
 */
public class Fragment2 extends Fragment {
    private int[] ims=new int[]{R.mipmap.vp1,R.mipmap.vp2,R.mipmap.vp3,R.mipmap.vp4};
    private String path="http://japi.juhe.cn/comic/category?key=eb0b527e554e0c5f17095dc8c7be873f";
    private String[] arr;
    private XListView xlv;
    private MyAdapter adapter;
    private String[] str = {"少年漫画","青年漫画","少女漫画","耽美漫画"};
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    adapter = new MyAdapter();
                    xlv.setAdapter(adapter);
                    break;
            }
        }
    };
    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=View.inflate(getActivity(), R.layout.fragment1b,null);
        xlv = (XListView) view.findViewById(R.id.fragment1b_lv);
        initJson();
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), GridViewType.class);
                intent.putExtra("name",str[position]);
                startActivity(intent);
            }
        });
        return view;
    }
    private void initJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(path);
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    if(connection.getResponseCode()==200){
                        InputStream in=connection.getInputStream();
                        ByteArrayOutputStream bos=new ByteArrayOutputStream();
                        byte[] buffer=new byte[1024];
                        int len;
                        while((len=in.read(buffer))!=-1){
                            bos.write(buffer,0,len);
                            
                        }
                        bos.close();
                        in.close();
                        JSONObject json=new JSONObject(bos.toString());
                        JSONArray array=json.optJSONArray("result");
                        arr = new String[array.length()];
                        for(int i=0;i<array.length();i++)
                        {
                            arr[i]=array.optString(i);
                          
                        }
                        Message message = handler.obtainMessage();
                        message.what=1;
                        message.sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return arr.length;
        }
        @Override
        public Object getItem(int position) {
            return arr[position];
        }
        @Override
        public long getItemId(int position) {
            return  position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder v;
            if(convertView==null){
                v=new ViewHolder();
                convertView=View.inflate(getActivity(),R.layout.fragment_item,null);
                v.imageView= (ImageView) convertView.findViewById(R.id.gv_iv);
                v.textView= (TextView) convertView.findViewById(R.id.gv_tv);
                convertView.setTag(v);
            }else{
                v= (ViewHolder) convertView.getTag();
            }
            v.textView.setText(arr[position]);
            v.imageView.setImageResource(ims[position]);
            return convertView;
        }
        class ViewHolder{
            ImageView imageView;
            TextView textView;
        }
    }
   
}
