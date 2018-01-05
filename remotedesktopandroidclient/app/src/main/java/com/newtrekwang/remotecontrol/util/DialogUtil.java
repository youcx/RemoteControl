package com.newtrekwang.remotecontrol.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.newtrekwang.commonactivity.SharePreferenceUtil;
import com.newtrekwang.remotecontrol.R;
import com.newtrekwang.remotecontrol.bean.PCdevice;

/**
 * Created by You on 2017/12/21.
 */

public class DialogUtil {
    public static void deletePCDialog(Context context, PCdevice device){
        LayoutInflater factory = LayoutInflater.from(context);
        AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
        ad1.setTitle("删除电脑:");
        ad1.setIcon(android.R.drawable.ic_dialog_info);
        ad1.setMessage("是否删除当前名为："+device.getPcname()+"的电脑？");
        ad1.setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        ad1.setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        ad1.show();// 显示对话框
    }
}
