package com.zjk.module.home.fragment.dynamic.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjk.common.app.App;
import com.zjk.common.ui.BaseFragment;
import com.zjk.common.ui.ListItemDividerDecoration;
import com.zjk.model.ForumInfo;
import com.zjk.model.LikeForumInfo;
import com.zjk.module.home.fragment.dynamic.adapter.DynamicAdapter;
import com.zjk.module.home.fragment.dynamic.model.DynamicModelImpl;
import com.zjk.module.home.fragment.dynamic.present.DynamicPresenter;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class DynamicFragment extends BaseFragment implements IDynamicView {

    private static final String TAG = "DynamicFragment";

    private View mView;
    private Toolbar mToolbar;
    private RecyclerView mRvDynamic;
    private FloatingActionButton mFabAdd;

    private DynamicAdapter mAdapter;
    private DynamicPresenter mPresenter;

    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    public DynamicFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic, container, false);
        mPresenter = new DynamicPresenter(this, new DynamicModelImpl());
        mPresenter.start();
        mPresenter.getForum(App.instance().getUserInfo().getuId(), 0);
        findWidget();
        setListener();
        init();
        return mView;
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mRvDynamic = (RecyclerView) mView.findViewById(R.id.rv_dynamic);
        mFabAdd = (FloatingActionButton) mView.findViewById(R.id.fab_add);
    }

    @Override
    protected void setListener() {
        mFabAdd.setOnClickListener(this);
    }

    @Override
    protected void init() {
        mAdapter = new DynamicAdapter(getContext(), mPresenter);
        mRvDynamic.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvDynamic.addItemDecoration(new ListItemDividerDecoration(1,
                RecyclerView.VERTICAL, getContext().getResources().getColor(R.color.divider_color), true,
                0, 0, 0, 0));
        mRvDynamic.setNestedScrollingEnabled(false);
        mRvDynamic.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:

                break;
        }
    }

    @Override
    public void showProgress(int msgId) {
        if (getActivity() instanceof IProgress) {
            ((IProgress) getActivity()).showLoadingProgress(msgId);
        }
    }

    @Override
    public void hideProgress() {
        if (getActivity() instanceof IProgress) {
            ((IProgress) getActivity()).dismissLoadingProgress();
        }
    }

    @Override
    public void publishForumSuccess(boolean isOnUIThread, boolean bool) {

    }

    @Override
    public void publishForumFail(boolean isOnUIThread, Result result) {

    }

    @Override
    public void commentForumSuccess(boolean isOnUIThread, boolean bool) {

    }

    @Override
    public void commentForumFail(boolean isOnUIThread, Result result) {

    }

    @Override
    public void likeForumSuccess(boolean isOnUIThread, boolean bool) {
        if (isOnUIThread && bool) {
            mPresenter.getForum(App.instance().getUserInfo().getuId(), 0);
        }
    }

    @Override
    public void likeForumFail(boolean isOnUIThread, Result result) {

    }

    @Override
    public void getForumSuccess(boolean isOnUIThread, List<ForumInfo> forumInfos) {
        if (isOnUIThread) {
            mAdapter.setData(forumInfos);
            ToastUtil.shortShow(getContext(), R.string.get_forum_success);
        }
    }

    @Override
    public void getForumFail(boolean isOnUIThread, Result result) {
        if (isOnUIThread) {
            ToastUtil.shortShow(getContext(), result.errMsg);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
