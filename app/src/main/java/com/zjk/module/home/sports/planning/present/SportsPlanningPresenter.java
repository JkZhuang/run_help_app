package com.zjk.module.home.sports.planning.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.SportsSuggestion;
import com.zjk.module.home.sports.planning.model.ISportsPlanningModel;
import com.zjk.module.home.sports.planning.model.SportsPlanningModelImpl;
import com.zjk.module.home.sports.planning.view.ISportsPlanningView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class SportsPlanningPresenter extends BasePresenterImpl<ISportsPlanningView, ISportsPlanningModel> implements ISportsPlanningPresenter,
        ISportsPlanningModel.GetSportsSuggestionListener {

    public SportsPlanningPresenter(@Nullable ISportsPlanningView view) {
        super(view);
        mModel = new SportsPlanningModelImpl(this);
    }

    @Override
    public void getSportsSuggestion(int uId) {
        if (mModel != null) {
            mModel.getSportsSuggestion(uId, this);
        }
    }

    @Override
    public void getSportsSuggestionSuccess(List<SportsSuggestion> data) {
        if (mView != null) {
            mView.getSportsSuggestionSuccess(data);
        }
    }

    @Override
    public void getSportsSuggestionFail(Result result) {
        if (mView != null) {
            mView.getSportsSuggestionFail(result);
        }
    }
}
