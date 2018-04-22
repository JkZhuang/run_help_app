package com.zjk.common.app;

import android.app.Application;
import android.content.Context;

import com.zjk.common.util.AppUtils;
import com.zjk.model.FallThreshold;
import com.zjk.model.TrainingSuggestData;
import com.zjk.model.UserConfig;
import com.zjk.model.UserInfo;

import java.util.ArrayList;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class App extends Application {

    private static final String TAG = "App";

    private static App instance;

    private UserConfig userConfig;

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
        userConfig = new UserConfig();
        init();
    }

    private void init() {
        userConfig.userInfo = new UserInfo();
        userConfig.dynamicCount = 0;
        userConfig.fallThreshold = new FallThreshold();
        userConfig.trainingSuggestDataArrayList = new ArrayList<>();
    }

    public static App instance() {
        return instance;
    }

    public synchronized UserInfo getUserInfo() {
        return userConfig.userInfo;
    }

    public synchronized void setUserInfo(UserInfo userInfo) {
        this.userConfig.userInfo = userInfo;
    }

    public synchronized UserConfig getUserConfig() {
        return userConfig;
    }

    public synchronized void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }
}
