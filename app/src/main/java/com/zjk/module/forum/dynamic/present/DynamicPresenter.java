package com.zjk.module.forum.dynamic.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;
import com.zjk.module.forum.dynamic.model.DynamicModelImpl;
import com.zjk.module.forum.dynamic.model.IDynamicModel;
import com.zjk.module.forum.dynamic.view.IDynamicView;
import com.zjk.okhttp.DefList;
import com.zjk.result.Result;
import com.zjk.run_help.R;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public class DynamicPresenter extends BasePresenterImpl<IDynamicView, IDynamicModel> implements IDynamicPresenter, IDynamicModel.GetForumListener,
        IDynamicModel.CommentForumListener, IDynamicModel.LikeForumListener {

    public DynamicPresenter(@Nullable IDynamicView view) {
        super(view);
        mModel = new DynamicModelImpl(this);
    }

    @Override
    public void commentForum(CommentForumInfo commentForumInfo) {
        if (mView != null) {
            mView.showProgress(R.string.comment_forum_ing);
        }
        if (mModel != null) {
            mModel.commentForum(commentForumInfo, this);
        }
    }

    @Override
    public void likeForum(LikeForumInfo likeForumInfo) {
        if (mModel != null) {
            mModel.likeForum(likeForumInfo, this);
        }
    }

    @Override
    public void getForum(int uId, int lastFId, int opera) {
        if (mModel != null) {
            mModel.getForum(uId, lastFId, this, opera);
        }
    }

    @Override
    public void showCommentWidget(ForumInfo forumInfo, CommentForumInfo commentForumInfo) {
        if (mView != null) {
            mView.showCommentWidget(forumInfo, commentForumInfo);
        }
    }

    @Override
    public void hideCommentWidget() {
        if (mView != null) {
            mView.hideCommentWidget();
        }
    }

    @Override
    public String getCommentText() {
        if (mView != null) {
            return mView.getCommentText();
        }
        return DefList.EMPTY;
    }

    @Override
    public void getForumSuccess(List<ForumInfo> forumInfos, int opera) {
        if (mView != null) {
            mView.getForumSuccess(forumInfos, opera);
        }
    }

    @Override
    public void getForumFail(Result result, int opera) {
        if (mView != null) {
            mView.getForumFail(result, opera);
        }
    }

    @Override
    public void commentForumSuccess(CommentForumInfo commentForumInfo) {
        if (mView != null) {
            mView.hideProgress();
            mView.commentForumSuccess(commentForumInfo);
        }
    }

    @Override
    public void commentForumFail(Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.commentForumFail(result);
        }
    }

    @Override
    public void likeForumSuccess(LikeForumInfo likeForumInfo) {
        if (mView != null) {
            mView.likeForumSuccess(likeForumInfo);
        }
    }

    @Override
    public void likeForumFail(Result result) {
        if (mView != null) {
            mView.likeForumFail(result);
        }
    }
}
