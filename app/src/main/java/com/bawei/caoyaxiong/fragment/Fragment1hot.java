package com.bawei.caoyaxiong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bawei.caoyaxiong.R;
import com.bawei.caoyaxiong.activity.GridViewType;
import com.bawei.caoyaxiong.bean.TypeGrid;
import com.bawei.caoyaxiong.db_help.UserDao;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/10.
 */
public class Fragment1hot extends Fragment {
    private String im1="http://imgs.juheapi.com/comic_xin/5559b86938f275fd560ad61f.jpg";
    private String im2="http://imgs.juheapi.com/comic_xin/5559b86938f275fd560ad634.jpg";
    private String im3="http://imgs.juheapi.com/comic_xin/5559b86938f275fd560ad613.jpg";
    private String im4="http://imgs.juheapi.com/comic_xin/5559b86938f275fd560ad662.jpg";
    
    private String[] im=new String[]{im1,im2,im3,im4};
    private int[] ims=new int[]{R.mipmap.vp1,R.mipmap.vp2,R.mipmap.vp3,R.mipmap.vp4};
    private List<ImageView>list;
    private ViewPager viewPager;
    private GridView gv;
    private DisplayImageOptions dd;
    private String path="http://japi.juhe.cn/comic/category?key=eb0b527e554e0c5f17095dc8c7be873f";
    private LinearLayout ll;
    private String[] arr;
    private String[] str = {"少年漫画","青年漫画","少女漫画","耽美漫画"};
    private Handler handler=new Handler(){
        private List<TypeGrid> listGrid;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    int item = viewPager.getCurrentItem();
                    if (item == im.length - 1) {
                        //如果是最后一个 就跳转到第一个
                        item = 0;
                    } else {
                        //累加
                        item++;
                    }
                    viewPager.setCurrentItem(item);
                    handler.sendEmptyMessageDelayed(0,3000);
                case 1:
                    gv.setAdapter(new MyAdapter1());
                    break;
              
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=View.inflate(getActivity(), R.layout.fragment1a,null);
        viewPager = (ViewPager) view.findViewById(R.id.fragment1a_vp);
        gv = (GridView) view.findViewById(R.id.fragment1a_gv);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), GridViewType.class);
                intent.putExtra("name",str[position]);
                startActivity(intent);
            }
        });
        chageImage();
        initImage();
        initPoint();
        initJson();
        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }
            @Override
            public void onPageSelected(int position) {
                int count = ll.getChildCount();
                for (int i = 0; i < count; i++) {
                    //获取子控件
                    View view = ll.getChildAt(i);
                    view.setEnabled(i == position ? false : true);
                }
        }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handler.sendEmptyMessageDelayed(0,3000);
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


    private void initImage() {
        list=new ArrayList<ImageView>();
        for(int i=0;i<im.length;i++){
            ImageView iv=new ImageView(getActivity());
            ImageLoader.getInstance().displayImage(im[i],iv);
            
            list.add(iv);            
        }
    }
    private void initPoint() {
        for (int i = 0; i < im.length; i++) {
            View view = new View(getActivity());
            view.setBackgroundResource(R.drawable.point_bg);
            //动态生成小圆点
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            //设置小圆的右边距
            params.rightMargin = 10;
            ll.addView(view, params);
            //设置唯一标识
            //view.setId(i);
            view.setTag(i);
        }
        View view = ll.getChildAt(0);
        view.setEnabled(false);
        

    }
class MyAdapter1 extends BaseAdapter{
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
   
    private void chageImage() {
       ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(getActivity());
        ImageLoader.getInstance().init(imageLoaderConfiguration);
        dd = new DisplayImageOptions.Builder().build();
        
        
    }
    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return im.length ;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object ;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = list.get(position);
            viewPager.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
            // super.destroyItem(container, position, object);
        }
    }
}
