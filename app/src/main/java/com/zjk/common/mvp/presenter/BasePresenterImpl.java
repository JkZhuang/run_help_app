package com.zjk.common.mvp.presenter;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.common.mvp.view.IBaseView;

/**
 * Created by lianzhan on 2017/5/4.
 * 所有业务presenter 需要继承此类
 */

public abstract class BasePresenterImpl<T extends IBaseView, M extends IModel> implements IBasePresenter {

    @Nullable
    protected T mView;

    @Nullable
    protected M mModel;

    public BasePresenterImpl(@Nullable T view) {
        this.mView = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if (mModel != null) {
            mModel.destroy();
        }
        if (mView != null) {
            mView = null;
        }
    }
}
