package com.zjk.module.forum.publishforum.present;

import android.support.annotation.Nullable;

import com.zjk.common.mvp.presenter.BasePresenterImpl;
import com.zjk.model.ForumInfo;
import com.zjk.module.forum.publishforum.model.IPublishForumModel;
import com.zjk.module.forum.publishforum.model.PublishForumModelImpl;
import com.zjk.module.forum.publishforum.view.IPublishForumView;
import com.zjk.result.Result;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/11
 */

public class PublishForumPresenter extends BasePresenterImpl<IPublishForumView, IPublishForumModel>
        implements IPublishForumPresenter, IPublishForumModel.PublishForumListener {

    public PublishForumPresenter(@Nullable IPublishForumView view) {
        super(view);
        mModel = new PublishForumModelImpl(this);
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
    public void publishForum(ForumInfo forumInfo) {
        if (mView != null) {
            mView.showProgress(R.string.publish_forum_ing);
        }
        if (mModel != null) {
            mModel.publish(forumInfo, this);
        }
    }
}
