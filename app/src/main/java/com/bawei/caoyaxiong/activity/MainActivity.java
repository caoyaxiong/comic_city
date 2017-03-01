package com.bawei.caoyaxiong.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bawei.caoyaxiong.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private String im1="http://img3.imgtn.bdimg.com/it/u=3950875133,109655218&fm=21&gp=0.jpg";
    private String im2="http://img0.imgtn.bdimg.com/it/u=213076409,1937742691&fm=21&gp=0.jpg";
    private String im3="http://img5.imgtn.bdimg.com/it/u=1507042213,4000043348&fm=23&gp=0.jpg";
    private String im4="http://img1.imgtn.bdimg.com/it/u=1159304688,1198518985&fm=23&gp=0.jpg";
    private String[] im={im1,im2,im3,im4};
    private int[] ims={R.mipmap.d, R.mipmap.c,R.mipmap.a,R.mipmap.f};
    private LinearLayout ll;
    private Button jump;
    private List<ImageView> list;
    private boolean flag;
    
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    int item=vp.getCurrentItem();
                    item++;
                    vp.setCurrentItem(item);
                    handler.sendEmptyMessageDelayed(0,3000);
                    break;
            }
        }
    };
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp = (ViewPager) findViewById(R.id.vp);
        ll = (LinearLayout) findViewById(R.id.ll);
        jump = (Button) findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CountDown.class);
                startActivity(intent);
                editor.putBoolean("flag",true);
                editor.commit();
                finish();
            }
        });
         convertImage(); 
        init();
        sp = getSharedPreferences("1502",MODE_PRIVATE);
        editor = sp.edit();
        flag=sp.getBoolean("flag",false);
        if(flag){
            Intent intent=new Intent(MainActivity.this,CountDown.class);
            startActivity(intent);
            finish();
        }
        vp.setAdapter(new MyAdapter());
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
            
                for(int i=0;i<ims.length;i++){
                    ImageView view=list.get(i);
                    if(i==position%4){
                        view.setImageResource(R.drawable.image_true);
                    }else{
                        view.setImageResource(R.drawable.image_false);
                    }
                }
                
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handler.sendEmptyMessageDelayed(0,3000);
    }
    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v=View.inflate(getApplicationContext(), R.layout.item, null);
            ImageView imageView=(ImageView) v.findViewById(R.id.images);
            int a=position%ims.length;
            imageView.setImageResource(ims[a]);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
            //super.destroyItem(container, position, object);
        }
    }
    //加载资源
    private void init() {
        list = new ArrayList<ImageView>();
        for(int i=0;i<ims.length ;i++){
            ImageView image=new ImageView(MainActivity.this);
            if(i==0){
                image.setImageResource(R.drawable.image_true);
            }else{
                image.setImageResource(R.drawable.image_false);
            }
            //生成小圆点，设置其宽高
            image.setLayoutParams(new LinearLayout.LayoutParams(20,20));
            //把图片不按比例缩放到view的比例
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(image);
            ll.addView(image);
            
        }
    }
    //图片转换
    private void convertImage() {
        ImageLoaderConfiguration.Builder builder=new ImageLoaderConfiguration.Builder(this);
        ImageLoader.getInstance().init(builder.build());
    }
}
