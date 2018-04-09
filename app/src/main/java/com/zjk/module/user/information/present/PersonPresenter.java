package com.zjk.module.user.information.present;

import com.zjk.model.UserInfo;
import com.zjk.module.user.information.model.IPersonModel;
import com.zjk.module.user.information.view.IPersonView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class PersonPresenter implements IPersonPresenter, IPersonModel.OnChangeInfoListener,
        IPersonModel.OnGetUpdateInfoListener {

    private IPersonView mView;
    private IPersonModel mModel;

    public PersonPresenter(IPersonView view, IPersonModel model) {
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
    public void doChangeInfo(UserInfo userInfo) {
        mView.showProgress(R.string.change_info_ing);
        mModel.changeInfo(userInfo, this);
    }

    @Override
    public void getUpdateInfo(UserInfo userInfo) {
        mView.showProgress(R.string.get_update_info_ing);
        mModel.getUpdateInfo(userInfo, this);
    }

    @Override
    public void changeSuccess(boolean onUIThread, boolean bool) {
        mView.hideProgress();
        mView.changeInfoSuccess(onUIThread, bool);
    }

    @Override
    public void changeFail(boolean onUIThread, Result result) {
        mView.hideProgress();
        mView.changeInfoFail(onUIThread, result);
    }

    @Override
    public void getUpdateInfoSuccess(boolean onUIThread, UserInfo userInfo) {
        mView.hideProgress();
        mView.getUpdateInfoSuccess(onUIThread, userInfo);
    }

    @Override
    public void getUpdateInfoFail(boolean onUIThread, Result result) {
        mView.hideProgress();
        mView.getUpdateInfoFail(onUIThread, result);
    }
}
