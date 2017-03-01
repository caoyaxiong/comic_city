package com.bawei.caoyaxiong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bawei.caoyaxiong.R;


/**
 * Created by dell on 2017/1/10.
 */
public class Fragment1 extends Fragment implements View.OnClickListener{

    private Fragment1save f2;
    private Fragment1hot f1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(), R.layout.fragment1,null);
        FrameLayout fll= (FrameLayout) view.findViewById(R.id.fll);
        TextView hot= (TextView) view.findViewById(R.id.tv_hot);
        TextView save= (TextView) view.findViewById(R.id.tv_save);
        hot.setOnClickListener(this);
        save.setOnClickListener(this);
        f1 = new Fragment1hot();
        f2 = new Fragment1save();
        getChildFragmentManager().beginTransaction().add(R.id.fll,f1).commit();
        return view;
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.tv_hot:
             getChildFragmentManager().beginTransaction().replace(R.id.fll,f1).commit();
             break;
         case R.id.tv_save:
             getChildFragmentManager().beginTransaction().replace(R.id.fll,f2).commit();
             break;
     }  
    }
}
