package com.zjk.module.ranking.model;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.model.RankingVersion;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public interface IRankingModel extends IModel {

    void getRankingVersion(int uId, GetRankingListener listener);

    public interface GetRankingListener {

        void getRankingSuccess(List<RankingVersion> rankingVersions);

        void getRankingFail(Result result);
    }
}
