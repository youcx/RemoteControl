package com.newtrekwang.remotecontrol;

import android.app.Application;

import java.net.Socket;
/**
 * 全局应用对象
 */
public class CustomApplication extends Application {

    private Socket socket;

//    类似单例，这样无论哪个地方都可以引用到application
    private static CustomApplication application;
    public static CustomApplication getApplication() {
        return application;
    }



/**
 * 应用最开始启动时，会先执行这里
 */
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
