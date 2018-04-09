package com.zjk.module.home.sports.planning.view;

import com.zjk.common.ui.BaseView;
import com.zjk.model.SportsSuggestion;
import com.zjk.module.home.sports.planning.present.SportsPlanningPresenter;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public interface ISportsPlanningView extends BaseView<SportsPlanningPresenter> {

    void showProgress(int msgId);

    void hideProgress();

    void getSportsSuggestionFail(boolean onUIThread, Result result);

    void getSportsSuggestionSuccess(boolean onUIThread, List<SportsSuggestion> data);
}
