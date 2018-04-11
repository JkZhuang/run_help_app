package com.zjk.module.forum.publishforum.present;

import com.zjk.model.ForumInfo;
import com.zjk.module.forum.publishforum.model.IPublishForumModel;
import com.zjk.module.forum.publishforum.view.IPublishForumView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/11
 */

public class PublishForumPresenter implements IPublishForumPresenter, IPublishForumModel.PublishForumListener {

    private IPublishForumModel mModel;
    private IPublishForumView mView;

    public PublishForumPresenter(IPublishForumView view, IPublishForumModel model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

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
    public void publishForum(ForumInfo forumInfo) {
        mView.showProgress(R.string.publish_forum_ing);
        mModel.publish(forumInfo, this);
    }
}
