package com.zjk.module.setting.feedback.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.Feedback;
import com.zjk.module.setting.feedback.model.FeedbackModelImpl;
import com.zjk.module.setting.feedback.model.IFeedbackModel;
import com.zjk.module.setting.feedback.view.IFeedbackView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public class FeedbackPresenter extends BasePresenterImpl<IFeedbackView, IFeedbackModel> implements IFeedbackPresenter, IFeedbackModel.SetFeedbackListener {

    public FeedbackPresenter(@Nullable IFeedbackView view) {
        super(view);
        mModel = new FeedbackModelImpl(this);
    }

    @Override
    public void setFeedback(Feedback feedback) {
        if (mView != null) {
            mView.showProgress(R.string.send_feedback_ing);
        }
        if (mModel != null) {
            mModel.setFeedback(feedback, this);
        }
    }

    @Override
    public void setFeedbackSuccess(boolean bool) {
        if (mView != null) {
            mView.hideProgress();
            mView.setFeedbackSuccess(bool);
        }
    }

    @Override
    public void setFeedbackFail(Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.setFeedbackFail(result);
        }
    }
}
