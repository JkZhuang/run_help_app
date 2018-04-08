package com.zjk.module.user.register.view;

import com.zjk.common.ui.BaseView;
import com.zjk.module.user.register.present.RegisterPresenter;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public interface IRegisterView extends BaseView<RegisterPresenter> {

    void showProgress();

    void hideProgress();

    void registerFail(boolean onUIThread, Result result);

    void registerSuccess(boolean onUIThread);
}
