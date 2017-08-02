package com.newtrekwang.remotecontrol.util;

import com.newtrekwang.remotecontrol.CustomApplication;

import java.io.IOException;
import java.io.OutputStream;

/**
 *  socket工具类
 */
public class SocketUtil {
    /**
     * 构造一个发字节数组的runnable
     * @param bytes
     * @return
     */
    public static  Runnable sendHeaderData(final byte[] bytes){
return new Runnable() {
    @Override
    public void run() {
        if (CustomApplication.getApplication().getSocket()==null){
            return;
        }
            try {
                OutputStream outputStream= CustomApplication.getApplication().getSocket().getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
};
}
}
