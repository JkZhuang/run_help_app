package com.zjk.module.sportsachievement.activity.presenter;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.SportsData;
import com.zjk.module.sportsachievement.activity.model.ISportsAchievementModel;
import com.zjk.module.sportsachievement.activity.model.SportsAchievementModelImpl;
import com.zjk.module.sportsachievement.activity.view.IISportsAchievementView;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public class SportsAchievementPresenter extends BasePresenterImpl<IISportsAchievementView, ISportsAchievementModel>
        implements ISportsAchievementPresenter, ISportsAchievementModel.GetUserSportsDataListener {

    public SportsAchievementPresenter(@Nullable IISportsAchievementView view) {
        super(view);
        mModel = new SportsAchievementModelImpl(this);
    }

    @Override
    public void getUserSportsData(int uId) {
        if (mModel != null) {
            mModel.getUserSportsData(uId, this);
        }
    }

    @Override
    public void getUserSportsDataSuccess(List<SportsData> sportsDatas) {
        if (mView != null) {
            mView.getUserSportsDataSuccess(sportsDatas);
        }
    }

    @Override
    public void getUserSportsDataFail(Result result) {
        if (mView != null) {
            mView.getUserSportsDataFail(result);
        }
    }
}
