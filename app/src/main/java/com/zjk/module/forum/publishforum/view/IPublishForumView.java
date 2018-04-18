package com.zjk.module.forum.publishforum.view;

import com.zjk.common.mvp.view.IBaseView;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/11
 */

public interface IPublishForumView extends IBaseView {

    void showProgress(int msgId);

    void hideProgress();

    void publishForumSuccess(boolean isOnUIThread, boolean bool);

    void publishForumFail(boolean isOnUIThread, Result result);
}
