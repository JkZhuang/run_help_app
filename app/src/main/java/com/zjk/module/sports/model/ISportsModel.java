package com.zjk.module.sports.model;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.model.SportsData;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public interface ISportsModel extends IModel {

    void uploadSportsData(SportsData sportsData, UploadSportsDataListener listener);

    interface UploadSportsDataListener {
        void uploadSportsDataSuccess(boolean bool);

        void uploadSportsDataFail(Result result);
    }
}
