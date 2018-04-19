package com.zjk.module.sports.view;

import com.zjk.common.mvp.view.IBaseView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public interface ISportsView extends IBaseView {

    void showProgress(int msgId);

    void hideProgress();

    void uploadSportsDataSuccess(boolean bool);

    void uploadSportsDataFail(Result result);
}
