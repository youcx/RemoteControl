package com.newtrekwang.remotecontrol.connect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.newtrekwang.remotecontrol.CustomApplication;
import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.bean.PCdevice;
import com.newtrekwang.remotecontrol.keyboard.KeyboardUtil;
import com.newtrekwang.remotecontrol.service.ConnectService;
import com.newtrekwang.remotecontrol.util.BytesUtil;
import com.newtrekwang.remotecontrol.util.IpUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newtrekwang.remotecontrol.service.ConnectService.SHOWIMAGE_BROADCAST_ACTION;
/**
 * auther    : WJX
 * classname : ConnectActivity
 * desc      : 桌面显示界面
 * createTime: 2017/7/30 9:55
 */
public class ConnectActivity extends AppCompatActivity {
    private static final String TAG = "ConnectActivity>>>";
    Socket client;

//    imageView 显示发过来的图片
    //@BindView(R.id.connectActivity_img)
    //ImageView connectActivityImg;
//定义显示屏幕的SurfaceView
    private ShowScreen mShow;
//    存放上一界面发过来的数据
    private PCdevice device;
//   广播管理
    private LocalBroadcastManager mLocalBroadcastManager;
//    广播接收者
    private BroadcastReceiver broadcastReceiver;
    //定义屏幕宽高
    int screenwidth;
    int screenheight;
    //定义图片宽高
    int picwidth;
    int picheight;
    //定义触摸点xy
    int rawx;
    int rawy;
    int i=1;
    //定义鼠标手势
    private MyGestureListener mgListener;
    private GestureDetector mDetector;
    //定义键盘按键
    private Button btn_input;
    private boolean isShowKeyboard;


    /**
 * 界面初始化
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        super.onCreate(savedInstanceState);
        //setContentView(new ShowScreen(this));
        setContentView(R.layout.activity_connect);
        //获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenwidth=dm.widthPixels;
        screenheight=dm.heightPixels;
        mShow=(ShowScreen) findViewById(R.id.mshow);
        //键盘
        isShowKeyboard=false;
        btn_input=(Button) findViewById(R.id.btn_input);
        btn_input.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(!isShowKeyboard)
                {
                    new KeyboardUtil(ConnectActivity.this,CustomApplication.getApplication().getSocket()).showKeyboard();
                    isShowKeyboard=true;
                }else
                {
                    new KeyboardUtil(ConnectActivity.this,CustomApplication.getApplication().getSocket()).hideKeyboard();
                    isShowKeyboard=false;
                }
               // InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
               // inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        btn_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN&&CustomApplication.getApplication().getSocket()!=null)
                {
                    SendKeyThread sendKeyThread=new SendKeyThread(keyCode,CustomApplication.getApplication().getSocket());
                    sendKeyThread.start();
                }
                /*
                if(keyCode == KeyEvent.KEYCODE_A && event.getAction() == KeyEvent.ACTION_DOWN){
                    //do something...
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 97, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_B && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 98, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_1 && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 49, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_C && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 99, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_D && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 100, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_E && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 101, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_F && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 102, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_G && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 103, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_H && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 104, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_I && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 105, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_J && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 106, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_K && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(CustomApplication.getApplication().getSocket()!=null) {
                        SendKeyThread sendKey = new SendKeyThread((byte) 107, CustomApplication.getApplication().getSocket());
                        sendKey.start();
                    }
                    return true;
                }*/
                return true;
            }
        });
        //实例化GestureListener与GestureDetector对象，鼠标操作
        mgListener = new MyGestureListener();
        mDetector = new GestureDetector(this, mgListener);
        ButterKnife.bind(this);
        init();

