package com.zjk.module.user.information.view;

import com.zjk.common.ui.BaseView;
import com.zjk.model.UserInfo;
import com.zjk.module.user.information.present.PersonPresenter;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public interface IPersonView extends BaseView<PersonPresenter> {

    void showProgress(int msgId);

    void hideProgress();

    void changeInfoFail(boolean onUIThread, Result result);

    void changeInfoSuccess(boolean onUIThread, boolean bool);

    void getUpdateInfoFail(boolean onUIThread, Result result);

    void getUpdateInfoSuccess(boolean onUIThread, UserInfo userInfo);
}
