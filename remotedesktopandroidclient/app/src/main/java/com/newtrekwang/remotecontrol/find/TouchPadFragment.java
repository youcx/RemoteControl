package com.newtrekwang.remotecontrol.find;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.newtrekwang.remotecontrol.CustomApplication;
import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.service.ConnectService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * 鼠标控制碎片界面，相当于触摸板界面
 */
public class TouchPadFragment extends Fragment {
    private static final String TAG = "TouchPadFragment>>>>>>>";

    Unbinder unbinder;

    private int initX, initY, disX, disY;
    boolean mouseMoved = false, moultiTouch = false;


    public TouchPadFragment() {
        // Required empty public constructor
    }


    public static TouchPadFragment newInstance() {
        TouchPadFragment fragment = new TouchPadFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_touch_pad, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
