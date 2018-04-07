package com.zjk.module.user.register.model;

import com.zjk.module.user.register.bean.RegisterBean;

import java.util.Date;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public interface IRegisterModel {

    void setPhone(String phone);

    void setPassword(String password);

    void setHeadPhoto(String url);

    void setNickName(String nickName);

    void setHeight(int height);

    void setWeight(int weight);

    void setBirthday(Date date);

    void setGender(int gender);

    void setUrgentPhone(String urgentPhone);
}
