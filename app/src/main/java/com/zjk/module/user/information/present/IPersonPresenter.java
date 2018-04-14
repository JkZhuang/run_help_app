package com.zjk.module.user.information.present;

import com.zjk.common.mvp.presenter.BasePresenter;
import com.zjk.model.UserInfo;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public interface IPersonPresenter extends BasePresenter {

    void doChangeInfo(UserInfo userInfo, String path);

    void getUpdateInfo(UserInfo userInfo);
}
