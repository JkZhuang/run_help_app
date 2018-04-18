package com.zjk.module.home.sports.planning.present;

import com.zjk.common.mvp.presenter.IBasePresenter;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public interface ISportsPlanningPresenter extends IBasePresenter {

    void getSportsSuggestion(int uId);
}
