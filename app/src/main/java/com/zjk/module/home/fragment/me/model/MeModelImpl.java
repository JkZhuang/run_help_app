package com.zjk.module.home.fragment.me.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.module.home.fragment.me.present.IMePresenter;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class MeModelImpl extends BaseModel<IMePresenter> implements IMeModel {

    public MeModelImpl(IMePresenter presenter) {
        this.mPresenter = presenter;
    }
}
