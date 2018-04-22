package com.zjk.module.sports.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.SportsData;
import com.zjk.module.sports.presenter.ISportsPresenter;
import com.zjk.param.UploadSportsDataParam;
import com.zjk.result.UpdateFallThresholdResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public class SportsModelImpl extends BaseModel<ISportsPresenter> implements ISportsModel {

    public SportsModelImpl(ISportsPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void uploadSportsData(SportsData sportsData, final UploadSportsDataListener listener) {
        UploadSportsDataParam param = new UploadSportsDataParam();
        param.page = "/sports/uploadSportsData";
        param.sportsData = sportsData;
        LogicImpl.getInstance().uploadSportsData(param, new LogicHandler<UpdateFallThresholdResult>() {
            @Override
            public void onResult(UpdateFallThresholdResult result, boolean onUIThread) {
                if (onUIThread && result.isOk()) {
                    listener.uploadSportsDataSuccess(result.bool);
                } else if (onUIThread) {
                    listener.uploadSportsDataFail(result);
                }
            }
        });
    }
}
