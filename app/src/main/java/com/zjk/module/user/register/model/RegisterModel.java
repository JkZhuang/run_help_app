package com.zjk.module.user.register.model;

import com.zjk.model.UserInfo;
import com.zjk.module.user.register.bean.RegisterBean;

import java.util.Date;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class RegisterModel implements IRegisterModel {

    private RegisterBean registerBean;
    private UserInfo userInfo;

    public RegisterModel() {
        userInfo = new UserInfo();
        registerBean = new RegisterBean(userInfo);
    }

    @Override
    public void setPhone(String phone) {
        userInfo.setPhone(phone);
    }

    @Override
    public void setPassword(String password) {
        userInfo.setPassword(password);
    }

    @Override
    public void setHeadPhoto(String url) {
        userInfo.setHeadUrl(url);
    }

    @Override
    public void setNickName(String nickName) {
        userInfo.setUserName(nickName);
    }

    @Override
    public void setHeight(int height) {
        userInfo.setHeight(height);
    }

    @Override
    public void setWeight(int weight) {
        userInfo.setWeight(weight);
    }

    @Override
    public void setBirthday(Date date) {
        userInfo.setBirthday(date);
    }

    @Override
    public void setGender(int gender) {
        userInfo.setGender(gender);
    }

    @Override
    public void setUrgentPhone(String urgentPhone) {
        userInfo.setUrgentPhone(urgentPhone);
    }
}
