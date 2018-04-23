package com.zjk.module.sportsachievement.activity.view;

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
import com.zjk.model.SportsData;
import com.zjk.module.sportsachievement.activity.adapter.SportsAchievementAdapter;
import com.zjk.module.sportsachievement.activity.presenter.SportsAchievementPresenter;
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

public class SportsAchievementActivity extends BaseActivity implements IISportsAchievementView {

    private static final String TAG = "SportsAchievementActivity";

    private Toolbar mToolbar;
    private MaterialRefreshLayout mMrlContainer;
    private RecyclerView mRvSportsAche;

    private SportsAchievementAdapter mAdapter;
    private SportsAchievementPresenter mPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, SportsAchievementActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mMrlContainer = (MaterialRefreshLayout) findViewById(R.id.mrl_list_container);
        mRvSportsAche = (RecyclerView) findViewById(R.id.rv_sports_ache);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        mPresenter = new SportsAchievementPresenter(this);
        setupActionBar(mToolbar);
        mAdapter = new SportsAchievementAdapter(this);

        mRvSportsAche.setLayoutManager(new LinearLayoutManager(this));
        mRvSportsAche.addItemDecoration(new ListItemDividerDecoration(1,
                RecyclerView.VERTICAL, getResources().getColor(R.color.divider_color), true,
                0, 0, 0, 0));
        mRvSportsAche.setAdapter(mAdapter);

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
                    mPresenter.getUserSportsData(getUserInfo().getuId());
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
    public void showProgress(int msgRes) {
        showLoadingDialog(msgRes);
    }

    @Override
    public void hideProgress() {
        dismissLoadingDialog();
    }

    @Override
    public void getUserSportsDataSuccess(List<SportsData> sportsDatas) {
        mMrlContainer.finishRefreshing();
        mAdapter.setData(sportsDatas);
    }

    @Override
    public void getUserSportsDataFail(Result result) {
        mMrlContainer.finishRefreshing();
        ToastUtil.shortShow(this, result.errMsg);
    }
}
