package com.zjk.module.user.register.model;

import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.UserInfo;
import com.zjk.param.RegisteredParam;
import com.zjk.result.RegisteredResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class RegisterModelImpl implements IRegisterModel {

    public RegisterModelImpl() {

    }

    @Override
    public void register(UserInfo userInfo, final OnRegisterListener listener) {
        RegisteredParam param = new RegisteredParam();
        param.page = "/user/register";
        param.userInfo = userInfo;
        LogicImpl.getInstance().register(param, new LogicHandler<RegisteredResult>() {
            @Override
            public void onResult(RegisteredResult result, boolean onUIThread) {
                if (result.isOk() && result.bool) {
                    listener.onRegisterSuccess(onUIThread);
                } else {
                    listener.onRegisterFail(onUIThread, result);
                }
            }
        });
    }
}
