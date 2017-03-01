package com.bawei.caoyaxiong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bawei.caoyaxiong.R;
import com.bawei.caoyaxiong.bean.Chapters;
import com.bawei.caoyaxiong.bean.Image;
import com.bawei.caoyaxiong.xlist_view.XListView;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/13.
 */
public class Chapter_Content extends Activity {
    private List<Image>list = new ArrayList<>();
    private MyAdapter adapter;
    private XListView lv;
    private DisplayImageOptions dd;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1b);
        lv = (XListView) findViewById(R.id.fragment1b_lv);
        chageImage();
        String name=getIntent().getStringExtra("name");
        String id=getIntent().getStringExtra("id");
        resolverJSON(name,id);
    }
    private void resolverJSON(final String a, final String b) {
        new Thread(new Runnable() {


            @Override
            public void run() {
                try {
                    String name = URLEncoder.encode(a, "utf-8");
                    String id = URLEncoder.encode(b, "utf-8");
                    String s = " http://japi.juhe.cn/comic/chapterContent?comicName="+name+"&id="+id+"&key=eb0b527e554e0c5f17095dc8c7be873f";
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
                        JSONArray jsonArray=jsonObject1.optJSONArray("imageList");

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject2=jsonArray.optJSONObject(i);
                            Gson gson=new Gson();
                            Image image=gson.fromJson(jsonObject2.toString(),Image.class);
                            list.add(image);
                        }
                        Message message=new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void chageImage() {
        ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(imageLoaderConfiguration);
        dd = new DisplayImageOptions.Builder().build();


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
                convertView=View.inflate(Chapter_Content.this,R.layout.item,null);
                v.im= (ImageView) convertView.findViewById(R.id.images);
                convertView.setTag(v);
            }else {
                v= (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(list.get(position).getImageUrl(),v.im,dd);
            return convertView;
        }
        class ViewHolder{
            ImageView im;
        }
    }
}
