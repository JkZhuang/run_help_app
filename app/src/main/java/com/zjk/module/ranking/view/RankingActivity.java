package com.zjk.module.ranking.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.ListItemDividerDecoration;
import com.zjk.common.ui.refresh.MaterialRefreshLayout;
import com.zjk.common.ui.refresh.MaterialRefreshListener;
import com.zjk.model.RankingVersion;
import com.zjk.module.ranking.adapter.RankingAdapter;
import com.zjk.module.ranking.present.RankingPresenter;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.DebugUtil;
import com.zjk.util.ToastUtil;

import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/23
 */

public class RankingActivity extends BaseActivity implements IRankingView {

    private static final String TAG = "RankingActivity";

    private Toolbar mToolbar;
    private MaterialRefreshLayout mMrlContainer;
    private RecyclerView mRvRanking;

    private RankingAdapter mAdapter;
    private RankingPresenter mPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, RankingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mMrlContainer = (MaterialRefreshLayout) findViewById(R.id.mrl_list_container);
        mRvRanking = (RecyclerView) findViewById(R.id.rv_ranking);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
        mPresenter = new RankingPresenter(this);
        mAdapter = new RankingAdapter(this);
        mRvRanking.setLayoutManager(new LinearLayoutManager(this));
        mRvRanking.addItemDecoration(new ListItemDividerDecoration(1,
                RecyclerView.VERTICAL, getResources().getColor(R.color.divider_color), true,
                0, 0, 0, 0));
        mRvRanking.setAdapter(mAdapter);

        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMrlContainer.autoRefresh();
            }
        }, 100);
        mMrlContainer.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if (mPresenter != null) {
                    DebugUtil.debug(TAG, "onRefresh");
                    mPresenter.getRankingVersion(getUserInfo().getuId());
                }
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

            }
        });
    }

    @Override
    public void onClick(View v) {

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
    public void getRankingVersionSuccess(List<RankingVersion> rankingVersions) {
        mMrlContainer.finishRefreshing();
        mAdapter.setData(rankingVersions);
    }

    @Override
    public void getRankingVersionFail(Result result) {
        mMrlContainer.finishRefreshing();
        ToastUtil.shortShow(this, result.errMsg);
    }
}
