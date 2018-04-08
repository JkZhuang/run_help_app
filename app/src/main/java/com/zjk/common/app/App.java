package com.zjk.common.app;

import android.app.Application;
import android.content.Context;

import com.zjk.common.util.AppUtils;
import com.zjk.model.UserInfo;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class App extends Application {

    private static final String TAG = "App";

    private static App instance;

    private UserInfo userInfo;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppUtils.setBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        instance = this;
        userInfo = new UserInfo();
    }

    public static App instance() {
        return instance;
    }

    public synchronized UserInfo getUserInfo() {
        return userInfo;
    }

    public synchronized void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
