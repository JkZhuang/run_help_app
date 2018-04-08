package com.zjk.module.user.register.present;

import com.zjk.common.presenter.BasePresenter;
import com.zjk.model.UserInfo;

/**
 * Created by pandengzhe on 2018/4/7.
 */

public interface IRegisterPresenter extends BasePresenter {

    void doRegister(UserInfo userInfo);
}
