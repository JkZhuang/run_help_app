package com.zjk.module.sports.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.module.sports.presenter.ISportsPresenter;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public class SportsModelImpl extends BaseModel<ISportsPresenter> implements ISportsModel {

    public SportsModelImpl(ISportsPresenter presenter) {
        this.mPresenter = presenter;
    }

}
