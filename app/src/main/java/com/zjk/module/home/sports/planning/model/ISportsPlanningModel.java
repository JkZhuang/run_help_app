package com.zjk.module.home.sports.planning.model;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.model.SportsSuggestion;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public interface ISportsPlanningModel extends IModel {

    void getSportsSuggestion(int uId, GetSportsSuggestionListener listener);

    interface GetSportsSuggestionListener {
        void getSportsSuggestionSuccess(boolean isOnUIThread, List<SportsSuggestion> data);

        void getSportsSuggestionFail(boolean isOnUIThread, Result result);
    }
}
