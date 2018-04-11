package com.zjk.module.home.sports.planning.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjk.common.app.App;
import com.zjk.common.ui.BaseFragment;
import com.zjk.common.ui.ListItemDividerDecoration;
import com.zjk.common.ui.refresh.MaterialRefreshLayout;
import com.zjk.common.util.DeviceUtils;
import com.zjk.model.SportsSuggestion;
import com.zjk.module.home.sports.planning.adapter.SportsPlanningAdapter;
import com.zjk.module.home.sports.planning.model.SportsPlanningModelImpl;
import com.zjk.module.home.sports.planning.present.SportsPlanningPresenter;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.ToastUtil;

import java.util.List;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class PlanningFragment extends BaseFragment implements ISportsPlanningView {

    private static final String TAG = "PlanningFragment";

    private View mView;
    private SwipeRefreshLayout mSrlListContainer;
    private RecyclerView mRvSportsPlanning;

    private SportsPlanningAdapter mAdapter;
    private SportsPlanningPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_planning, container, false);
        findWidget();
        setListener();
        init();
        mPresenter = new SportsPlanningPresenter(this, new SportsPlanningModelImpl());
        mPresenter.start();
        mPresenter.getSportsSuggestion(App.instance().getUserInfo().getuId());
        return mView;
    }

    @Override
    protected void findWidget() {
        mSrlListContainer = (SwipeRefreshLayout) mView.findViewById(R.id.sl_list_container);
        mRvSportsPlanning = (RecyclerView) mView.findViewById(R.id.rv_sports_planning);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        MaterialRefreshLayout m;
        mSrlListContainer.setEnabled(false);
        mAdapter = new SportsPlanningAdapter(getContext());
        mRvSportsPlanning.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRvSportsPlanning.addItemDecoration(new ListItemDividerDecoration(1,
                RecyclerView.VERTICAL, getContext().getResources().getColor(R.color.divider_color), true,
                (int) DeviceUtils.dipToPixels(10), 0, (int) DeviceUtils.dipToPixels(10), 0));
        mRvSportsPlanning.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
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
    public void getSportsSuggestionFail(boolean onUIThread, Result result) {
        if (onUIThread) {
            ToastUtil.shortShow(getContext(), R.string.get_sports_planning_suggestion_fail);
        }
    }

    @Override
    public void getSportsSuggestionSuccess(boolean onUIThread, List<SportsSuggestion> data) {
        if (onUIThread) {
            mAdapter.setData(data);
            ToastUtil.shortShow(getContext(), R.string.get_sports_planning_suggestion_success);
        }
    }
}
