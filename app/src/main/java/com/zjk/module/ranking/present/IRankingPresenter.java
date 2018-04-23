package com.zjk.module.ranking.present;

import com.zjk.common.mvp.presenter.IBasePresenter;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public interface IRankingPresenter extends IBasePresenter {

    void getRankingVersion(int uId);
}
