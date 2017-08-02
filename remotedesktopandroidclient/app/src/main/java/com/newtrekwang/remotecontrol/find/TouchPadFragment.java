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
    @BindView(R.id.touchPadTextView)
    TextView touchPadTextView;
    @BindView(R.id.leftClickButton)
    Button leftClickButton;
    @BindView(R.id.rightClickButton)
    Button rightClickButton;
    @BindView(R.id.btn_connect)
    Button button_connect;

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
        touchPadTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (true){
                    Log.i(TAG, "onTouch: >>>>>>>>>>"+event.getAction());
                    switch(event.getAction() ){
                        case MotionEvent.ACTION_DOWN:
                            //save X and Y positions when user touches the TextView
                            initX = (int) event.getX();
                            initY = (int) event.getY();
                            mouseMoved = false;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if(moultiTouch == false) {
                                disX = (int) event.getX()- initX; //Mouse movement in x direction
                                disY = (int) event.getY()- initY; //Mouse movement in y direction
                                /*set init to new position so that continuous mouse movement
                                is captured*/
                                initX = (int) event.getX();
                                initY = (int) event.getY();
                                if (disX != 0 || disY != 0) {

                                }
                                }
                            else {
                                disY = (int) event.getY()- initY; //Mouse movement in y direction
                                disY = (int) disY / 2;//to scroll by less amount
                                initY = (int) event.getY();
                                if(disY != 0) {

                                }
                            }
                            mouseMoved=true;
                            break;
                        case MotionEvent.ACTION_UP:
                            if(!mouseMoved){

                            }
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            initY = (int) event.getY();
                            mouseMoved = false;
                            moultiTouch = true;
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            if(!mouseMoved) {

                            }
                            moultiTouch = false;
                            break;
                    }
            }
                return true;
        }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.leftClickButton, R.id.rightClickButton,R.id.btn_connect})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.leftClickButton:


                break;
            case R.id.rightClickButton:
                break;
            case R.id.btn_connect:
                if (CustomApplication.getApplication().getSocket()==null){
                    Intent intent=new Intent(getActivity(), ConnectService.class);
                    getActivity().startService(intent);
                }
                break;
        }
    }

}
