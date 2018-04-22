package com.zjk.module.user.login.present;

import android.support.annotation.Nullable;

import com.zjk.common.app.App;
import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.UserConfig;
import com.zjk.model.UserInfo;
import com.zjk.module.user.login.model.ILoginModel;
import com.zjk.module.user.login.model.LoginModelImpl;
import com.zjk.module.user.login.view.ILoginView;
import com.zjk.result.GetConfigResult;
import com.zjk.result.Result;
import com.zjk.util.DebugUtil;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/08
 */

public class LoginPresenter extends BasePresenterImpl<ILoginView, ILoginModel>
        implements ILoginPresenter, ILoginModel.OnLoginListener, ILoginModel.OnGetConfigListener {

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
    public void getConfig(int uId) {
        if (mModel != null) {
            mModel.getConfig(uId, this);
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

    @Override
    public void getConfigSuccess(GetConfigResult result) {
        UserConfig userConfig = App.instance().getUserConfig();
        userConfig.dynamicCount = result.dynamicCount;
        if (result.fallThreshold != null) {
            userConfig.fallThreshold = result.fallThreshold;
        }
        if (result.trainingSuggestData != null) {
            userConfig.trainingSuggestData = result.trainingSuggestData;
        }
    }

    @Override
    public void getConfigFail(GetConfigResult result) {

    }
}
