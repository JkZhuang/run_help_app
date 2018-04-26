package com.zjk.module.forum.dynamic.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjk.common.layout.ResizeLayout;
import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.ListItemDividerDecoration;
import com.zjk.common.ui.refresh.MaterialRefreshLayout;
import com.zjk.common.ui.refresh.MaterialRefreshListener;
import com.zjk.model.CommentForumInfo;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;
import com.zjk.module.forum.dynamic.adapter.DynamicAdapter;
import com.zjk.module.forum.dynamic.model.DynamicModelImpl;
import com.zjk.module.forum.dynamic.present.DynamicPresenter;
import com.zjk.module.forum.dynamic.present.IDynamicPresenter;
import com.zjk.module.forum.publishforum.view.PublishForumActivity;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.DebugUtil;
import com.zjk.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class DynamicActivity extends BaseActivity<IDynamicPresenter> implements IDynamicView, ResizeLayout.OnResizeListener {

    private static final String TAG = "DynamicActivity";

    private static final int REQUEST_PUBLISH_FORUM = 3;

    private Toolbar mToolbar;
    private MaterialRefreshLayout mMrlDynamicContainer;
    private RecyclerView mRvDynamic;
    private FloatingActionButton mFabAdd;

    private RelativeLayout mRlCommentContainer;
    private EditText mEtComment;
    private TextView mTvSend;

    private DynamicAdapter mAdapter;
    private DynamicPresenter mPresenter;

    private List<ForumInfo> data = new ArrayList<>();
    // 评论时保存引用
    private ForumInfo mForumInfo;
    private CommentForumInfo mCommentForumInfo;

    public static void start(Context context) {
        Intent intent = new Intent(context, DynamicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mMrlDynamicContainer = (MaterialRefreshLayout) findViewById(R.id.mrl_dynamic_container);
        mRvDynamic = (RecyclerView) findViewById(R.id.rv_dynamic);
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        mRlCommentContainer = (RelativeLayout) findViewById(R.id.rl_comment_container);
        mEtComment = (EditText) findViewById(R.id.et_comment);
        mTvSend = (TextView) findViewById(R.id.tv_send);
    }

    @Override
    protected void setListener() {
        mFabAdd.setOnClickListener(this);
        ((ResizeLayout) findViewById(R.id.resize_layout)).setOnResizeListener(this);
        mTvSend.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
        mPresenter = new DynamicPresenter(this);
        mAdapter = new DynamicAdapter(this, mPresenter);
        mRvDynamic.setLayoutManager(new LinearLayoutManager(this));
        mRvDynamic.addItemDecoration(new ListItemDividerDecoration(1,
                RecyclerView.VERTICAL, this.getResources().getColor(R.color.divider_color), true,
                0, 0, 0, 0));
        mRvDynamic.setNestedScrollingEnabled(false);
        mRvDynamic.setAdapter(mAdapter);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMrlDynamicContainer.autoRefresh();
            }
        }, 100);
        mMrlDynamicContainer.setLoadMore(true);
        mMrlDynamicContainer.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if (mPresenter != null) {
                    DebugUtil.debug(TAG, "onRefresh");
                    mPresenter.getForum(getUserInfo().getuId(), 0, false);
                }
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (mPresenter != null) {
                    DebugUtil.debug(TAG, "onRefreshLoadMore");
                    mPresenter.getForum(getUserInfo().getuId(), data.get(data.size() - 1).getfId(), true);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                PublishForumActivity.start(this, REQUEST_PUBLISH_FORUM);
                break;
            case R.id.tv_send:
                sendComment();
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
    public void commentForumSuccess(boolean bool, CommentForumInfo commentForumInfo) {
        if (bool) {
            mPresenter.getForum(getUserInfo().getuId(), 0, false);
        }
    }

    @Override
    public void commentForumFail(Result result) {
        ToastUtil.shortShow(this, result.errMsg);
    }

    @Override
    public void likeForumSuccess(boolean bool, LikeForumInfo likeForumInfo) {
        if (bool) {
            mPresenter.getForum(getUserInfo().getuId(), 0, false);
        }
    }

    @Override
    public void likeForumFail(Result result) {

    }

    @Override
    public void getForumSuccess(List<ForumInfo> forumInfos, boolean loadMore) {
        if (!loadMore) {
            data.clear();
        }
        data.addAll(forumInfos);
        mMrlDynamicContainer.finishRefreshing();
        mMrlDynamicContainer.finishRefreshLoadMore();
        if (loadMore) {
            mAdapter.setData(data, data.size() - forumInfos.size() - 1, forumInfos.size());
        } else {
            mAdapter.setData(data);
        }
        if (forumInfos.size() == 0) {
            mMrlDynamicContainer.setLoadMore(false);
        }
    }

    @Override
    public void getForumFail(Result result) {
        mMrlDynamicContainer.finishRefreshing();
        mMrlDynamicContainer.finishRefreshLoadMore();
        ToastUtil.shortShow(this, result.errMsg);
    }

    @Override
    public void showCommentWidget(ForumInfo forumInfo, CommentForumInfo commentForumInfo) {
        mRlCommentContainer.setVisibility(View.VISIBLE);
        mEtComment.setText("");
        showKeyboard(mEtComment);
        mForumInfo = forumInfo;
        mCommentForumInfo = commentForumInfo;
    }

    private void sendComment() {
        String content = mEtComment.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.shortShow(this, R.string.empty_content);
            return;
        }
        CommentForumInfo commentForumInfo = new CommentForumInfo();
        commentForumInfo.setfId(mForumInfo.getfId());
        commentForumInfo.setuId(getUserInfo().getuId());
        commentForumInfo.setUserName(getUserInfo().getUserName());
        if (mCommentForumInfo != null) {
            commentForumInfo.settUId(mCommentForumInfo.getuId());
            commentForumInfo.settUserName(mCommentForumInfo.getUserName());
        } else {
            commentForumInfo.settUId(mForumInfo.getuId());
            commentForumInfo.settUserName(mForumInfo.getUserName());
        }
        commentForumInfo.setContent(content);
        commentForumInfo.setTime(new Date());
        hideCommentWidget();
        mPresenter.commentForum(commentForumInfo);
    }

    @Override
    public void hideCommentWidget() {
        hideKeyboard(mEtComment);
        mRlCommentContainer.setVisibility(View.GONE);
    }

    @Override
    public String getCommentText() {
        return mEtComment.getText().toString().trim();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResize(int w, int h, int oldw, int oldh) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PUBLISH_FORUM:
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMrlDynamicContainer.autoRefresh();
                    }
                }, 100);
                break;
        }
    }
}
