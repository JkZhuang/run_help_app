package com.zjk.module.sports.presenter;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.module.sports.model.ISportsModel;
import com.zjk.module.sports.model.SportsModelImpl;
import com.zjk.module.sports.view.ISportsView;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public class SportsPresenter extends BasePresenterImpl<ISportsView, ISportsModel> implements ISportsPresenter {

    public SportsPresenter(@Nullable ISportsView view) {
        super(view);
        mModel = new SportsModelImpl(this);
    }
}
