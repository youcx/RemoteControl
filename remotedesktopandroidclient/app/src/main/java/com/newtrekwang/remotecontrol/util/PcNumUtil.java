package com.newtrekwang.remotecontrol.util;

import android.util.Log;

/**
 * Created by You on 2017/12/18.
 */

public class PcNumUtil {
    private static final String TAG = "PcNumUtil>>>>>>>";
    public static byte[] getBytesFromPcNum(String pcnum){
        int num=Integer.parseInt(pcnum);
        byte[] numbs=BytesUtil.intToByte(num);
        Log.i(TAG, "getBytesFromIPString: "+HexString.bytesToHex(numbs));
        return numbs;
    }
}
