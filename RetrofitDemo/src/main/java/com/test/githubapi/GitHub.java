package com.test.githubapi;

/**
 * Created by zhanghongqiang
 * Date:2016/1/5 Time:14:15
 * ToDo：得到GitHubApi的工厂，主要是得到
 */
public class GitHub {

    private static GitHubApi gitHubApi;

    protected static final Object monitor = new Object();

//    单例模式

    public static GitHubApi getGitHubApiInstance() {
        synchronized (monitor) {
            if (gitHubApi == null) {
                gitHubApi = new GitHubRetrofit().getGitHubService();
            }
            return gitHubApi;
        }
    }
}
