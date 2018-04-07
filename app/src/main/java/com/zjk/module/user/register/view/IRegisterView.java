package com.zjk.module.user.register.view;

import java.util.Date;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public interface IRegisterView {

    String getPhone();

    String getPassword();

    String getHeadPhotoUrl();

    String getNickName();

    int getHeight();

    int getWeight();

    Date getBirthday();

    int getGender();

    String getUrgentPhone();
}
