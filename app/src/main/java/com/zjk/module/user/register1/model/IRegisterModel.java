package com.zjk.module.user.register1.model;

import com.zjk.model.UserInfo;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public interface IRegisterModel {

    void register(UserInfo userInfo, OnRegisterListener listener);

    interface OnRegisterListener {

        void onRegisterSuccess(boolean onUIThread);

        void onRegisterFail(boolean onUIThread, String errMsg);
    }
}
