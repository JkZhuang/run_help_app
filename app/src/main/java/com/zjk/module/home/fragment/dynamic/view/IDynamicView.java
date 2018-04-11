package com.zjk.module.home.fragment.dynamic.view;

import com.zjk.common.ui.BaseView;
import com.zjk.model.ForumInfo;
import com.zjk.module.home.fragment.dynamic.present.DynamicPresenter;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public interface IDynamicView extends BaseView<DynamicPresenter> {

    void showProgress(int msgId);

    void hideProgress();

    void publishForumSuccess(boolean isOnUIThread, boolean bool);

    void publishForumFail(boolean isOnUIThread, Result result);

    void commentForumSuccess(boolean isOnUIThread, boolean bool);

    void commentForumFail(boolean isOnUIThread, Result result);

    void likeForumSuccess(boolean isOnUIThread, boolean bool);

    void likeForumFail(boolean isOnUIThread, Result result);

    void getForumSuccess(boolean isOnUIThread, List<ForumInfo> forumInfos);

    void getForumFail(boolean isOnUIThread, Result result);
}
