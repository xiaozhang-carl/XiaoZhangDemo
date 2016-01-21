package com.test.jiashiapi;

/**
 * User: Created by zhanghongqiang
 * Date: 2016-01-20 Time: 09:42
 * ToDo:
 */
public class Jiashi {
    private static JisshiApi api;

    protected static final Object mo = new Object();

    //    单例模式

    public static JisshiApi getGitHubApiInstance() {
        synchronized (mo) {
            if (api == null) {
                api = new JiashiRetrofit().getService();
            }
            return api;
        }
    }
}
