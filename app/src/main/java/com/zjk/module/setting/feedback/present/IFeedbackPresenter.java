package com.zjk.module.setting.feedback.present;

import com.zjk.common.mvp.presenter.IBasePresenter;
import com.zjk.model.Feedback;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public interface IFeedbackPresenter extends IBasePresenter {

    void setFeedback(Feedback feedback);
}
