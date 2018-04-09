package com.zjk.module.home.sports.planning.present;

import com.zjk.model.SportsSuggestion;
import com.zjk.module.home.sports.planning.model.ISportsPlanningModel;
import com.zjk.module.home.sports.planning.view.ISportsPlanningView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class SportsPlanningPresenter implements ISportsPlanningPresenter,
        ISportsPlanningModel.GetSportsSuggestionListener {

    private ISportsPlanningModel mModel;
    private ISportsPlanningView mView;

    public SportsPlanningPresenter(ISportsPlanningView view, ISportsPlanningModel model) {
        this.mView = view;
        this.mModel = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void getSportsSuggestion(int uId) {
        mView.showProgress(R.string.get_sports_planning_suggestion_ing);
        mModel.getSportsSuggestion(uId, this);
    }

    @Override
    public void getSportsSuggestionSuccess(boolean isOnUIThread, List<SportsSuggestion> data) {
        mView.hideProgress();
        mView.getSportsSuggestionSuccess(isOnUIThread, data);
    }

    @Override
    public void getSportsSuggestionFail(boolean isOnUIThread, Result result) {
        mView.hideProgress();
        mView.getSportsSuggestionFail(isOnUIThread, result);
    }
}
