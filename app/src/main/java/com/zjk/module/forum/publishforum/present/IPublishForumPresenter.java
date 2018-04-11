package com.zjk.module.forum.publishforum.present;

import com.zjk.common.presenter.BasePresenter;
import com.zjk.model.ForumInfo;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/11
 */

public interface IPublishForumPresenter extends BasePresenter {

    void publishForum(ForumInfo forumInfo);
}
