package com.test.githubapi;

import java.util.List;
import java.util.Map;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by zhanghongqiang
 * Date:2016/1/5 Time:14:15
 * ToDo：所有接口的api
 * http://blog.csdn.net/biezhihua/article/details/49232289
 */
public interface GitHubApi {

    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributors(
            @Path("owner") String owner, @Path("repo") String repo);

    @GET("users/list") Observable<Response> some(@Query("sort") String query);

    @GET("users/list")
    public Observable<Response> someMap(@QueryMap Map<String, String> dynamic);

    @POST("/api/geo/getLoc")
    public void getFriendsLocation(@QueryMap Map<String, Object> map);
}
