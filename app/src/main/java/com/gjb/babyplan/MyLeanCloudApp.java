package com.gjb.babyplan;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2016/6/2.
 */
public class MyLeanCloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        String AppID="T3S5raAIAaPSXKiGndlo4a4I-gzGzoHsz";
        String AppKey="K83S0Qlt9yaige7AFDxMVxvT";
        AVOSCloud.initialize(this,AppID,AppKey);

    }
}
