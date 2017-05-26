package com.cheetahlou.careu;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.cheetahlou.careu.chat.CustomUserProvider;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by Administrator on 2016/6/2.
 */
public class MyLeanCloudApp extends Application {

    private final String AppID="SDxIYYVvCktTJAcCnL0ogRWW-gzGzoHsz";
    private final String AppKey="fAl1XBhVePJFKG8LM484MFk7";

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,AppID,AppKey);
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        AVOSCloud.setDebugLogEnabled(true);
        LCChatKit.getInstance().init(getApplicationContext(), AppID, AppKey);
        AVIMClient.setAutoOpen(false);
    }
}
