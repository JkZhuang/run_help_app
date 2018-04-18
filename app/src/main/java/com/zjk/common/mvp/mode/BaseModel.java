package com.zjk.common.mvp.mode;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.IBasePresenter;

public abstract class BaseModel<T extends IBasePresenter> implements IModel {

    @Nullable
    protected T mPresenter;

    @Override
    public void destroy() {
        if (mPresenter != null) {
            mPresenter = null;
        }
    }
}
