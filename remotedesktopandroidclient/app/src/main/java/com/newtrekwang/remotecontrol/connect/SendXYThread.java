package com.newtrekwang.remotecontrol.connect;

import android.util.Log;

import com.newtrekwang.remotecontrol.util.BytesUtil;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by You on 2017/10/23.
 * 发送坐标和指令控制鼠标
 */

public class SendXYThread extends Thread {
   private int x,y;
   private  byte order;
    private Socket socket;
    private static final String TAG = "SendXYThread>>>";
    SendXYThread(int m_x,int m_y,byte m_order,Socket m_socket)
    {
        this.x=m_x;
        this.y=m_y;
        this.order=m_order;
        this.socket=m_socket;
    }

    @Override
    public void run() {
        try {
            if(socket==null)
                return;

            OutputStream ops = socket.getOutputStream();
            //发送包头
            byte[] header=new byte[8];
            header[0]=1;
            header[1]=7;
            header[5]=9;
            ops.write(header);
            ops.flush();
            //发送坐标
            byte[] xy=new byte[9];
            xy[0]=order;
            byte[] xbyte= BytesUtil.intToByte(x);
            byte[] ybyte=BytesUtil.intToByte(y);
            System.arraycopy(xbyte, 0,xy, 1, 4);
            System.arraycopy(ybyte,0,xy,5,4);
            for(int i=0;i<9;i++)
            {
                Log.i(TAG," "+xy[i]+"/n");
            }
            ops.write(xy);
            ops.flush();
            Log.i(TAG, "发送xy成功："+x+","+y );
        }catch (Exception e)
        {
            e.printStackTrace();
            Log.i(TAG, "send err");
        }
    }
}
