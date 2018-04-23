package com.zjk.module.ranking.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.RankingVersion;
import com.zjk.module.ranking.model.IRankingModel;
import com.zjk.module.ranking.model.RankingModelImpl;
import com.zjk.module.ranking.view.IRankingView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public class RankingPresenter extends BasePresenterImpl<IRankingView, IRankingModel>
        implements IRankingPresenter, IRankingModel.GetRankingListener {

    public RankingPresenter(@Nullable IRankingView view) {
        super(view);
        mModel = new RankingModelImpl(this);
    }

    @Override
    public void getRankingVersion(int uId) {
        if (mView != null) {
            mView.showProgress(R.string.get_ranking_ing);
        }
        if (mModel != null) {
            mModel.getRankingVersion(uId, this);
        }
    }

    @Override
    public void getRankingSuccess(List<RankingVersion> rankingVersions) {
        if (mView != null) {
            mView.hideProgress();
            mView.getRankingVersionSuccess(rankingVersions);
        }
    }

    @Override
    public void getRankingFail(Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.getRankingVersionFail(result);
        }
    }
}
