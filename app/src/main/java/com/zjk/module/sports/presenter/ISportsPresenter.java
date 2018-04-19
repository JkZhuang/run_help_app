package com.zjk.module.sports.presenter;

import com.zjk.common.mvp.presenter.IBasePresenter;
import com.zjk.model.SportsData;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public interface ISportsPresenter extends IBasePresenter {

    void uploadSportsData(SportsData sportsData);
}
