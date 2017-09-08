package com.newtrekwang.remotecontrol.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.newtrekwang.remotecontrol.CustomApplication;
import com.newtrekwang.remotecontrol.util.BytesUtil;
import com.newtrekwang.remotecontrol.util.HexString;
import com.newtrekwang.remotecontrol.util.IpUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * auther    : WJX
 * classname : ConnectService
 * desc      : 连接服务
 * createTime: 2017/7/30 10:07
 */
public class ConnectService extends IntentService {
    private static final String TAG = "ConnectService>>>>>";
    //    TCP服务端IP
    public static final String IP = "192.168.1.123";
    //public static final String IP = "119.29.201.35";
    //    TCP服务端端口
    //public static final int PORT=12346;
    public static final int PORT = 9900;
    //  广播管理器
    private LocalBroadcastManager mLocalBroadcastManager;
    //    广播消息类型标志
    public static final String CONNECT_BROADCAST_ACTION = "com.newtrekwang.connect";
    public static final String CONNECT_PROGRESS_BROADCAST_ACTION = "com.newtrekwang.connect";
    public static final String SHOWIMAGE_BROADCAST_ACTION = "showimage";
    //    要连接的设备IP，需要从intent中获取
    private String pcip;


    public ConnectService() {
        super("ConnectService");
    }


    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: >>>>>");
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        sendServiceStatus("服务启动");
    }

    /**
     * 发广播，消息类型是CONNECT_BROADCAST_ACTION
     *
     * @param str 字符串消息
     */
    private void sendServiceStatus(String str) {
        Intent intent = new Intent(CONNECT_BROADCAST_ACTION);
        intent.putExtra("status", str);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    /**
     * 收到其他组件的intent ,可以获取intent里的数据
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        pcip = intent.getStringExtra("pcip");
        connectToServer(IP, PORT);//开始TCP连接
    }


    /**
     * 销毁前调用
     */
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: >>>>>>>>>");
        mLocalBroadcastManager = null;
        super.onDestroy();
    }


    /**
     * 连接到服务器
     */
    private void connectToServer(final String ip, int por) {
        try {
            Socket socket = new Socket(IP,por);
            if (socket != null) {
                Log.i(TAG, "connectToServer: success !!!!!!!!!!");
                sendServiceStatus("连接服务器成功！");
                sendMe(socket);
                CustomApplication.getApplication().setSocket(socket);//将连接成功的socket赋给全局变量
                dealRead(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "connectToServer: fail !!!!!!!!!!");
            sendServiceStatus("连接服务器异常！");
            CustomApplication.getApplication().setSocket(null);
        }
    }

    /**
     * 根据协议，连接成功后先发8个字节，里面表示客户端类型和要连接设备的IP
     */
    private void sendMe(Socket socket) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes = new byte[8];
            bytes[0] = 1;
            bytes[1] = 2;
            byte[] ipbytes = IpUtil.getBytesFromIPString("192.168.1.109");//IP字符串转换为字节数据
            System.arraycopy(ipbytes, 0, bytes, 2, 4);
            Log.i(TAG, "sendMe: >>>>>>发送IP  : " + pcip + "    " + HexString.bytesToHex(bytes));
            outputStream.write(bytes);
            outputStream.flush();


//           要PC端 开始发图片 的8字节指令
            byte[] bytes1 = new byte[8];
            bytes1[0] = 1;
            bytes1[1] = 4;
            Log.i(TAG, "sendMe: 发送开始图片指令" + HexString.bytesToHex(bytes1));
            outputStream.write(bytes1);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 统一处理读，一个死循环，都是先读8个字节，根据协议判断含义，根据含义再继续读
     */
    private void dealRead(Socket socket) {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            while (true) {
                byte[] header=new byte[8];
                byte[] bytes = new byte[1024];
                int length = 0;
//                首先读取8个字节的包头，read方法会阻塞直到读到数据
                length = inputStream.read(header, 0, 8);
                if (length == 8) {
                    length = 0;//清空读到的长度
                    byte[] len = new byte[4];
                    len[0] = header[2];
                    len[1] = header[3];
                    len[2] = header[4];
                    len[3] = header[5];
//                    包体长度
                    int bodyLength = BytesUtil.byteArrayToInt(len);
                    Log.i(TAG, "dealRead: 收到包头 解析内容为：" + HexString.bytesToHex(header, 8) + " body length:" + bodyLength);
                    switch (header[0]) {//判断指令类型
                        case 1:
                            //InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            //inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            // TODO: 2017/7/30
                            break;
                        case 2:
                            if (header[1] == 2) {//发来是图片
                               // File file = new File(getFilesDir(), "1.webp");
                                File file=new File("/storage/emulated/0/1.webp");
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                int size = 0;
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                while (size != bodyLength) {
                                    if(bodyLength-size>=1024)
                                    length = inputStream.read(bytes);
                                    else
                                        length=inputStream.read(bytes,0,bodyLength-size);
                                    fileOutputStream.write(bytes, 0, length);
                                    size += length;
                                 //   Log.i(TAG, "dealRead: >>>>>>>收到长度" + size);
                                  //  Log.i(TAG,"dealRead: >>>>>>>已完成"+size/bodyLength*100+"%");
                                }
                                Log.i(TAG, "dealRead: 结束接收文件保存文件在" + file.getAbsolutePath());
                                fileOutputStream.close();
//                            广播通知显示图片
                                Intent intent = new Intent(SHOWIMAGE_BROADCAST_ACTION);

                                intent.putExtra("path", file.getAbsoluteFile().toString());
                                mLocalBroadcastManager.sendBroadcast(intent);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendServiceStatus("流异常！");
        }
    }

}
