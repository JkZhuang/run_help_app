package com.zjk.module.forum.publishforum.model;

import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.ForumInfo;
import com.zjk.param.PublishForumParam;
import com.zjk.result.PublishForumResult;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/11
 */

public class PublishForumModelImpl implements IPublishForumModel {

    public PublishForumModelImpl() {

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
                    listener.publishForumSuccess(onUIThread, result.bool);
                } else if (onUIThread) {
                    listener.publishForumFail(onUIThread, result);
                }
            }
        });
    }
}