//            if(CustomApplication.getApplication().getSocket()!=null) {
//                将全局变量socket赋给client
//                client = CustomApplication.getApplication().getSocket();
//            }
       /* connectActivityImg.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    int rawx =(int) event.getRawX();
                    int rawy =(int) event.getRawY();
                    int x=rawx*picwidth/screenwidth;
                    int y=rawy*picheight/screenheight;
                    if(CustomApplication.getApplication().getSocket()!=null){
                    //触摸后发送触摸点坐标
                    sendXY(x, y, CustomApplication.getApplication().getSocket());
                    }

                }
                return true;
            }
        });*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    private class MyGestureListener implements GestureDetector.OnGestureListener
    {
        @Override
        public boolean onDown(MotionEvent event) {
            //触摸点xy
            rawx =(int) event.getRawX();
            rawy =(int) event.getRawY();
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int x=rawx*picwidth/screenwidth;
            int y=rawy*picheight/screenheight;
            if(CustomApplication.getApplication().getSocket()!=null){
                //触摸后发送触摸点坐标
                //sendXY(x, y,(byte)4 ,CustomApplication.getApplication().getSocket());
                SendXYThread send=new SendXYThread(x, y,(byte)4 ,CustomApplication.getApplication().getSocket());
                send.start();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            int x=rawx*picwidth/screenwidth;
            int y=rawy*picheight/screenheight;
            if(CustomApplication.getApplication().getSocket()!=null){
                //触摸后发送触摸点坐标
                //sendXY(x, y,(byte)3 ,CustomApplication.getApplication().getSocket());
                SendXYThread send=new SendXYThread(x, y,(byte)3 ,CustomApplication.getApplication().getSocket());
                send.start();
            }
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int x=rawx*picwidth/screenwidth;
            int y=rawy*picheight/screenheight;
            if(CustomApplication.getApplication().getSocket()!=null){
                //触摸后发送触摸点坐标
                //sendXY(x,y,(byte)2 ,CustomApplication.getApplication().getSocket());
                SendXYThread send=new SendXYThread(x, y,(byte)2 ,CustomApplication.getApplication().getSocket());
                send.start();
            }
            return false;
        }
    }
/**
 * 初始化一些数据和控件显示
 */
    private void init() {
        device = (PCdevice) getIntent().getSerializableExtra("device");//获取上一界面传来的数据
        if (device == null) {//数据为空，则退出界面
            finish();
        }
        IpUtil.getBytesFromIPString(device.getIpaddr());
        initBroadCast();//初始化广播
        startConnect();//开始连接

    }

    /**
     * 发送触摸坐标
     */
    private void sendXY(int x,int y,byte order,Socket socket)
    {


    }

    /**
     * 开始连接
     */
    private void startConnect() {
        Intent intent=new Intent(ConnectActivity.this, ConnectService.class);
        intent.putExtra("pcip",device.getIpaddr());
        startService(intent);
    }


    /**
     * 显示Toast
     */
    private void showToast(String str) {
        Toast.makeText(ConnectActivity.this, str, Toast.LENGTH_SHORT).show();
    }



    /**
     * 销毁前移除looper里所有的消息
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
//        解除广播注册
        mLocalBroadcastManager.unregisterReceiver(broadcastReceiver);
        try {
            //暂时是退出界面就关闭连接
            if(CustomApplication.getApplication().getSocket()!=null)
            CustomApplication.getApplication().getSocket().close();
            Log.i(TAG, "关闭连接" );
        } catch (IOException e) {
            Log.i(TAG,"退出异常");
            e.printStackTrace();
        }
    }


    /**
     * 初始化广播接收器
     */
    private void initBroadCast(){
        mLocalBroadcastManager=LocalBroadcastManager.getInstance(this);
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(SHOWIMAGE_BROADCAST_ACTION)){//广播消息类型
                    Log.i(TAG, "onReceive: >>收到广播");
                    final String path=intent.getStringExtra("path");//收到的图片文件路径
                    //                            刷新UI，显示图片
                    if(i==1) {
                        //将第一次的图片宽高赋值
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
                        picwidth=options.outWidth;
                        picheight=options.outHeight;
                        //将第一次的图片宽高赋给自定义的SurfaceView
                        mShow.setPicheight(picheight);
                        mShow.setPicwidth(picwidth);
                        mShow.setScreenwidth(screenwidth);
                        mShow.setScreenheight(screenheight);
                        mShow.setStart(path);
                        //  connectActivityImg.setImageBitmap(bitmap);
                        i=0;
                    }
                    else
                    {
                        //Bitmap bitmap = BitmapFactory.decodeFile(path);
                        mShow.setStart(path);
                        // connectActivityImg.setImageBitmap(bitmap);
                        Log.i("ConnectActivity","显示图片");
                    }
                 /*   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });*/


//                    Glide.with(ConnectActivity.this)
//                            .load(path)
//                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                            .dontAnimate()
//                            .into(connectActivityImg);
                }
            }
        };

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(SHOWIMAGE_BROADCAST_ACTION);
        mLocalBroadcastManager.registerReceiver(broadcastReceiver,intentFilter);//注册广播
    }


}
