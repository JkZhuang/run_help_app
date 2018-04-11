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

import com.zjk.common.chooselocalpicture.ui.ChooseLocalPictureActivity;
import com.zjk.common.ui.BaseActivity;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.ToastUtil;

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
}
