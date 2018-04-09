package com.zjk.module.user.information.model;

import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.UserInfo;
import com.zjk.param.ChangeUserInfoParam;
import com.zjk.param.LoginParam;
import com.zjk.result.ChangeUserInfoResult;
import com.zjk.result.LoginResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class PersonModelImpl implements IPersonModel {

    public PersonModelImpl() {

    }

    @Override
    public void changeInfo(UserInfo userInfo, final OnChangeInfoListener listener) {
        ChangeUserInfoParam param = new ChangeUserInfoParam();
        param.page = "/user/changeUserInfo";
        param.userInfo = userInfo;
        LogicImpl.getInstance().changeUserInfo(param, new LogicHandler<ChangeUserInfoResult>() {
            @Override
            public void onResult(ChangeUserInfoResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.changeSuccess(true, result.bool);
                } else if (onUIThread) {
                    listener.changeFail(true, result);
                }
            }
        });
    }

    @Override
    public void getUpdateInfo(UserInfo userInfo, final OnGetUpdateInfoListener listener) {
        LoginParam param = new LoginParam();
        param.userInfo = userInfo;
        param.page = "/user/login";
        LogicImpl.getInstance().login(param, new LogicHandler<LoginResult>() {
            @Override
            public void onResult(LoginResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.getUpdateInfoSuccess(true, result.userInfo);
                } else if (onUIThread) {
                    listener.getUpdateInfoFail(true, result);
                }
            }
        });
    }
}
