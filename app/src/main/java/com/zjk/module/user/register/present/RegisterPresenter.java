package com.zjk.module.user.register.present;

import com.zjk.module.user.register.bean.RegisterBean;
import com.zjk.module.user.register.model.IRegisterModel;
import com.zjk.module.user.register.view.IRegisterView;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class RegisterPresenter {

    private IRegisterView mUserView;
    private IRegisterModel mUserModel;

    public RegisterPresenter(IRegisterView view) {
        mUserView = view;
//        mUserModel = new IRegisterModel();
    }

    public void saveUser(int id, String firstName, String lastName) {
        mUserModel.setID(id);
        mUserModel.setFirstName(firstName);
        mUserModel.setLastName(lastName);
    }

    public void loadUser(int id) {
        RegisterBean user = mUserModel.load(id);
//        mUserView.setFirstName(user.getFirstName());
//        mUserView.setLastName(user.getLastName());
    }
}
