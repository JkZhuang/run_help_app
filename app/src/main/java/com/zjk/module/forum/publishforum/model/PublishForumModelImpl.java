package com.zjk.module.forum.publishforum.model;

import com.zjk.common.mvp.mode.BaseModel;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.ForumInfo;
import com.zjk.module.forum.publishforum.present.IPublishForumPresenter;
import com.zjk.param.PublishForumParam;
import com.zjk.result.PublishForumResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/11
 */

public class PublishForumModelImpl extends BaseModel<IPublishForumPresenter> implements IPublishForumModel {

    public PublishForumModelImpl(IPublishForumPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void publish(ForumInfo forumInfo, final PublishForumListener listener) {
        PublishForumParam param = new PublishForumParam();
        param.page = "/forum/publishForum";
        param.forumInfo = forumInfo;
        LogicImpl.getInstance().publishForum(param, new LogicHandler<PublishForumResult>() {
            @Override
            public void onResult(PublishForumResult result, boolean onUIThread) {
                if (onUIThread && result.isOk()) {
                    listener.publishForumSuccess(result.bool);
                } else if (onUIThread) {
                    listener.publishForumFail(result);
                }
            }
        });
    }
}
