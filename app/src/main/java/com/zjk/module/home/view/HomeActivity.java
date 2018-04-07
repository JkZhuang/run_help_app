package com.zjk.module.home.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.BaseFragment;
import com.zjk.common.ui.MyFragmentPageAdapter;
import com.zjk.common.ui.NoScrollViewPager;
import com.zjk.module.home.fragment.DietFragment;
import com.zjk.module.home.fragment.DynamicFragment;
import com.zjk.module.home.fragment.MeFragment;
import com.zjk.module.home.fragment.SportsFragment;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/29
 */

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";

    private static final int HOME_TAB_COUNT = 4;

    private NoScrollViewPager mViewPager;
    private TextView mTvSports;
    private TextView mTvDiet;
    private TextView mTvDynamic;
    private TextView mTvMe;

    private MyFragmentPageAdapter<BaseFragment> mFragmentAdapter;
    private BaseFragment[] mFragmentArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.tab_pager);
        mTvSports = (TextView) findViewById(R.id.tv_sports);
        mTvDiet = (TextView) findViewById(R.id.tv_diet);
        mTvDynamic = (TextView) findViewById(R.id.tv_dynamic);
        mTvMe = (TextView) findViewById(R.id.tv_me);
    }

    @Override
    protected void setListener() {
        mTvSports.setOnClickListener(this);
        mTvDiet.setOnClickListener(this);
        mTvDynamic.setOnClickListener(this);
        mTvMe.setOnClickListener(this);
    }

    @Override
    protected void init() {
        mFragmentArray = new BaseFragment[HOME_TAB_COUNT];
        mFragmentArray[0] = new SportsFragment();
        mFragmentArray[1] = new DietFragment();
        mFragmentArray[2] = new DynamicFragment();
        mFragmentArray[3] = new MeFragment();
        mFragmentAdapter = new MyFragmentPageAdapter<>(this, getSupportFragmentManager(), mFragmentArray, new String[HOME_TAB_COUNT]);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(HOME_TAB_COUNT);
    }

    public void switchContent(int pageIndex) {
        mViewPager.setCurrentItem(pageIndex, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sports:
                switchContent(0);
                break;
            case R.id.tv_diet:
                switchContent(1);
                break;
            case R.id.tv_dynamic:
                switchContent(2);
                break;
            case R.id.tv_me:
                switchContent(3);
                break;
        }
    }
}
