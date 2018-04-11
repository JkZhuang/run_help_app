package com.zjk.module.forum.dynamic.model;

import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;
import com.zjk.param.CommentForumParam;
import com.zjk.param.GetForumParam;
import com.zjk.param.LikeForumParam;
import com.zjk.param.PublishForumParam;
import com.zjk.result.CommentForumResult;
import com.zjk.result.GetForumResult;
import com.zjk.result.LikeForumResult;
import com.zjk.result.PublishForumResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public class DynamicModelImpl implements IDynamicModel {

    public DynamicModelImpl() {

    }

    @Override
    public void publishForum(ForumInfo forumInfo, final PublishForumListener listener) {
        PublishForumParam param = new PublishForumParam();
        param.page = "/forum/publishForum";
        param.forumInfo = forumInfo;
        LogicImpl.getInstance().publishForum(param, new LogicHandler<PublishForumResult>() {
            @Override
            public void onResult(PublishForumResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.publishForumSuccess(onUIThread, result.bool);
                } else if (onUIThread) {
                    listener.publishForumFail(onUIThread, result);
                }
            }
        });
    }

    @Override
    public void commentForum(CommentForumInfo commentForumInfo, final CommentForumListener listener) {
        CommentForumParam param = new CommentForumParam();
        param.page = "/forum/commentForum";
        param.commentForumInfo = commentForumInfo;
        LogicImpl.getInstance().commentForum(param, new LogicHandler<CommentForumResult>() {
            @Override
            public void onResult(CommentForumResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.commentForumSuccess(onUIThread, result.bool);
                } else if (onUIThread) {
                    listener.commentForumFail(onUIThread, result);
                }
            }
        });
    }

    @Override
    public void likeForum(LikeForumInfo likeForumInfo, final LikeForumListener listener) {
        LikeForumParam param = new LikeForumParam();
        param.page = "/forum/likeForum";
        param.likeForumInfo = likeForumInfo;
        LogicImpl.getInstance().likeForum(param, new LogicHandler<LikeForumResult>() {
            @Override
            public void onResult(LikeForumResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.likeForumSuccess(onUIThread, result.bool);
                } else if (onUIThread) {
                    listener.likeForumFail(onUIThread, result);
                }
            }
        });
    }

    @Override
    public void getForum(int uId, int lastFId, final GetForumListener listener) {
        GetForumParam param = new GetForumParam();
        param.page = "/forum/getForum";
        param.uId = uId;
        param.lastFId = lastFId;
        LogicImpl.getInstance().getForum(param, new LogicHandler<GetForumResult>() {
            @Override
            public void onResult(GetForumResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.getForumSuccess(onUIThread, result.forumInfos);
                } else if (onUIThread) {
                    listener.getForumFail(onUIThread, result);
                }
            }
        });
    }
}
