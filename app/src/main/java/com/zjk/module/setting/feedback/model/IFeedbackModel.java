package com.zjk.module.setting.feedback.model;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.model.Feedback;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public interface IFeedbackModel extends IModel {

    void setFeedback(Feedback feedback, SetFeedbackListener listener);

    interface SetFeedbackListener {
        void setFeedbackSuccess(boolean bool);

        void setFeedbackFail(Result result);
    }
}
