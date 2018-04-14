package com.zjk.module.forum.dynamic.view;

import com.zjk.common.mvp.view.BaseView;
import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public interface IDynamicView extends BaseView {

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

    void showCommentWidget(ForumInfo forumInfo, CommentForumInfo commentForumInfo);

    void hideCommentWidget();

    String getCommentText();
}
