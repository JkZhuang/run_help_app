package com.zjk.module.home.sports.planning.model;

import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.param.GetSportsSuggestionParam;
import com.zjk.result.GetSportsSuggestionResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class SportsPlanningModelImpl implements ISportsPlanningModel {

    public SportsPlanningModelImpl() {

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
                    listener.getSportsSuggestionSuccess(onUIThread, result.sportsSuggestions);
                } else if (onUIThread) {
                    listener.getSportsSuggestionFail(onUIThread, result);
                }
            }
        });
    }
}