package com.zjk.module.home.sports.base.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.module.home.sports.base.model.BaseSportsModelImpl;
import com.zjk.module.home.sports.base.model.IBaseSportsModel;
import com.zjk.module.home.sports.base.view.IBaseSportsView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/05/07
 */

public class BaseSportsPresenter extends BasePresenterImpl<IBaseSportsView, IBaseSportsModel>
        implements IBaseSportsPresenter, IBaseSportsModel.DelSportsSuggestionListener {

    public BaseSportsPresenter(@Nullable IBaseSportsView view) {
        super(view);
        mModel = new BaseSportsModelImpl(this);
    }

    @Override
    public void delSportsSuggestion(int sSId) {
        if (mModel != null) {
            mModel.delSportsSuggestion(sSId, this);
        }
    }

    @Override
    public void delSportsSuggestionSuccess(boolean bool) {

    }

    @Override
    public void delSportsSuggestionFail(Result result) {

    }
}
