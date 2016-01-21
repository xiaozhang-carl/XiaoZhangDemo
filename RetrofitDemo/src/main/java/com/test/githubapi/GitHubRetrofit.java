package com.test.githubapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import com.squareup.okhttp.Response;
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
public class GitHubRetrofit {

    final static String url = "https://api.github.com/";

    GitHubApi gitHubService;

    // @formatter:off
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    // @formatter:on


    GitHubRetrofit() {

        //  配置okHttp
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(12, TimeUnit.SECONDS);
        client.interceptors().add(new Interceptor() {
            @Override public Response intercept(Chain chain)
                    throws IOException {
                Response response = chain.proceed(chain.request());

                // Do anything with response here

                return response;
            }
        });

        //2.0的写法
        Retrofit retrofit = new Retrofit.Builder()
                //                根域名
                .baseUrl(url)
                        //   Gson解析
                .addConverterFactory(GsonConverterFactory.create(gson))
                        //
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        //
                .client(client)
                        //
                .build();

        gitHubService = retrofit.create(GitHubApi.class);
    }


    public GitHubApi getGitHubService() {
        return gitHubService;
    }
}
