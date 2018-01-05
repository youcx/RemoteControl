package com.newtrekwang.remotecontrol.keyboard;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.connect.SendKeyThread;
import com.newtrekwang.remotecontrol.connect.ShowScreen;

import java.net.Socket;

/**
 * Created by You on 2017/11/15.
 */

public class KeyboardUtil {
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private Socket client;
    //private ShowScreen mSurfaceView;
    private float mScale;

    public KeyboardUtil(Activity act,Socket client) {
        this.client = client;
        //this.mSurfaceView=mmSurface;
        this.mScale=2;
        mKeyboard=new Keyboard(act, R.xml.mkeyboard);
        mKeyboardView=(KeyboardView)act.findViewById(R.id.mKeyView);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(onKeyboardActionListener);
    }
    KeyboardView.OnKeyboardActionListener onKeyboardActionListener=new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            //键盘控制缩放
            if(primaryCode==-11)
            {
                if(mScale>1)
                {
                    mScale=mScale-1;
                    //mSurfaceView.setmMatrix(mScale);
                }

            }else if(primaryCode==-12)
            {
                if(mScale<5)
                {
                    mScale+=1;
                    //mSurfaceView.setmMatrix(mScale);
                }
            }else
            {
                Log.i("KEY_CODE", ":" + primaryCode);
                SendKeyThread sendKey=new SendKeyThread(primaryCode,client);
                sendKey.start();
            }





        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };
    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.GONE);
        }
    }
}
