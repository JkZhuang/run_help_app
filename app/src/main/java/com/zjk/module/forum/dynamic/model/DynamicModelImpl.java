package com.zjk.module.forum.dynamic.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;
import com.zjk.module.forum.dynamic.present.IDynamicPresenter;
import com.zjk.param.CommentForumParam;
import com.zjk.param.GetForumParam;
import com.zjk.param.LikeForumParam;
import com.zjk.param.PublishForumParam;
import com.zjk.result.CommentForumResult;
import com.zjk.result.GetForumResult;
import com.zjk.result.LikeForumResult;
import com.zjk.result.PublishForumResult;

import java.util.ArrayList;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public class DynamicModelImpl extends BaseModel<IDynamicPresenter> implements IDynamicModel {

    public DynamicModelImpl(IDynamicPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void commentForum(final CommentForumInfo commentForumInfo, final CommentForumListener listener) {
        CommentForumParam param = new CommentForumParam();
        param.page = "/forum/commentForum";
        param.commentForumInfo = commentForumInfo;
        LogicImpl.getInstance().commentForum(param, new LogicHandler<CommentForumResult>() {
            @Override
            public void onResult(CommentForumResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.commentForumSuccess(result.bool, commentForumInfo);
                } else if (onUIThread) {
                    listener.commentForumFail(result);
                }
            }
        });
    }

    @Override
    public void likeForum(final LikeForumInfo likeForumInfo, final LikeForumListener listener) {
        LikeForumParam param = new LikeForumParam();
        param.page = "/forum/likeForum";
        param.likeForumInfo = likeForumInfo;
        LogicImpl.getInstance().likeForum(param, new LogicHandler<LikeForumResult>() {
            @Override
            public void onResult(LikeForumResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    listener.likeForumSuccess(result.bool, likeForumInfo);
                } else if (onUIThread) {
                    listener.likeForumFail(result);
                }
            }
        });
    }

    @Override
    public void getForum(int uId, int lastFId, final GetForumListener listener, final boolean loadMore) {
        GetForumParam param = new GetForumParam();
        param.page = "/forum/getForum";
        param.uId = uId;
        param.lastFId = lastFId;
        LogicImpl.getInstance().getForum(param, new LogicHandler<GetForumResult>() {
            @Override
            public void onResult(GetForumResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    if (result.forumInfos == null) {
                        result.forumInfos = new ArrayList<>();
                    }
                    listener.getForumSuccess(result.forumInfos, loadMore);
                } else if (onUIThread) {
                    listener.getForumFail(result);
                }
            }
        });
    }
}
