package com.newtrekwang.remotecontrol.http;


import com.newtrekwang.remotecontrol.bean.DeletePcByUID;
import com.newtrekwang.remotecontrol.bean.LoginUserInfo;
import com.newtrekwang.remotecontrol.bean.Login_json_;
import com.newtrekwang.remotecontrol.bean.PCdevice;
import com.newtrekwang.remotecontrol.bean.PcByIp;
import com.newtrekwang.remotecontrol.bean.PcByMac;
import com.newtrekwang.remotecontrol.bean.PcByNum;
import com.newtrekwang.remotecontrol.bean.RegistUserInfo;
import com.newtrekwang.remotecontrol.bean.Result;
import com.newtrekwang.remotecontrol.bean.SessionUser;
import com.newtrekwang.remotecontrol.bean.UpdateUsername;
import com.newtrekwang.remotecontrol.bean.UpdateUserpasswd;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by WJX .
 * Desc: 放服务接口的被观察者
 * Created on 2017/1/15 15:34.
 * Mail:408030208@qq.com
 */

public interface ApiStores {
    /**
    * 删除电脑
    * */
    @POST("deletePCByUID")
    Observable<Result> deletepcbyuid(@Body DeletePcByUID deletePcByUID);


    /**
     * 登录
     * @param loginUserInfo 登录信息
     * @return
     */
    @POST("login")
    Observable<Result<Login_json_>> login(@Body LoginUserInfo loginUserInfo);

    /**
     * 注册
     * @param registUserInfo  注册信息
     * @return
     */
    @POST("regist")
    Observable<Result> regist(@Body RegistUserInfo registUserInfo);


    /**
     * 获取所有PC列表
     * @param sessionUser  sesseion数据
     * @return
     */
    @POST("listAllPC")
    Observable<Result<List<PCdevice>>> getAllPcs(@Body SessionUser sessionUser);


    /**
     * 通过wifi添加设备
     * @param pcByIp  输入参数
     * @return
     */
    @POST("addPCByIp")
    Observable<Result> addPcByIp(@Body PcByIp pcByIp);


    /**
     * 通过mac添加pc
     * @param pcByMac 输入参数
     * @return
     */
    @POST("addPCByMac")
    Observable<Result> addPcByMAC(@Body PcByMac pcByMac);


    /**
     *修改用户名
     */

    @POST("updateUserName")
    Observable<Result> Updateusername(@Body UpdateUsername updateUsername);

    /**
     * 修改用户密码
     * @param updateUserpasswd
     * @return
     */
    @POST("updateUserPassword")
    Observable<Result> Updateuserpasswd(@Body UpdateUserpasswd updateUserpasswd);


    /**
     *通过随机码添加PC
     * @return
     */
    @POST("addPCByUID")
    Observable<Result> addPCByNum(@Body PcByNum pcBynum);

}
