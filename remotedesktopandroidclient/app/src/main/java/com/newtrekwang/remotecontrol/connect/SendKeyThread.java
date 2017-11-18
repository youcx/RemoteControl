package com.newtrekwang.remotecontrol.connect;

import android.util.Log;
import android.view.KeyEvent;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by You on 2017/11/8.
 */

public class SendKeyThread extends Thread {
    int key_code;
    private Socket client;

    public SendKeyThread(int key_code,Socket client) {
        this.key_code = key_code;
        this.client=client;
    }

    @Override
    public void run() {
        try{
            if(client==null)
                return;
            OutputStream ops=client.getOutputStream();
            //发送包头
            byte[] header=new byte[8];
            header[0]=1;
            header[1]=7;
            header[5]=3;
            ops.write(header);
            ops.flush();
            //发送包体
            byte[] body=new byte[3];
            body[0]=5;
            //body[1]=getorder();
            body[1]=(byte) key_code;
            body[2]=0;
            for(int i=0;i<3;i++)
            {
                Log.i("SendKey"," "+body[i]+"/n");
            }
            ops.write(body);
            ops.flush();


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private byte getorder()
    {
        switch (key_code)
        {
            case KeyEvent.KEYCODE_0:
                return 48;
            case KeyEvent.KEYCODE_1:
                return 49;
            case KeyEvent.KEYCODE_2:
                return 50;
            case KeyEvent.KEYCODE_3:
                return 51;
            case KeyEvent.KEYCODE_4:
                return 52;
            case KeyEvent.KEYCODE_5:
                return 53;
            case KeyEvent.KEYCODE_6:
                return 54;
            case KeyEvent.KEYCODE_7:
                return 55;
            case KeyEvent.KEYCODE_8:
                return 56;
            case KeyEvent.KEYCODE_9:
                return 57;
            case KeyEvent.KEYCODE_A:
                return 65;
            case KeyEvent.KEYCODE_B:
                return 66;
            case KeyEvent.KEYCODE_C:
                return 67;
            case KeyEvent.KEYCODE_D:
                return 68;
            case KeyEvent.KEYCODE_E:
                return 69;
            case KeyEvent.KEYCODE_F:
                return 70;
            case KeyEvent.KEYCODE_G:
                return 71;
            case KeyEvent.KEYCODE_H:
                return 72;
            case KeyEvent.KEYCODE_I:
                return 73;
            case KeyEvent.KEYCODE_J:
                return 74;
            case KeyEvent.KEYCODE_K:
                return 75;
            case KeyEvent.KEYCODE_L:
                return 76;
            case KeyEvent.KEYCODE_M:
                return 77;
            case KeyEvent.KEYCODE_N:
                return 78;
            case KeyEvent.KEYCODE_O:
                return 79;
            case KeyEvent.KEYCODE_P:
                return 80;
            case KeyEvent.KEYCODE_Q:
                return 81;
            case KeyEvent.KEYCODE_R:
                return 82;
            case KeyEvent.KEYCODE_S:
                return 83;
            case KeyEvent.KEYCODE_T:
                return 84;
            case KeyEvent.KEYCODE_U:
                return 85;
            case KeyEvent.KEYCODE_V:
                return 86;
            case KeyEvent.KEYCODE_W:
                return 87;
            case KeyEvent.KEYCODE_X:
                return 88;
            case KeyEvent.KEYCODE_Y:
                return 89;
            case KeyEvent.KEYCODE_Z:
                return 90;

            default:
                return 0;
        }
    }
}
