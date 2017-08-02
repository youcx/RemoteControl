package com.newtrekwang.remotecontrol.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.newtrekwang.commonactivity.BaseRegistDialog;
import com.newtrekwang.commonlib.http.ApiCallBack;
import com.newtrekwang.commonlib.http.SubscriberCallBack;
import com.newtrekwang.remotecontrol.bean.Result;
import com.newtrekwang.remotecontrol.model.ModelManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
/**
 * auther    : WJX
 * classname : RegistDialog
 * desc      : 注册登录框
 * createTime: 2017/7/30 10:22
 */
public class RegistDialog extends BaseRegistDialog {
    private static final String TAG = "RegistDialog>>>>>>";
    //    订阅管理
    private CompositeDisposable compositeDisposable;
    //    数据层
    private ModelManager modelManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable=new CompositeDisposable();
        modelManager=new ModelManager();
    }

    /**
     * 开始注册任务
     * @param phone 手机号
     * @param password 密码
     */
    @Override
    protected void startRegistTask(String phone, String password,String username) {
        modelManager.regist(phone,password,username)
                    .subscribe(new SubscriberCallBack<Result>(new ApiCallBack<Result>() {
                        @Override
                        public void onStart(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onFailed(int code, String msg, Throwable e) {
                            showProgress(false);
                            showToast(msg+"  "+code);
                            Log.e(TAG, "onFailed: "+e.getMessage() );
                        }

                        @Override
                        public void onNext(Result result) {
                            showProgress(false);
                            if (result!=null){
                                if (result.getStatus()==1){
                                    showToast(result.getMsg());
                                    dismiss();
                                }else {
                                    showToast(result.getMsg());
                                }
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }
                    }));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable!=null&&!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
        modelManager=null;
    }
}
