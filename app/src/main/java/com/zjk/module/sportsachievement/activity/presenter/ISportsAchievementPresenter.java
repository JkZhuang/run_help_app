package com.zjk.module.sportsachievement.activity.presenter;

import com.zjk.common.mvp.presenter.IBasePresenter;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public interface ISportsAchievementPresenter extends IBasePresenter {

    void getUserSportsData(int uId);
}
