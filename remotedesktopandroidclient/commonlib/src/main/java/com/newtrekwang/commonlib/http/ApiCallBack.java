package com.newtrekwang.commonlib.http;


import io.reactivex.disposables.Disposable;

/**
 * Created by WJX .
 * Desc: api 回调接口
 * Created on 2017/1/15 16:00.
 * Mail:408030208@qq.com
 */

public interface ApiCallBack<T>  {
    void onStart(Disposable d);
    void onFailed(int code, String msg, Throwable e);
    void onNext(T t);
    void onCompleted();
}
