package com.newtrekwang.remotecontrol.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.newtrekwang.commonactivity.BaseLoginActivity;
import com.newtrekwang.commonactivity.SharePreferenceUtil;
import com.newtrekwang.commonlib.http.ApiCallBack;
import com.newtrekwang.commonlib.http.SubscriberCallBack;
import com.newtrekwang.remotecontrol.bean.Result;
import com.newtrekwang.remotecontrol.main.MainActivity;
import com.newtrekwang.remotecontrol.model.ModelManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.newtrekwang.remotecontrol.util.Constants.AUTOLOGIN;
import static com.newtrekwang.remotecontrol.util.Constants.PHONE;
import static com.newtrekwang.remotecontrol.util.Constants.SESSIONID;

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

        mPhoneView.setText("18683668831");
        mPasswordView.setText("123456");
    }

    /**
     * 开始登录任务
     * @param phone 手机号 已做过过滤处理
     * @param pwd 密码 已做过过滤处理
     */
    @Override
    protected void startLoginTask(final String phone, String pwd) {
        modelManager.login(phone,pwd)
                .subscribe(new SubscriberCallBack<Result<String>>(new ApiCallBack<Result<String>>() {
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
                    public void onNext(Result<String> stringResult) {
                        showProgress(false);
                        if (stringResult!=null){
                            if (stringResult.getStatus()==1){
                                SharePreferenceUtil.setParam(LoginActivity.this,SESSIONID,stringResult.getResult());
                                SharePreferenceUtil.setParam(LoginActivity.this,AUTOLOGIN,true);
                                SharePreferenceUtil.setParam(LoginActivity.this,PHONE,phone);
                                goMain();
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
