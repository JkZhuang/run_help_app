package com.zjk.module.home.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.NoScrollViewPager;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/29
 */

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";

    private NoScrollViewPager mViewPager;
    private TextView mTvSports;
    private TextView mTvDiet;
    private TextView mTvDynamic;
    private TextView mTvMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    private void findWidget() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.tab_pager);
        mTvSports = (TextView) findViewById(R.id.tv_sports);
        mTvDiet = (TextView) findViewById(R.id.tv_diet);
        mTvDynamic = (TextView) findViewById(R.id.tv_dynamic);
        mTvMe = (TextView) findViewById(R.id.tv_me);
    }

    public void switchContent(int pageIndex) {
        mViewPager.setCurrentItem(pageIndex, false);
    }
}
