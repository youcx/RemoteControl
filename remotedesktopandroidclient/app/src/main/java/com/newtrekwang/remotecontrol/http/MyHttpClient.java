package com.newtrekwang.remotecontrol.http;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WJX .
 * Desc: 单例，http请求过程封装
 * Created on 2017/1/15 15:35.
 * Mail:408030208@qq.com
 */
public class MyHttpClient {
    //    服务端接口基地址
    public static final String BASE_URL = "http://119.29.201.35/RemoteControl/";
    //    默认链接超时
    private static final int DEFAULT_TIMEOUT = 5;
    //
    private Retrofit retrofit;
    //    接口库
    private ApiStores apiStores;

    /**
     * 构造方法私有
     */
    private MyHttpClient() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new LogInterceptor());
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        apiStores = retrofit.create(ApiStores.class);
    }

    /**
     * 内部静态类实现单例
     */
    private static class SingletonHolder {
        private static final MyHttpClient INSTANCE = new MyHttpClient();
    }

    /**
     * 获取MyHttpClient单例
     */
    public static MyHttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public static ApiStores getApi() {
        return SingletonHolder.INSTANCE.apiStores;
    }



    /**
     * 拦截器，打印请求和响应信息
     */
    private class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request=request.newBuilder().addHeader("content-type","application/json").build();
            Logger.d(request.toString() + request.headers().toString());
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            if (!TextUtils.isEmpty(content)) {
                Logger.json(content);
            }
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }


}
