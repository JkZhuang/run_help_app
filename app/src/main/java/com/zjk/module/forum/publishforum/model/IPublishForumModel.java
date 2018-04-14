package com.zjk.module.forum.publishforum.model;

import com.zjk.common.mvp.mode.IModel;
import com.zjk.model.ForumInfo;
import com.zjk.result.Result;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/11
 */

public interface IPublishForumModel extends IModel {

    void publish(ForumInfo forumInfo, PublishForumListener listener);

    interface PublishForumListener {
        void publishForumSuccess(boolean isOnUIThread, boolean bool);

        void publishForumFail(boolean isOnUIThread, Result result);
    }
}
