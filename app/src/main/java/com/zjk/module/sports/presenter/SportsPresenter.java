package com.zjk.module.sports.presenter;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.SportsData;
import com.zjk.module.sports.model.ISportsModel;
import com.zjk.module.sports.model.SportsModelImpl;
import com.zjk.module.sports.view.ISportsView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public class SportsPresenter extends BasePresenterImpl<ISportsView, ISportsModel> implements ISportsPresenter, ISportsModel.UploadSportsDataListener {

    public SportsPresenter(@Nullable ISportsView view) {
        super(view);
        mModel = new SportsModelImpl(this);
    }

    @Override
    public void uploadSportsData(SportsData sportsData) {
        if (mView != null) {
            mView.showProgress(R.string.upload_sports_data_ing);
        }
        if (mModel != null) {
            mModel.uploadSportsData(sportsData, this);
        }
    }

    @Override
    public void uploadSportsDataSuccess(boolean bool) {
        if (mView != null) {
            mView.hideProgress();
            mView.uploadSportsDataSuccess(bool);
        }
    }

    @Override
    public void uploadSportsDataFail(Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.uploadSportsDataFail(result);
        }
    }
}
