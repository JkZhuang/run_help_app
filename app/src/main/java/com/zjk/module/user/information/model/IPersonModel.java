package com.zjk.module.user.information.model;

import com.zjk.model.UserInfo;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public interface IPersonModel {

    void changeInfo(UserInfo userInfo, OnChangeInfoListener listener);

    interface OnChangeInfoListener {
        void changeSuccess(boolean onUIThread, boolean bool);

        void changeFail(boolean onUIThread, Result result);
    }

    void getUpdateInfo(UserInfo userInfo, OnGetUpdateInfoListener listener);

    interface OnGetUpdateInfoListener {
        void getUpdateInfoSuccess(boolean onUIThread, UserInfo userInfo);

        void getUpdateInfoFail(boolean onUIThread, Result result);
    }
}
