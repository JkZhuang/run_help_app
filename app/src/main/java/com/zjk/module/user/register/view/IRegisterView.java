package com.zjk.module.user.register.view;

import com.zjk.common.mvp.view.BaseView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public interface IRegisterView extends BaseView {

    void showProgress();

    void hideProgress();

    void registerFail(boolean onUIThread, Result result);

    void registerSuccess(boolean onUIThread);
}
