package com.zjk.module.user.login.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.UserInfo;
import com.zjk.module.user.login.present.ILoginPresenter;
import com.zjk.param.GetConfigParam;
import com.zjk.param.LoginParam;
import com.zjk.result.GetConfigResult;
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
                    listener.onLoginSuccess(result.userInfo);
                } else if (onUIThread) {
                    listener.onLoginFail(result);
                }
            }
        });
    }

    @Override
    public void getConfig(int uId, final OnGetConfigListener listener) {
        GetConfigParam param = new GetConfigParam();
        param.page = "/config/getConfig";
        param.uId = uId;
        LogicImpl.getInstance().getConfig(param, new LogicHandler<GetConfigResult>() {
            @Override
            public void onResult(GetConfigResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.getConfigSuccess(result);
                } else if (onUIThread) {
                    listener.getConfigFail(result);
                }
            }
        });
    }
}
