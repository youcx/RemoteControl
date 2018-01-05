package com.newtrekwang.remotecontrol.login;

import android.content.Intent;
import android.os.Handler;

import com.newtrekwang.commonactivity.BaseSplashActivity;

/**
 * auther    : WJX
 * classname : SplashActivity
 * desc      : 欢迎页界面
 * createTime: 2017/7/30 10:23
 */
public class SplashActivity extends BaseSplashActivity {
    @Override
    protected void isFirstIn() {
//        如果是第一次启动应用
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        },3000);

    }

    @Override
    protected void notFirstIn() {
//        如果不是第一次启动应用
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        },3000);

    }

}
