package com.zjk.module.sportsachievement.activity.view;

import com.zjk.common.mvp.view.IBaseView;
import com.zjk.model.SportsData;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public interface IISportsAchievementView extends IBaseView {

    void getUserSportsDataSuccess(List<SportsData> sportsDatas);

    void getUserSportsDataFail(Result result);
}
