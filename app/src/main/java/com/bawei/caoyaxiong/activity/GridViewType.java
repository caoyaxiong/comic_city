package com.bawei.caoyaxiong.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.caoyaxiong.R;
import com.bawei.caoyaxiong.bean.TypeGrid;
import com.bawei.caoyaxiong.db_help.UserDao;
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
 * Created by dell on 2017/1/12.
 */
public class GridViewType extends Activity implements XListView.IXListViewListener{
    private MyAdapter adapter;
    private List<TypeGrid> list ;
    private XListView lv;
    private DisplayImageOptions dd;
    private List<TypeGrid> listType= new ArrayList<>();
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
    private UserDao dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1b);
        lv = (XListView) findViewById(R.id.fragment1b_lv);
        dao = new UserDao(this);
        
        chageImage();
        name = getIntent().getStringExtra("name");
        resolverJSON(name);
        //是否下拉刷新
        lv.setPullRefreshEnable(true);
        //是否上拉加载
        lv.setPullLoadEnable(true);
        //监听当前页面
        lv.setXListViewListener(this);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            private int num;
            private AlertDialog.Builder builder;

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                num = position;
                builder = new AlertDialog.Builder(GridViewType.this);
                AlertDialog dialog = builder.create();
                builder.setTitle("收藏漫画").setMessage("是否收藏").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name=listType.get(num).getName().toString();
                        String type=listType.get(num).getType().toString();
                        String area=listType.get(num).getArea().toString();
                        String des=listType.get(num).getDes().toString();
                        String coverImg=listType.get(num).getCoverImg().toString();
                        dao.addType(name,type,area,des,coverImg);
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return true;
            }
        });
       
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               Intent intent=new Intent(GridViewType.this,Chapter.class);
               String name=listType.get(position).getName().toString();
               
               intent.putExtra("name",name);
               startActivity(intent);
           }
       });
       
        
        
    }
    private void chageImage() {
        ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(imageLoaderConfiguration);
        dd = new DisplayImageOptions.Builder().build();


    }
    private void resolverJSON(final String name) {
        new Thread(new Runnable() {


            @Override
            public void run() {
                try {
                    String encode = URLEncoder.encode(name, "utf-8");
                    String s = "http://japi.juhe.cn/comic/book?name=&type="+encode+"&skip=&finish=&key=eb0b527e554e0c5f17095dc8c7be873f";
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
                        JSONArray jsonArray=jsonObject1.optJSONArray("bookList");
                       
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject2=jsonArray.optJSONObject(i);
                            Gson gson=new Gson();
                            TypeGrid typeGrid=gson.fromJson(jsonObject2.toString(),TypeGrid.class);
                            listType.add(typeGrid);

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
//下拉刷新
    @Override
    public void onRefresh() {
       listType.clear();
        resolverJSON(name);
        lv.stopRefresh();
        lv.stopLoadMore();
        lv.setRefreshTime("3000");
    }
//上拉加载
    @Override
    public void onLoadMore() {
        resolverJSON(name);
        adapter.notifyDataSetChanged();
        lv.stopRefresh();
        lv.stopLoadMore();
        lv.setRefreshTime("3000");
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listType.size();
        }

        @Override
        public Object getItem(int position) {
            return listType.get(position);
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
                convertView=View.inflate(getApplicationContext(),R.layout.gridview,null);
                v.iv= (ImageView) convertView.findViewById(R.id.g_iv);
                v.tv1= (TextView) convertView.findViewById(R.id.g_name);
                v.tv2= (TextView) convertView.findViewById(R.id.g_type);
                v.tv3= (TextView) convertView.findViewById(R.id.g_area);
                v.tv4= (TextView) convertView.findViewById(R.id.g_des);
                convertView.setTag(v);
            }else {
                v= (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(listType.get(position).getCoverImg(),v.iv,dd);
            v.tv1.setText(listType.get(position).getName());
            v.tv1.setTextColor(Color.RED);
            v.tv2.setText(listType.get(position).getType());
            v.tv2.setTextColor(Color.BLUE);
            v.tv3.setText(listType.get(position).getArea());
            v.tv3.setTextColor(Color.GREEN);
            v.tv4.setText(listType.get(position).getDes());
            v.tv4.setTextColor(Color.YELLOW);
            return convertView;
        }
        class ViewHolder{
            ImageView iv;
            TextView tv1;
            TextView tv2;
            TextView tv3;
            TextView tv4;
        }
    }
}
