package com.zjk.module.ranking.view;

import com.zjk.common.mvp.view.IBaseView;
import com.zjk.model.RankingVersion;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public interface IRankingView extends IBaseView {

    void showProgress(int msgId);

    void hideProgress();

    void getRankingVersionSuccess(List<RankingVersion> rankingVersions);

    void getRankingVersionFail(Result result);
}
