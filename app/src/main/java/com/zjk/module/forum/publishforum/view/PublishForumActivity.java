package com.zjk.module.forum.publishforum.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zjk.common.app.App;
import com.zjk.common.chooselocalpicture.ui.ChooseLocalPictureActivity;
import com.zjk.common.ui.BaseActivity;
import com.zjk.model.ForumInfo;
import com.zjk.model.UserInfo;
import com.zjk.module.forum.publishforum.model.PublishForumModelImpl;
import com.zjk.module.forum.publishforum.present.PublishForumPresenter;
import com.zjk.module.user.register.view.RegisterActivity;
import com.zjk.okhttp.DefList;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.DebugUtil;
import com.zjk.util.ToastUtil;

import java.io.File;
import java.util.Date;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/11
 */

public class PublishForumActivity extends BaseActivity implements IPublishForumView {

    private static final String TAG = "PublishActivity";

    private static final int REQUEST_GET_PHOTO = 4;

    private Toolbar mToolbar;
    private EditText mEtContent;
    private ImageView mIvAddPhoto;
    private FloatingActionButton mFabSend;

    private PublishForumPresenter mPresenter;

    private String imagePath = DefList.EMPTY;

    public static void start(BaseActivity activity, int req) {
        Intent intent = new Intent(activity, PublishForumActivity.class);
        activity.startActivityForResult(intent, req);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_forum);

        findWidget();
        setListener();
        init();

        mPresenter = new PublishForumPresenter(this, new PublishForumModelImpl());
        mPresenter.start();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mIvAddPhoto = (ImageView) findViewById(R.id.iv_add_photo);
        mFabSend = (FloatingActionButton) findViewById(R.id.fab_send);
    }

    @Override
    protected void setListener() {
        mIvAddPhoto.setOnClickListener(this);
        mFabSend.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);

    }

    private void sendForum() {
        String content = mEtContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.shortShow(this, R.string.empty_content);
            return;
        }
        UserInfo userInfo = getUserInfo();
        ForumInfo forumInfo = new ForumInfo();
        forumInfo.setuId(userInfo.getuId());
        forumInfo.setHeadPhotoUrl(userInfo.getHeadUrl());
        forumInfo.setUserName(userInfo.getUserName());
        forumInfo.setContent(content);
        forumInfo.setPhotoUrl(imagePath);
        forumInfo.setTime(new Date());
        mPresenter.publishForum(forumInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_photo:
                ChooseLocalPictureActivity.start(this, REQUEST_GET_PHOTO);
                break;
            case R.id.fab_send:
                sendForum();
                break;
        }
    }

    @Override
    public void showProgress(int msgId) {
        showLoadingDialog(msgId);
    }

    @Override
    public void hideProgress() {
        dismissLoadingDialog();
    }

    @Override
    public void publishForumSuccess(boolean isOnUIThread, boolean bool) {
        if (isOnUIThread && bool) {
            ToastUtil.shortShow(this, R.string.publish_forum_success);
            setResult(RESULT_OK);
            finish();
        } else if (isOnUIThread) {
            ToastUtil.shortShow(this, R.string.publish_forum_fail);
        }
    }

    @Override
    public void publishForumFail(boolean isOnUIThread, Result result) {
        if (isOnUIThread) {
            ToastUtil.shortShow(this, result.errMsg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_GET_PHOTO) {
            imagePath = data.getStringExtra(RegisterActivity.KEY_IMAGE);
            File file = new File(imagePath);
            Glide.with(this)
                    .load(file)
                    .placeholder(R.drawable.photo_default)
                    .into(mIvAddPhoto);

            DebugUtil.debug(TAG, imagePath);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
