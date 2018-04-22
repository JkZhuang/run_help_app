package com.zjk.module.setting.feedback.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.zjk.common.ui.BaseActivity;
import com.zjk.model.Feedback;
import com.zjk.module.setting.feedback.present.FeedbackPresenter;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.ContactUtil;
import com.zjk.util.ToastUtil;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public class FeedbackActivity extends BaseActivity implements IFeedbackView {

    private static final String TAG = "FeedbackActivity";

    private Toolbar mToolbar;
    private EditText mEtContent;
    private EditText mEtContact;
    private FloatingActionButton mFabSend;

    private FeedbackPresenter mPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEtContact = (EditText) findViewById(R.id.et_contact);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mFabSend = (FloatingActionButton) findViewById(R.id.fab_send);
    }

    @Override
    protected void setListener() {
        mFabSend.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
        mPresenter = new FeedbackPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_send:
                send();
                break;
        }
    }

    private void send() {
        if (mEtContent.getText().toString().trim().length() == 0) {
            ToastUtil.shortShow(this, R.string.empty_content);
            return;
        }
        if (!ContactUtil.checkContact(mEtContact.getText().toString().trim())) {
            ToastUtil.shortShow(this, R.string.contact_error);
            return;
        }
        Feedback feedback = new Feedback();
        feedback.setuId(getUserInfo().getuId());
        feedback.setContent(mEtContent.getText().toString().trim());
        feedback.setContact(mEtContact.getText().toString().trim());
        if (mPresenter != null) {
            mPresenter.setFeedback(feedback);
        }
    }

    @Override
    public void showProgress(int msgRes) {
        showLoadingDialog(msgRes);
    }

    @Override
    public void hideProgress() {
        dismissLoadingDialog();
    }

    @Override
    public void setFeedbackSuccess(boolean bool) {
        if (bool) {
            ToastUtil.shortShow(this, R.string.send_feedback_success);
            finish();
        } else {
            ToastUtil.shortShow(this, R.string.send_feedback_fail);
        }
    }

    @Override
    public void setFeedbackFail(Result result) {
        ToastUtil.shortShow(this, result.errMsg);
    }
}
