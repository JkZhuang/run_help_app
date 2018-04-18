package com.zjk.module.user.register.present;

import com.zjk.common.mvp.presenter.IBasePresenter;
import com.zjk.model.UserInfo;

/**
 * Created by pandengzhe on 2018/4/7.
 */

public interface IRegisterPresenter extends IBasePresenter {

    void doRegister(UserInfo userInfo);
}
