package com.zjk.module.home.sports.planning.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.BaseFragment;
import com.zjk.common.ui.ListItemDividerDecoration;
import com.zjk.common.ui.refresh.MaterialRefreshLayout;
import com.zjk.common.ui.refresh.MaterialRefreshListener;
import com.zjk.common.util.DeviceUtils;
import com.zjk.model.SportsSuggestion;
import com.zjk.module.home.fragment.sports.listener.SwitchListener;
import com.zjk.module.home.sports.base.view.BaseSportsFragment;
import com.zjk.module.home.sports.planning.adapter.OnItemClickListener;
import com.zjk.module.home.sports.planning.adapter.SportsPlanningAdapter;
import com.zjk.module.home.sports.planning.present.ISportsPlanningPresenter;
import com.zjk.module.home.sports.planning.present.SportsPlanningPresenter;
import com.zjk.module.home.sports.walk.view.WalkFragment;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.DebugUtil;
import com.zjk.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class SportsPlanningFragment extends BaseFragment<ISportsPlanningPresenter>
        implements ISportsPlanningView, OnItemClickListener {

    private static final String TAG = "SportsPlanningFragment";

    private View mView;
    private MaterialRefreshLayout mMrlListContainer;
    private RecyclerView mRvSportsPlanning;

    private SportsPlanningAdapter mAdapter;
    private SportsPlanningPresenter mPresenter;
    private List<SportsSuggestion> mData = new ArrayList<>();
    private SwitchListener listener;

    public static SportsPlanningFragment newInstance(BaseActivity activity) {
        SportsPlanningFragment fragment = new SportsPlanningFragment();
        fragment.setActivity(activity);
        return fragment;
    }

    public SportsPlanningFragment() {

    }

    public void setSwitchListener(SwitchListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_planning, container, false);
        findWidget();
        setListener();
        init();
        mPresenter = new SportsPlanningPresenter(this);
        return mView;
    }

    @Override
    protected void findWidget() {
        mMrlListContainer = (MaterialRefreshLayout) mView.findViewById(R.id.mrl_list_container);
        mRvSportsPlanning = (RecyclerView) mView.findViewById(R.id.rv_sports_planning);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        mAdapter = new SportsPlanningAdapter(getContext());
        mAdapter.setListener(this);
        mRvSportsPlanning.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRvSportsPlanning.addItemDecoration(new ListItemDividerDecoration(1,
                RecyclerView.VERTICAL, getContext().getResources().getColor(R.color.divider_color), true,
                (int) DeviceUtils.dipToPixels(10), 0, (int) DeviceUtils.dipToPixels(10), 0));
        mRvSportsPlanning.setAdapter(mAdapter);

        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMrlListContainer.autoRefresh();
            }
        }, 100);
        mMrlListContainer.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if (mPresenter != null) {
                    DebugUtil.debug(TAG, "onRefresh");
                    mPresenter.getSportsSuggestion(getUserInfo().getuId());
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getSportsSuggestionFail(Result result) {
        mMrlListContainer.finishRefreshing();
        ToastUtil.shortShow(getContext(), R.string.get_sports_planning_suggestion_fail);
    }

    @Override
    public void getSportsSuggestionSuccess(List<SportsSuggestion> data) {
        mMrlListContainer.finishRefreshing();
        mData.clear();
        mData.addAll(data);
        mAdapter.setData(mData);
    }

    @Override
    public void onItemClick(int position) {
        if (position >= mData.size()) {
            return;
        }
        SportsSuggestion suggestion = mData.get(position);
        Bundle args = new Bundle();
        args.putDouble(BaseSportsFragment.KEY_DISTANCE, suggestion.getDistance());
        if (listener != null) {
            listener.switchFragment(suggestion.getType() + 1, args);
        }
    }
}
