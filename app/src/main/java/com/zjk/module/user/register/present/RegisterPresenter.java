package com.zjk.module.user.register.present;

import com.zjk.module.user.register.model.IRegisterModel;
import com.zjk.module.user.register.model.RegisterModel;
import com.zjk.module.user.register.view.IRegisterView;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class RegisterPresenter {

    private IRegisterView mRegisterView;
    private IRegisterModel mRegisterModel;

    public RegisterPresenter(IRegisterView view) {
        mRegisterView = view;
        mRegisterModel = new RegisterModel();
    }

    public void register() {
    }
}
