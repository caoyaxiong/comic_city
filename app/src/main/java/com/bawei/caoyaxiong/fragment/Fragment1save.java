package com.bawei.caoyaxiong.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.caoyaxiong.R;
import com.bawei.caoyaxiong.bean.TypeGrid;
import com.bawei.caoyaxiong.db_help.UserDao;
import com.bawei.caoyaxiong.xlist_view.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/10.
 */
public class Fragment1save extends Fragment implements XListView.IXListViewListener{
    private XListView lv;
    private DisplayImageOptions dd;
    private List<TypeGrid> listType= new ArrayList<>();
    private UserDao dao;
    private MyAdapter adapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                adapter = new MyAdapter();
                lv.setAdapter(adapter);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=View.inflate(getActivity(), R.layout.fragment1b,null);
        lv = (XListView) view.findViewById(R.id.fragment1b_lv);
        dao = new UserDao(getActivity());
        chageImage();
        listType=dao.findType();
        Log.e("zzz",listType.toString());
        
       handler.sendEmptyMessage(1);
        //是否下拉刷新
        lv.setPullRefreshEnable(true);
        //是否上拉加载
        lv.setPullLoadEnable(true);
        //监听当前页面
        lv.setXListViewListener(this);
       
        return view;
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        listType.clear();
        listType=dao.findType();
        lv.setAdapter(adapter);
        lv.stopRefresh();
        lv.stopLoadMore();
        lv.setRefreshTime("3000");
    }
    //上拉加载
    @Override
    public void onLoadMore() {
        listType=dao.findType();
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv.stopRefresh();
        lv.stopLoadMore();
        lv.setRefreshTime("3000");
    }
    private void chageImage() {
        ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(getActivity());
        ImageLoader.getInstance().init(imageLoaderConfiguration);
        dd = new DisplayImageOptions.Builder().build();


    }

    class MyAdapter extends BaseAdapter {

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
                convertView=View.inflate(getActivity(),R.layout.gridview,null);
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
