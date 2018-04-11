package com.zjk.module.home.fragment.dynamic.model;

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

public interface IDynamicModel {

    void publishForum(ForumInfo forumInfo, PublishForumListener listener);

    interface PublishForumListener {

        void publishForumSuccess(boolean isOnUIThread, boolean bool);

        void publishForumFail(boolean isOnUIThread, Result result);
    }

    void commentForum(CommentForumInfo commentForumInfo, CommentForumListener listener);

    interface CommentForumListener {

        void commentForumSuccess(boolean isOnUIThread, boolean bool);

        void commentForumFail(boolean isOnUIThread, Result result);
    }

    void likeForum(LikeForumInfo likeForumInfo, LikeForumListener listener);

    interface LikeForumListener {

        void likeForumSuccess(boolean isOnUIThread, boolean bool);

        void likeForumFail(boolean isOnUIThread, Result result);
    }

    void getForum(int uId, int lastFId, GetForumListener listener);

    interface GetForumListener {

        void getForumSuccess(boolean isOnUIThread, List<ForumInfo> forumInfos);

        void getForumFail(boolean isOnUIThread, Result result);
    }
}
