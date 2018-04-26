package com.zjk.module.user.register.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.UserInfo;
import com.zjk.module.user.register.model.IRegisterModel;
import com.zjk.module.user.register.model.RegisterModelImpl;
import com.zjk.module.user.register.view.IRegisterView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class RegisterPresenter extends BasePresenterImpl<IRegisterView, IRegisterModel>
        implements IRegisterPresenter, IRegisterModel.OnRegisterListener {

    public RegisterPresenter(@Nullable IRegisterView view) {
        super(view);
        mModel = new RegisterModelImpl(this);
    }

    @Override
    public void doRegister(UserInfo userInfo) {
        if (mView != null) {
            mView.showProgress();
        }
        if (mModel != null) {
            mModel.register(userInfo, this);
        }
    }

    @Override
    public void onRegisterSuccess() {
        if (mView != null) {
            mView.hideProgress();
            mView.registerSuccess();
        }
    }

    @Override
    public void onRegisterFail(Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.registerFail(result);
        }
    }
}
