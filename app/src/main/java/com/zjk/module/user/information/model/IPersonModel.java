package com.zjk.module.user.information.model;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.model.UserInfo;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public interface IPersonModel extends IModel {

    void changeInfo(UserInfo userInfo, String path, OnChangeInfoListener listener);

    interface OnChangeInfoListener {
        void changeSuccess(boolean bool);

        void changeFail(Result result);
    }

    void getUpdateInfo(UserInfo userInfo, OnGetUpdateInfoListener listener);

    interface OnGetUpdateInfoListener {
        void getUpdateInfoSuccess(UserInfo userInfo);

        void getUpdateInfoFail(Result result);
    }
}
