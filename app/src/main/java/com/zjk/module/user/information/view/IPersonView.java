package com.zjk.module.user.information.view;

import com.zjk.common.mvp.view.BaseView;
import com.zjk.model.UserInfo;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public interface IPersonView extends BaseView {

    void showProgress(int msgId);

    void hideProgress();

    void changeInfoFail(boolean onUIThread, Result result);

    void changeInfoSuccess(boolean onUIThread, boolean bool);

    void getUpdateInfoFail(boolean onUIThread, Result result);

    void getUpdateInfoSuccess(boolean onUIThread, UserInfo userInfo);
}
