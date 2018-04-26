package com.zjk.module.user.register.view;

import com.zjk.common.mvp.view.IBaseView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public interface IRegisterView extends IBaseView {

    void showProgress();

    void hideProgress();

    void registerFail(Result result);

    void registerSuccess();
}
