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
        IDynamicModel.PublishForumListener, IDynamicModel.CommentForumListener, IDynamicModel.LikeForumListener {

    public DynamicPresenter(@Nullable IDynamicView view) {
        super(view);
        mModel = new DynamicModelImpl(this);
    }

    @Override
    public void publishForum(ForumInfo forumInfo) {
        if (mView != null) {
            mView.showProgress(R.string.publish_forum_ing);
        }
        if (mModel != null) {
            mModel.publishForum(forumInfo, this);
        }
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
    public void getForum(int uId, int lastFId) {
        if (mModel != null) {
            mModel.getForum(uId, lastFId, this);
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
    public void getForumSuccess(boolean isOnUIThread, List<ForumInfo> forumInfos) {
        if (mView != null) {
            mView.getForumSuccess(isOnUIThread, forumInfos);
        }
    }

    @Override
    public void getForumFail(boolean isOnUIThread, Result result) {
        if (mView != null) {
            mView.getForumFail(isOnUIThread, result);
        }
    }

    @Override
    public void publishForumSuccess(boolean isOnUIThread, boolean bool) {
        if (mView != null) {
            mView.hideProgress();
            mView.publishForumSuccess(isOnUIThread, bool);
        }
    }

    @Override
    public void publishForumFail(boolean isOnUIThread, Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.publishForumFail(isOnUIThread, result);
        }
    }

    @Override
    public void commentForumSuccess(boolean isOnUIThread, boolean bool) {
        if (mView != null) {
            mView.hideProgress();
            mView.commentForumSuccess(isOnUIThread, bool);
        }
    }

    @Override
    public void commentForumFail(boolean isOnUIThread, Result result) {
        if (mView != null) {
            mView.hideProgress();
            mView.commentForumFail(isOnUIThread, result);
        }
    }

    @Override
    public void likeForumSuccess(boolean isOnUIThread, boolean bool) {
        if (mView != null) {
            mView.likeForumSuccess(isOnUIThread, bool);
        }
    }

    @Override
    public void likeForumFail(boolean isOnUIThread, Result result) {
        if (mView != null) {
            mView.likeForumFail(isOnUIThread, result);
        }
    }
}
