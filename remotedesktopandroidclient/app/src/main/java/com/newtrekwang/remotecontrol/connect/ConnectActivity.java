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
public class ConnectActivity extends AppCompatActivity{
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
    //定义键盘按键
    private Button btn_input,btn_zoomIn,btn_zoomOut;
    private boolean isShowKeyboard;
    //private KeyboardUtil mKeyboard;
    private float mScale;

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
        mScale=2f;
        //获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenwidth=dm.widthPixels;
        screenheight=dm.heightPixels;
        mShow=(ShowScreen) findViewById(R.id.mshow);
        //键盘
        isShowKeyboard=false;
        btn_zoomIn=(Button) findViewById(R.id.btn_zoom_in);
        btn_zoomOut=(Button) findViewById(R.id.btn_zoom_out);
        btn_input=(Button) findViewById(R.id.btn_input);
        btn_zoomIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mScale<5)
                {
                    mScale+=1;
                    mShow.setmScale(mScale);
                }
            }
        });
        btn_zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mScale>1)
                {
                    mScale=mScale-1;
                    mShow.setmScale(mScale);
                }
            }
        });
        btn_input.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(!isShowKeyboard)
                {
                    new KeyboardUtil(ConnectActivity.this,CustomApplication.getApplication().getSocket()).showKeyboard();
                    //mKeyboard.showKeyboard();
                    isShowKeyboard=true;
                }else
                {
                    new KeyboardUtil(ConnectActivity.this,CustomApplication.getApplication().getSocket()).hideKeyboard();
                    //mKeyboard.hideKeyboard();
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
                return true;
            }
        });
        ButterKnife.bind(this);
        init();




    }


/**
 * 初始化一些数据和控件显示
 */
    private void init() {
        device = (PCdevice) getIntent().getSerializableExtra("device");//获取上一界面传来的数据
        if (device == null) {//数据为空，则退出界面
            finish();
        }
        //IpUtil.getBytesFromIPString(device.getIpaddr());
        initBroadCast();//初始化广播
        startConnect();//开始连接

    }



    /**
     * 开始连接
     */
    private void startConnect() {
        Intent intent=new Intent(ConnectActivity.this, ConnectService.class);
        intent.putExtra("pcnum",device.getUid());
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
        Intent intent=new Intent(ConnectActivity.this, ConnectService.class);
        stopService(intent);
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
                    final String pic_name=intent.getStringExtra("filename");//收到的图片文件路径
                    //                            刷新UI，显示图片
                    if(i==1) {
                        //将第一次的图片宽高赋值
                       // BitmapFactory.Options options = new BitmapFactory.Options();
                        //Bitmap bitmap = BitmapFactory.decodeFile(path,options);
                        //picwidth=options.outWidth;
                       // picheight=options.outHeight;
                        //将第一次的图片宽高赋给自定义的SurfaceView
                       // mShow.setPicheight(picheight);
                       // mShow.setPicwidth(picwidth);
                        mShow.setScreenwidth(screenwidth);
                        mShow.setScreenheight(screenheight);
                        mShow.setStart(pic_name);
                        //  connectActivityImg.setImageBitmap(bitmap);
                        i=0;
                    }
                    else
                    {
                        //Bitmap bitmap = BitmapFactory.decodeFile(path);
                        mShow.setStart(pic_name);
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
