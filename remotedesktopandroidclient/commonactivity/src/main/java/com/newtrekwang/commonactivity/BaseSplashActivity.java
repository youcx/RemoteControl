package com.newtrekwang.commonactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseSplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFisrtIn= (boolean) SharePreferenceUtil.getParam(BaseSplashActivity.this,"isFirstIn",true);
        if (!isFisrtIn){
           notFirstIn();
        }else {
           isFirstIn();
        }
    }
    protected abstract  void isFirstIn();
    protected abstract  void notFirstIn();
}
