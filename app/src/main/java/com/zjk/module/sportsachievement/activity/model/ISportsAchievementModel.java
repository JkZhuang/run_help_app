package com.zjk.module.sportsachievement.activity.model;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.model.SportsData;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public interface ISportsAchievementModel extends IModel {

    void getUserSportsData(int uId, GetUserSportsDataListener listener);

    public interface GetUserSportsDataListener {
        void getUserSportsDataSuccess(List<SportsData> sportsDatas);

        void getUserSportsDataFail(Result result);
    }
}
