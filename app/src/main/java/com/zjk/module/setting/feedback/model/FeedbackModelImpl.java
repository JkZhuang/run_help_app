package com.zjk.module.setting.feedback.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.Feedback;
import com.zjk.module.setting.feedback.present.IFeedbackPresenter;
import com.zjk.param.SetFeedbackParam;
import com.zjk.result.SetFeedbackResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public class FeedbackModelImpl extends BaseModel<IFeedbackPresenter> implements IFeedbackModel {

    public FeedbackModelImpl(IFeedbackPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setFeedback(Feedback feedback, final SetFeedbackListener listener) {
        SetFeedbackParam param = new SetFeedbackParam();
        param.page = "/feedback/setFeedback";
        param.feedback = feedback;
        LogicImpl.getInstance().setFeedback(param, new LogicHandler<SetFeedbackResult>() {
            @Override
            public void onResult(SetFeedbackResult result, boolean onUIThread) {
                if (onUIThread && result.isOk()) {
                    listener.setFeedbackSuccess(result.bool);
                } else if (onUIThread) {
                    listener.setFeedbackFail(result);
                }
            }
        });
    }
}
