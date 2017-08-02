package com.newtrekwang.commonlib.http;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by WJX .
 * Desc:  封装观察者，主要是实现网络异常情况判断
 * Created on 2017/1/15 15:58.
 * Mail:408030208@qq.com
 */

public class SubscriberCallBack<T> implements Observer<T> {
private ApiCallBack<T> apiCallBack;
    public SubscriberCallBack(ApiCallBack<T> apiCallBack){
        this.apiCallBack=apiCallBack;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if ( e instanceof HttpException){
            HttpException httpException= (HttpException) e;
            int code=httpException.code();
            String msg=httpException.getMessage();
            if (code==504){
                msg="网络不给力";
            }else if(code==404){
                msg="请求内容不存在！";
            }
            apiCallBack.onFailed(code,msg,e);
        }else {
            apiCallBack.onFailed(0,e.getMessage(),e);
        }
    }

    @Override
    public void onComplete() {
        apiCallBack.onCompleted();
    }


    @Override
    public void onSubscribe(Disposable d) {
        apiCallBack.onStart(d);
    }

    @Override
    public void onNext(T t) {
        apiCallBack.onNext(t);
    }

}
