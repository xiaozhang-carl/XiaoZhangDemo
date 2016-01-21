package com.test.jiashiapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.test.StringConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-15 Time: 14:33
 * ToDo:retrofit的创建
 */
public class JiashiRetrofit {

    final static String url = "http://www.jiashi51.com/api.php/";

    JisshiApi service;

    // @formatter:off
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    // @formatter:on


    public JisshiApi getService() {
        return service;
    }


    JiashiRetrofit() {

        //  配置okHttp
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(12, TimeUnit.SECONDS);
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain)
                    throws IOException {
                Response response = chain.proceed(chain.request());

                // Do anything with response here

                return response;
            }
        });

        //2.0的写法
        Retrofit retrofit = new Retrofit.Builder()
                        //   根域名
                .baseUrl(url)
                        //   这样可以返回String类型的数据
//                .addConverterFactory(StringConverterFactory.create())
                        //   Gson解析
                .addConverterFactory(GsonConverterFactory.create(gson))
                        //   解析的适配器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        //   OKHTTP
                .client(client)
                        //
                .build();

        service = retrofit.create(JisshiApi.class);
    }


}
