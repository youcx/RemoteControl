package com.newtrekwang.remotecontrol.keyboard;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;

import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.connect.SendKeyThread;

import java.net.Socket;

/**
 * Created by You on 2017/11/15.
 */

public class KeyboardUtil {
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private Socket client;

    public KeyboardUtil(Activity act,Socket client) {
        this.client = client;
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

                    Log.i("KEY_CODE", ":" + primaryCode);
                    SendKeyThread sendKey=new SendKeyThread(primaryCode,client);
                    sendKey.start();



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
