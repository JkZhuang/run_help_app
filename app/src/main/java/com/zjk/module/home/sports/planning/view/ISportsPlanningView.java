package com.zjk.module.home.sports.planning.view;

import com.zjk.common.mvp.view.IBaseView;
import com.zjk.model.SportsSuggestion;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public interface ISportsPlanningView extends IBaseView {

    void getSportsSuggestionFail(Result result);

    void getSportsSuggestionSuccess(List<SportsSuggestion> data);
}
