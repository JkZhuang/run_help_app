package com.zjk.module.forum.dynamic.view;

import com.zjk.common.mvp.view.IBaseView;
import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;
import com.zjk.result.Result;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public interface IDynamicView extends IBaseView {

    void showProgress(int msgId);

    void hideProgress();

    void commentForumSuccess(boolean bool, CommentForumInfo commentForumInfo);

    void commentForumFail(Result result);

    void likeForumSuccess(boolean bool, LikeForumInfo likeForumInfo);

    void likeForumFail( Result result);

    void getForumSuccess(List<ForumInfo> forumInfos, boolean loadMore);

    void getForumFail(Result result);

    void showCommentWidget(ForumInfo forumInfo, CommentForumInfo commentForumInfo);

    void hideCommentWidget();

    String getCommentText();
}
