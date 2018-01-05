package com.newtrekwang.remotecontrol.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.newtrekwang.commonactivity.BaseLoginActivity;
import com.newtrekwang.commonactivity.SharePreferenceUtil;
import com.newtrekwang.commonlib.http.ApiCallBack;
import com.newtrekwang.commonlib.http.SubscriberCallBack;
import com.newtrekwang.remotecontrol.bean.Result;
import com.newtrekwang.remotecontrol.bean.Login_json_;
import com.newtrekwang.remotecontrol.connect.ConnectActivity;
import com.newtrekwang.remotecontrol.devices.DevicesFragment;
import com.newtrekwang.remotecontrol.main.MainActivity;
import com.newtrekwang.remotecontrol.model.ModelManager;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.newtrekwang.remotecontrol.util.Constants.AUTOLOGIN;
import static com.newtrekwang.remotecontrol.util.Constants.PHONE;
import static com.newtrekwang.remotecontrol.util.Constants.SESSIONID;
import static com.newtrekwang.remotecontrol.util.Constants.USERNAME;

/**
 * auther    : WJX
 * classname : LoginActivity
 * desc      : 登录界面
 * createTime: 2017/7/23 11:07
 */
public class LoginActivity extends BaseLoginActivity {
    private static final String TAG = "LoginActivity>>>>";
//    订阅管理
   private CompositeDisposable compositeDisposable;
//    数据层
  private ModelManager modelManager;



    /**
     * 初始化
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable=new CompositeDisposable();
        modelManager=new ModelManager();
        SharedPreferences mSP=getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String phonenum=mSP.getString("phonenum","");
        String passwd=mSP.getString("password","");
        mPhoneView.setText(phonenum);
        mPasswordView.setText(passwd);
    }

    /**
     * 开始登录任务
     * @param phone 手机号 已做过过滤处理
     * @param pwd 密码 已做过过滤处理
     */
    @Override
    protected void startLoginTask(final String phone, String pwd) {
        modelManager.login(phone,pwd)
                .subscribe(new SubscriberCallBack<Result<Login_json_>>(new ApiCallBack<Result<Login_json_>>() {
                    @Override
                    public void onStart(Disposable d) {
//                        开始订阅
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onFailed(int code, String msg, Throwable e) {
//                        出异常
                        showProgress(false);
                        showToast(msg+"  "+code);
                        Log.e(TAG, "onFailed: "+e.getMessage() );
                    }

                    @Override
                    public void onNext(Result<Login_json_> stringResult) {
                        showProgress(false);
                        if (stringResult!=null){
                            if (stringResult.getStatus()==1){
                                Log.e("saddf",stringResult.getResult().getSessionid());
                                SharePreferenceUtil.setParam(LoginActivity.this,SESSIONID,stringResult.getResult().getSessionid());
                                SharePreferenceUtil.setParam(LoginActivity.this,AUTOLOGIN,true);
                                SharePreferenceUtil.setParam(LoginActivity.this,PHONE,phone);
                                SharePreferenceUtil.setParam(LoginActivity.this,USERNAME,stringResult.getResult().getUsername());
                                Intent intent=new Intent(LoginActivity.this, DevicesFragment.class);
                                intent.putExtra("sessionid",stringResult.getResult().getSessionid());
                                //
                                SharedPreferences msp=getSharedPreferences("userdata",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=msp.edit();
                                editor.putString("phonenum",mPhoneView.getText().toString());
                                editor.putString("password",mPasswordView.getText().toString());
                                editor.apply();
                                goMain();
                            }else if(stringResult.getStatus()==2)
                            {
                                mPhoneView.setError(stringResult.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
//                        订阅结束
                    }
                }));
    }

    /**
     * 点击注册按钮触发的方法，这里是显示注册对话框
     */
    @Override
    protected void clickRegist() {
       new RegistDialog().show(getSupportFragmentManager(),"registerDialog");
    }

    /**
     * 销毁前的操作，取消订阅
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable!=null&&!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
        modelManager=null;
    }

    /**
     * 跳转到主页
     */
    private void goMain(){
        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
