package com.zjk.module.user.login.present;

import com.zjk.common.mvp.presenter.BasePresenter;
import com.zjk.model.UserInfo;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/08
 */

public interface ILoginPresenter extends BasePresenter {

    void doLogin(UserInfo userInfo);
}
