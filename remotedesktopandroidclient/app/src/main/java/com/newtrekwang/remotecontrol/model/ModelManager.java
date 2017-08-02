package com.newtrekwang.remotecontrol.model;

import android.util.Log;

import com.newtrekwang.remotecontrol.bean.Device;
import com.newtrekwang.remotecontrol.bean.LoginUserInfo;
import com.newtrekwang.remotecontrol.bean.PCdevice;
import com.newtrekwang.remotecontrol.bean.PcByIp;
import com.newtrekwang.remotecontrol.bean.PcByMac;
import com.newtrekwang.remotecontrol.bean.RegistUserInfo;
import com.newtrekwang.remotecontrol.bean.Result;
import com.newtrekwang.remotecontrol.bean.SessionUser;
import com.newtrekwang.remotecontrol.http.MyHttpClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * auther    : WJX
 * classname : ModelManager
 * desc      : 数据层
 * createTime: 2017/7/24 15:03
 */
public class ModelManager {
    private static final String TAG = "ModelManager>>>>>>>";
    /**
     * 模拟获取设备列表数据
     */
    public List<Device> getDevices() {
        List<Device> list = new ArrayList<>();
        list.add(new Device("127.0.0.1", "localhost", 8080));
        return list;
    }

    /**
     * Method Name: login
     * Params     : phone 电话号码 password 用户密码
     * Return type: Observable<Result<String>> 登录任务被观察者
     * Desc       : 登录
     */
    public Observable<Result<String>> login(String phone, String password) {
        LoginUserInfo loginUserInfo = new LoginUserInfo(phone, password);
        return MyHttpClient
                .getApi()
                .login(loginUserInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Method Name: regist
     * Params     : phone 手机号 password 密码
     * Return type: Observable<Result> 注册任务被观察者
     * Desc       : 注册
     */
    public Observable<Result> regist(String phone, String password,String username) {
        RegistUserInfo registUserInfo = new RegistUserInfo(phone, password,username);
        return MyHttpClient
                .getApi()
                .regist(registUserInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取所有PC设备
     * @param phone 手机号
     * @param sessoion sessionid
     * @return
     */
    public Observable<Result<List<PCdevice>>>  getAllDevices(String phone, String sessoion){
        SessionUser sessionUser=new SessionUser(phone,sessoion);
        return MyHttpClient
                .getApi()
                .getAllPcs(sessionUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 通过ip添加PC
     * @param phone  手机号
     * @param session sessionid
     * @param ip ip
     * @return
     */
    public Observable<Result> addPcByIp(String phone,String session,String  ip,String pcname){
        PcByIp pcByIp=new PcByIp(phone,session,ip,pcname);
        Log.i(TAG, "addPcByIp: >>>>>>>>"+pcByIp);
        return MyHttpClient
                .getApi()
                .addPcByIp(pcByIp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 通过mac添加PC
     * @param phone 手机号
     * @param session sessionid
     * @param mac mac
     * @return
     */
    public Observable<Result> addPcByMac(String phone,String session ,String mac){
        PcByMac pcByMac=new PcByMac(phone,mac,session);
        return MyHttpClient
                .getApi()
                .addPcByMAC(pcByMac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



}
