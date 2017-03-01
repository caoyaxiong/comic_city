package com.bawei.caoyaxiong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bawei.caoyaxiong.R;

/**
 * Created by dell on 2017/1/10.
 */
public class Fragment4 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=View.inflate(getActivity(), R.layout.fragment4,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.f4_iv);
        return view;
    }
}
