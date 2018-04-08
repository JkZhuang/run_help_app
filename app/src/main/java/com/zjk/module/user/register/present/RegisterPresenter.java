package com.zjk.module.user.register.present;

import com.zjk.model.UserInfo;
import com.zjk.module.user.register.model.IRegisterModel;
import com.zjk.module.user.register.view.IRegisterView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class RegisterPresenter implements IRegisterPresenter, IRegisterModel.OnRegisterListener {

    private IRegisterView mView;
    private IRegisterModel mModel;

    public RegisterPresenter(IRegisterView view, IRegisterModel model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doRegister(UserInfo userInfo) {
        mView.showProgress();
        mModel.register(userInfo, this);
    }

    @Override
    public void onRegisterSuccess(boolean onUIThread) {
        mView.hideProgress();
        mView.registerSuccess(onUIThread);
    }

    @Override
    public void onRegisterFail(boolean onUIThread, Result result) {
        mView.hideProgress();
        mView.registerFail(onUIThread, result);
    }
}
