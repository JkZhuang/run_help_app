package com.zjk.module.forum.dynamic.present;

import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;
import com.zjk.module.forum.dynamic.model.IDynamicModel;
import com.zjk.module.forum.dynamic.view.IDynamicView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/10
 */

public class DynamicPresenter implements IDynamicPresenter, IDynamicModel.GetForumListener,
        IDynamicModel.PublishForumListener, IDynamicModel.CommentForumListener, IDynamicModel.LikeForumListener {

    private IDynamicView mView;
    private IDynamicModel mModel;

    public DynamicPresenter(IDynamicView view, IDynamicModel model) {
        this.mView = view;
        this.mModel = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void publishForum(ForumInfo forumInfo) {
        mView.showProgress(R.string.publish_forum_ing);
        mModel.publishForum(forumInfo, this);
    }

    @Override
    public void commentForum(CommentForumInfo commentForumInfo) {
        mView.showProgress(R.string.comment_forum_ing);
        mModel.commentForum(commentForumInfo, this);
    }

    @Override
    public void likeForum(LikeForumInfo likeForumInfo) {
        mModel.likeForum(likeForumInfo, this);
    }

    @Override
    public void getForum(int uId, int lastFId) {
        mView.showProgress(R.string.get_forum_ing);
        mModel.getForum(uId, lastFId, this);
    }

    @Override
    public void showCommentWidget(ForumInfo forumInfo, CommentForumInfo commentForumInfo) {
        mView.showCommentWidget(forumInfo, commentForumInfo);
    }

    @Override
    public void hideCommentWidget() {
        mView.hideCommentWidget();
    }

    @Override
    public String getCommentText() {
        return mView.getCommentText();
    }

    @Override
    public void getForumSuccess(boolean isOnUIThread, List<ForumInfo> forumInfos) {
        mView.hideProgress();
        mView.getForumSuccess(isOnUIThread, forumInfos);
    }

    @Override
    public void getForumFail(boolean isOnUIThread, Result result) {
        mView.hideProgress();
        mView.getForumFail(isOnUIThread, result);
    }

    @Override
    public void publishForumSuccess(boolean isOnUIThread, boolean bool) {
        mView.hideProgress();
        mView.publishForumSuccess(isOnUIThread, bool);
    }

    @Override
    public void publishForumFail(boolean isOnUIThread, Result result) {
        mView.hideProgress();
        mView.publishForumFail(isOnUIThread, result);
    }

    @Override
    public void commentForumSuccess(boolean isOnUIThread, boolean bool) {
        mView.hideProgress();
        mView.commentForumSuccess(isOnUIThread, bool);
    }

    @Override
    public void commentForumFail(boolean isOnUIThread, Result result) {
        mView.hideProgress();
        mView.commentForumFail(isOnUIThread, result);
    }

    @Override
    public void likeForumSuccess(boolean isOnUIThread, boolean bool) {
        mView.likeForumSuccess(isOnUIThread, bool);
    }

    @Override
    public void likeForumFail(boolean isOnUIThread, Result result) {
        mView.likeForumFail(isOnUIThread, result);
    }
}
