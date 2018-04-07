package com.zjk.module.user.register1.view;

import com.zjk.common.ui.BaseView;
import com.zjk.module.user.register1.present.RegisterPresenter;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public interface IRegisterView extends BaseView<RegisterPresenter> {

    void showProgress();

    void hideProgress();

    void registerFail(boolean onUIThread, String errMsg);

    void registerSuccess(boolean onUIThread);
}
