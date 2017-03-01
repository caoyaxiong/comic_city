package com.bawei.caoyaxiong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bawei.caoyaxiong.R;
import com.bawei.caoyaxiong.bean.Chapters;
import com.bawei.caoyaxiong.bean.TypeGrid;
import com.bawei.caoyaxiong.xlist_view.XListView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/13.
 */
public class Chapter extends Activity {
    private MyAdapter adapter;
    private XListView lv;
    private List<Chapters>list = new ArrayList<>();
    private Handler handler=new Handler(){



        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    adapter = new MyAdapter();
                    lv.setAdapter(adapter);
                    break;
            }
        }
    };
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1b);
        lv = (XListView) findViewById(R.id.fragment1b_lv);
        name = getIntent().getStringExtra("name");
       
        resolverJSON(name);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Chapter.this,Chapter_Content.class);
                intent.putExtra("name",name);
              
                intent.putExtra("id",list.get(position).getId().toString());
               
                startActivity(intent);
            }
        });
    }
    private void resolverJSON(final String a) {
        new Thread(new Runnable() {
             @Override
            public void run() {
                try {
                    String encode = URLEncoder.encode(a, "utf-8");
                   
                    String s = "http://japi.juhe.cn/comic/chapter?comicName="+encode+"&key=eb0b527e554e0c5f17095dc8c7be873f";
                    URL url=new URL(s);
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    if(connection.getResponseCode()==200){
                        InputStream in=connection.getInputStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buffer=new byte[1024];
                        int len;
                        while((len=in.read(buffer))!=-1){
                            bos.write(buffer,0,len);
                        }
                        bos.close();
                        in.close();

                        JSONObject jsonObject = new JSONObject(bos.toString());
                        JSONObject jsonObject1=jsonObject.optJSONObject("result");
                        JSONArray jsonArray=jsonObject1.optJSONArray("chapterList");

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject2=jsonArray.optJSONObject(i);
                            Gson gson=new Gson();
                           Chapters chapters=gson.fromJson(jsonObject2.toString(),Chapters.class);
                            list.add(chapters);
                           
                        }
                       handler.sendEmptyMessage(2);
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
            ViewHolder v;
            if(convertView==null){
                v=new ViewHolder();
                convertView=View.inflate(Chapter.this,R.layout.fragment2,null);
                v.tv1= (TextView) convertView.findViewById(R.id.f2_tv1);
                v.tv2= (TextView) convertView.findViewById(R.id.f2_tv2);
                convertView.setTag(v);
            }else {
                v= (ViewHolder) convertView.getTag();
            }
            v.tv1.setText(list.get(position).getName());
            v.tv2.setText(list.get(position).getId());
            return convertView;
        }
        class ViewHolder{
            TextView tv1,tv2;
        }
    }
}
