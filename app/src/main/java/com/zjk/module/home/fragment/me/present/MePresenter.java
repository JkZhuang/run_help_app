package com.zjk.module.home.fragment.me.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.module.home.fragment.me.model.IMeModel;
import com.zjk.module.home.fragment.me.model.MeModelImpl;
import com.zjk.module.home.fragment.me.view.IMeView;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class MePresenter extends BasePresenterImpl<IMeView, IMeModel> implements IMePresenter {

    public MePresenter(@Nullable IMeView view) {
        super(view);
        mModel = new MeModelImpl(this);
    }
}
