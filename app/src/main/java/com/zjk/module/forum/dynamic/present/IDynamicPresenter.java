package com.zjk.module.forum.dynamic.present;

import com.zjk.common.mvp.presenter.IBasePresenter;
import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public interface IDynamicPresenter extends IBasePresenter {

    void commentForum(CommentForumInfo commentForumInfo);

    void likeForum(LikeForumInfo likeForumInfo);

    void getForum(int uId, int lastFId, boolean loadMore);

    void showCommentWidget(ForumInfo forumInfo, CommentForumInfo commentForumInfo);

    void hideCommentWidget();

    String getCommentText();
}
