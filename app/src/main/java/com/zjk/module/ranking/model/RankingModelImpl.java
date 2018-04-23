package com.zjk.module.ranking.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.module.ranking.present.IRankingPresenter;
import com.zjk.param.GetConfigParam;
import com.zjk.param.GetRankingVersionParam;
import com.zjk.result.GetRankingVersionResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public class RankingModelImpl extends BaseModel<IRankingPresenter> implements IRankingModel {

    public RankingModelImpl(IRankingPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void getRankingVersion(int uId, final GetRankingListener listener) {
        GetRankingVersionParam param = new GetRankingVersionParam();
        param.page = "/sports/getRankingVersion";
        param.uId = uId;
        LogicImpl.getInstance().getRankingVersion(param, new LogicHandler<GetRankingVersionResult>() {
            @Override
            public void onResult(GetRankingVersionResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.getRankingSuccess(result.rankingVersions);
                } else if (onUIThread) {
                    listener.getRankingFail(result);
                }
            }
        });
    }
}
