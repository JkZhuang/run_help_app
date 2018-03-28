package com.zjk.module.user.register.model;

import com.zjk.module.user.register.bean.RegisterBean;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public interface IRegisterModel {

    void setID(int id);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    RegisterBean load(int id);
}
