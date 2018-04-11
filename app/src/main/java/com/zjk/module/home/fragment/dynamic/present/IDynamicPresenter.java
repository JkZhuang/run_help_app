package com.zjk.module.home.fragment.dynamic.present;

import com.zjk.common.presenter.BasePresenter;
import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public interface IDynamicPresenter extends BasePresenter {

    void publishForum(ForumInfo forumInfo);

    void commentForum(CommentForumInfo commentForumInfo);

    void likeForum(LikeForumInfo likeForumInfo);

    void getForum(int uId, int lastFId);
}
