package com.newtrekwang.commonlib.imageloader;

public class SettingUtil {
    private static boolean onlyWifiLoadImg =false;

    public static boolean isOnlyWifiLoadImg() {
        return onlyWifiLoadImg;
    }

    public static void setOnlyWifiLoadImg(boolean onlyWifiLoadImg) {
        SettingUtil.onlyWifiLoadImg = onlyWifiLoadImg;
    }
}
