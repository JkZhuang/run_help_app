package com.zjk.module.sportsachievement.activity.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.module.sportsachievement.activity.presenter.ISportsAchievementPresenter;
import com.zjk.param.GetUserSportsDataParam;
import com.zjk.result.GetUserSportsDataResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public class SportsAchievementModelImpl extends BaseModel<ISportsAchievementPresenter> implements ISportsAchievementModel {

    public SportsAchievementModelImpl(ISportsAchievementPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void getUserSportsData(int uId, final GetUserSportsDataListener listener) {
        GetUserSportsDataParam param = new GetUserSportsDataParam();
        param.page = "/sports/getUserSportsData";
        param.uId = uId;
        LogicImpl.getInstance().getUserSportsData(param, new LogicHandler<GetUserSportsDataResult>() {
            @Override
            public void onResult(GetUserSportsDataResult result, boolean onUIThread) {
                if (onUIThread && result.isOk()) {
                    listener.getUserSportsDataSuccess(result.sportsDatas);
                } else if (onUIThread) {
                    listener.getUserSportsDataFail(result);
                }
            }
        });
    }
}
