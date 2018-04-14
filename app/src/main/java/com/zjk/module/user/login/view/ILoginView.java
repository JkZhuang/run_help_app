package com.zjk.module.user.login.view;

import com.zjk.common.mvp.view.BaseView;
import com.zjk.model.UserInfo;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/08
 */

public interface ILoginView extends BaseView {

    void showProgress();

    void hideProgress();

    void loginFail(boolean onUIThread, Result result);

    void loginSuccess(boolean onUIThread, UserInfo userInfo);
}
