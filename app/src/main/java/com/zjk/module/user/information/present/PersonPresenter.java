package com.zjk.module.user.information.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.UserInfo;
import com.zjk.module.user.information.model.IPersonModel;
import com.zjk.module.user.information.model.PersonModelImpl;
import com.zjk.module.user.information.view.IPersonView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class PersonPresenter extends BasePresenterImpl<IPersonView, IPersonModel> implements IPersonPresenter, IPersonModel.OnChangeInfoListener,
        IPersonModel.OnGetUpdateInfoListener {

    public PersonPresenter(@Nullable IPersonView view) {
        super(view);
        mModel = new PersonModelImpl(this);
    }

    @Override
    public void doChangeInfo(UserInfo userInfo, String path) {
        if (mView != null) {
            mView.showProgress(R.string.change_info_ing);
        }
        if (mModel != null) {
            mModel.changeInfo(userInfo, path, this);
        }
    }

    @Override
    public void getUpdateInfo(UserInfo userInfo) {
        if (mView != null) {
            mView.showProgress(R.string.get_update_info_ing);
        }
        if (mModel != null) {
            mModel.getUpdateInfo(userInfo, this);
        }
    }

    @Override
    public void changeSuccess(boolean bool) {
        if (mView != null) {
            mView.hideProgress();
            mView.changeInfoSuccess(bool);
        }
    }

    @Override
    public void changeFail(Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.changeInfoFail(result);
        }
    }

    @Override
    public void getUpdateInfoSuccess(UserInfo userInfo) {
        if (mView != null) {
            mView.hideProgress();
            mView.getUpdateInfoSuccess(userInfo);
        }
    }

    @Override
    public void getUpdateInfoFail(Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.getUpdateInfoFail(result);
        }
    }
}
