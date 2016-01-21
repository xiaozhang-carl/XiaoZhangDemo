package com.test.jiashiapi;

import com.squareup.okhttp.RequestBody;

import java.util.Map;

import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-20 Time: 09:40
 * ToDo:
 */
public interface JisshiApi {
    //http://www.jiashi51.com/api.php/User-do_login.html?mobile=15986302511&password=123456
    @GET("User-do_login.html")
    rx.Observable<String> login(
            @Query("mobile") String moblie, @Query("password") String password);

    @GET("User-do_login.html")
    rx.Observable<LoginBean> login2(
            @Query("mobile") String moblie, @Query("password") String password);

    //测试成功
    @GET("Package-service_single.html")
    rx.Observable<Cat> cat();

    //上传头像
    @Multipart
    @POST("User-updateAvatar.html")
    rx.Observable<UpdateImgBean> changeUserIcon(@PartMap Map<String, RequestBody> params);

    //获取用户信息
    @GET("User-profile.html")
    rx.Observable<String> getUserInfo(@Query("session_id") String session_id);
}
