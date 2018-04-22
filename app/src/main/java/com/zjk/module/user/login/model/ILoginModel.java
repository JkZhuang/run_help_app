package com.zjk.module.user.login.model;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.model.UserInfo;
import com.zjk.result.GetConfigResult;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/08
 */

public interface ILoginModel extends IModel {

    void login(UserInfo userInfo, OnLoginListener listener);

    interface OnLoginListener {

        void onLoginSuccess(boolean onUIThread, UserInfo userInfo);

        void onLoginFail(boolean onUIThread, Result result);
    }

    void getConfig(int uId, OnGetConfigListener listener);

    interface OnGetConfigListener {
        void getConfigSuccess(GetConfigResult result);

        void getConfigFail(GetConfigResult result);
    }
}
