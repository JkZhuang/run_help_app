package com.zjk.module.user.login.present;

import com.zjk.model.UserInfo;
import com.zjk.module.user.login.model.ILoginModel;
import com.zjk.module.user.login.view.ILoginView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/08
 */

public class LoginPresenter implements ILoginPresenter, ILoginModel.OnLoginListener {

    private ILoginModel mModel;
    private ILoginView mView;

    public LoginPresenter(ILoginModel iLoginModel, ILoginView iLoginView) {
        this.mModel = iLoginModel;
        this.mView = iLoginView;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doLogin(UserInfo userInfo) {
        mView.showProgress();
        mModel.login(userInfo, this);
    }

    @Override
    public void onLoginSuccess(boolean onUIThread, UserInfo userInfo) {
        mView.hideProgress();
        mView.loginSuccess(onUIThread, userInfo);
    }

    @Override
    public void onLoginFail(boolean onUIThread, Result result) {
        mView.hideProgress();
        mView.loginFail(onUIThread, result);
    }
}
