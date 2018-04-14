package com.zjk.module.user.login.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.UserInfo;
import com.zjk.module.user.login.model.ILoginModel;
import com.zjk.module.user.login.model.LoginModelImpl;
import com.zjk.module.user.login.view.ILoginView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/08
 */

public class LoginPresenter extends BasePresenterImpl<ILoginView, ILoginModel> implements ILoginPresenter, ILoginModel.OnLoginListener {

    public LoginPresenter(@Nullable ILoginView view) {
        super(view);
        mModel = new LoginModelImpl(this);
    }

    @Override
    public void doLogin(UserInfo userInfo) {
        if (mView != null) {
            mView.showProgress();
        }
        if (mModel != null) {
            mModel.login(userInfo, this);
        }
    }

    @Override
    public void onLoginSuccess(boolean onUIThread, UserInfo userInfo) {
        if (mView != null) {
            mView.hideProgress();
            mView.loginSuccess(onUIThread, userInfo);
        }
    }

    @Override
    public void onLoginFail(boolean onUIThread, Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.loginFail(onUIThread, result);
        }
    }
}
