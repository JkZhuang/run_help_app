package com.zjk.module.setting.feedback.view;

import com.zjk.common.mvp.view.IBaseView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public interface IFeedbackView extends IBaseView {

    void showProgress(int msgRes);

    void hideProgress();

    void setFeedbackSuccess(boolean bool);

    void setFeedbackFail(Result result);
}
