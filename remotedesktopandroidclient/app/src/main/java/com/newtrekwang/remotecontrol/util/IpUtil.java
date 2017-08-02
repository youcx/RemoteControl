package com.newtrekwang.remotecontrol.util;

import android.util.Log;
/**
 * auther    : WJX
 * classname : IpUtil
 * desc      : Ip工具
 * createTime: 2017/7/30 10:27
 */
public class IpUtil {
    private static final String TAG = "IpUtil>>>>>>>";
    public static byte[] getBytesFromIPString(String ip){
        String [] ips=ip.split("[.]");
        byte[] ipbs=new byte[4];
        for (int i=0;i<4;i++){
            int m=Integer.parseInt(ips[i]);
            byte b=new Integer(m & 0xff).byteValue();
            ipbs[i]=b;
        }
        Log.i(TAG, "getBytesFromIPString: "+HexString.bytesToHex(ipbs));
        return ipbs;
    }
}
