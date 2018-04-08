package com.zjk.module.home.fragment.sports.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjk.common.ui.BaseFragment;
import com.zjk.common.ui.MyFragmentPageAdapter;
import com.zjk.module.home.sports.planning.view.PlanningFragment;
import com.zjk.module.home.sports.riding.view.RidingFragment;
import com.zjk.module.home.sports.running.view.RunningFragment;
import com.zjk.module.home.sports.search.view.SearchFragment;
import com.zjk.module.home.sports.walk.view.WalkFragment;
import com.zjk.run_help.R;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class SportsFragment extends BaseFragment {

    private static final String TAG = "SportsFragment";

    private static final int SPORTS_TAB_COUNT = 5;

    private View mView;
    private TabLayout mTlSports;
    private ViewPager mVpSports;

    private MyFragmentPageAdapter<BaseFragment> mAdapter;
    private BaseFragment[] mFragmentArray;
    private String[] mTitles;

    public static SportsFragment newInstance() {
        return new SportsFragment();
    }

    public SportsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sports, container, false);
        findWidget();
        setListener();
        init();
        return mView;
    }

    @Override
    protected void findWidget() {
        mTlSports = (TabLayout) mView.findViewById(R.id.tl_sports);
        mVpSports = (ViewPager) mView.findViewById(R.id.vp_sports);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        mFragmentArray = new BaseFragment[SPORTS_TAB_COUNT];
        mFragmentArray[0] = new PlanningFragment();
        mFragmentArray[1] = new WalkFragment();
        mFragmentArray[2] = new RunningFragment();
        mFragmentArray[3] = new RidingFragment();
        mFragmentArray[4] = new SearchFragment();
        mTitles = new String[SPORTS_TAB_COUNT];
        mTitles[0] = getContext().getString(R.string.planning);
        mTitles[1] = getContext().getString(R.string.walk);
        mTitles[2] = getContext().getString(R.string.run);
        mTitles[3] = getContext().getString(R.string.riding);
        mTitles[4] = getContext().getString(R.string.search);
        mAdapter = new MyFragmentPageAdapter<>(getContext(), getFragmentManager(), mFragmentArray, mTitles);
        mVpSports.setAdapter(mAdapter);
        mVpSports.setOffscreenPageLimit(SPORTS_TAB_COUNT);
        mTlSports.setupWithViewPager(mVpSports);
        mTlSports.setTabMode(TabLayout.MODE_SCROLLABLE); // MODE_FIXED
    }

    @Override
    public void onClick(View v) {

    }
}
