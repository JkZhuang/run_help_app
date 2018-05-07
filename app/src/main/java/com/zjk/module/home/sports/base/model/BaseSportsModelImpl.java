package com.zjk.module.home.sports.base.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.module.home.sports.base.present.IBaseSportsPresenter;
import com.zjk.param.DelSportsSuggestionParam;
import com.zjk.result.DelSportsSuggestionResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/05/07
 */

public class BaseSportsModelImpl extends BaseModel<IBaseSportsPresenter> implements IBaseSportsModel {

    public BaseSportsModelImpl(IBaseSportsPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void delSportsSuggestion(int sSId, final DelSportsSuggestionListener listener) {
        DelSportsSuggestionParam param = new DelSportsSuggestionParam();
        param.page = "/sports/delSportsSuggestion";
        param.sSId = sSId;
        LogicImpl.getInstance().delSportsSuggestion(param, new LogicHandler<DelSportsSuggestionResult>() {
            @Override
            public void onResult(DelSportsSuggestionResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.delSportsSuggestionSuccess(result.bool);
                } else if (onUIThread) {
                    listener.delSportsSuggestionFail(result);
                }
            }
        });
    }
}
