package com.zjk.module.forum.dynamic.model;

import com.zjk.common.mvp.mode.IModel;
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

public interface IDynamicModel extends IModel {

    void commentForum(CommentForumInfo commentForumInfo, CommentForumListener listener);

    interface CommentForumListener {

        void commentForumSuccess(CommentForumInfo commentForumInfo);

        void commentForumFail(Result result);
    }

    void likeForum(LikeForumInfo likeForumInfo, LikeForumListener listener);

    interface LikeForumListener {

        void likeForumSuccess(LikeForumInfo likeForumInfo);

        void likeForumFail(Result result);
    }

    void getForum(int uId, int lastFId, GetForumListener listener, int opera);

    interface GetForumListener {

        void getForumSuccess(List<ForumInfo> forumInfos, int opera, boolean isLastPage);

        void getForumFail(Result result, int opera);
    }
}
