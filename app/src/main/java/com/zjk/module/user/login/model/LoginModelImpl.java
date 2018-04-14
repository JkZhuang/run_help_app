package com.zjk.module.user.login.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.UserInfo;
import com.zjk.module.user.login.present.ILoginPresenter;
import com.zjk.param.LoginParam;
import com.zjk.result.LoginResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/08
 */

public class LoginModelImpl extends BaseModel<ILoginPresenter> implements ILoginModel {

    public LoginModelImpl(ILoginPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void login(UserInfo userInfo, final OnLoginListener listener) {
        LoginParam param = new LoginParam();
        param.userInfo = userInfo;
        param.page = "/user/login";
        LogicImpl.getInstance().login(param, new LogicHandler<LoginResult>() {
            @Override
            public void onResult(LoginResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.onLoginSuccess(true, result.userInfo);
                } else if (onUIThread) {
                    listener.onLoginFail(true, result);
                }
            }
        });
    }
}
