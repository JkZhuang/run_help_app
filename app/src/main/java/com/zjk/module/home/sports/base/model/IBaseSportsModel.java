package com.zjk.module.home.sports.base.model;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/05/07
 */

public interface IBaseSportsModel extends IModel {

    void delSportsSuggestion(int sSId, DelSportsSuggestionListener listener);

    interface DelSportsSuggestionListener {
        void delSportsSuggestionSuccess(boolean bool);

        void delSportsSuggestionFail(Result result);
    }
}
