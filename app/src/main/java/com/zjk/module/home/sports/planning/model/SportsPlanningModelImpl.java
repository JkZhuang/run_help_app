package com.zjk.module.home.sports.planning.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.module.home.sports.planning.present.ISportsPlanningPresenter;
import com.zjk.param.GetSportsSuggestionParam;
import com.zjk.result.GetSportsSuggestionResult;

import java.util.ArrayList;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class SportsPlanningModelImpl extends BaseModel<ISportsPlanningPresenter> implements ISportsPlanningModel {

    public SportsPlanningModelImpl(ISportsPlanningPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void getSportsSuggestion(int uId, final GetSportsSuggestionListener listener) {
        GetSportsSuggestionParam param = new GetSportsSuggestionParam();
        param.page = "/sports/getSportsSuggestion";
        param.uId = uId;
        LogicImpl.getInstance().getSportsSuggestion(param, new LogicHandler<GetSportsSuggestionResult>() {
            @Override
            public void onResult(GetSportsSuggestionResult result, boolean onUIThread) {
                if (onUIThread && result.isOk()) {
                    if (result.sportsSuggestions == null) {
                        result.sportsSuggestions = new ArrayList<>();
                    }
                    listener.getSportsSuggestionSuccess(result.sportsSuggestions);
                } else if (onUIThread) {
                    listener.getSportsSuggestionFail(result);
                }
            }
        });
    }
}
