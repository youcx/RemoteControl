package com.newtrekwang.commonlib.util;

import android.text.TextUtils;

public class StringUtils {
    /**
     * 提取fileUrl中的文件名
     */
    public static String getUrlFileName(String fileUrl){
        if (TextUtils.isEmpty(fileUrl)){
            throw new NullPointerException("fileUrl为空！");
        }
        String fileName= fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        if (!TextUtils.isEmpty(fileName)){
            return fileName;
        }
        return "";
    }
}
