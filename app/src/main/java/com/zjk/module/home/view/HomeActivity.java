package com.zjk.module.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zjk.common.app.App;
import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.BaseFragment;
import com.zjk.common.ui.MyFragmentPageAdapter;
import com.zjk.common.ui.NoScrollViewPager;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.model.UserInfo;
import com.zjk.module.forum.dynamic.view.DynamicActivity;
import com.zjk.module.home.fragment.diet.view.DietFragment;
import com.zjk.module.home.fragment.me.view.MeFragment;
import com.zjk.module.home.fragment.sports.view.SportsFragment;
import com.zjk.param.LoginParam;
import com.zjk.result.LoginResult;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/29
 */

public class HomeActivity extends BaseActivity implements BaseFragment.IProgress {

    private static final String TAG = "HomeActivity";

    private static final int HOME_TAB_COUNT = 4;

    private NoScrollViewPager mViewPager;
    private TextView[] mTvTabs = new TextView[HOME_TAB_COUNT];

    private MyFragmentPageAdapter<BaseFragment> mFragmentAdapter;
    private BaseFragment[] mFragmentArray;

    public static void start(BaseActivity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findWidget();
        setListener();
        init();

//        test();
    }

    private void test() {
        LoginParam param = new LoginParam();
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("18813295244");
        userInfo.setPassword("123456");
        param.userInfo = userInfo;
        param.page = "/user/login";
        LogicImpl.getInstance().login(param, new LogicHandler<LoginResult>() {
            @Override
            public void onResult(LoginResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    App.instance().setUserInfo(result.userInfo);
                }
            }
        });
    }

    @Override
    protected void findWidget() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.tab_pager);
        mTvTabs[0] = (TextView) findViewById(R.id.tv_sports);
        mTvTabs[1] = (TextView) findViewById(R.id.tv_diet);
        mTvTabs[2] = (TextView) findViewById(R.id.tv_dynamic);
        mTvTabs[3] = (TextView) findViewById(R.id.tv_me);
    }

    @Override
    protected void setListener() {
        for (int i = 0; i < HOME_TAB_COUNT; i++) {
            mTvTabs[i].setOnClickListener(this);
        }
    }

    @Override
    protected void init() {
        mFragmentArray = new BaseFragment[HOME_TAB_COUNT - 1];
        mFragmentArray[0] = SportsFragment.newInstance(this);
        mFragmentArray[1] = DietFragment.newInstance(this);
        mFragmentArray[2] = MeFragment.newInstance(this);
        mFragmentAdapter = new MyFragmentPageAdapter<>(this, getSupportFragmentManager(), mFragmentArray, new String[HOME_TAB_COUNT - 1]);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(HOME_TAB_COUNT - 1);
    }

    public void switchContent(int pageIndex) {
        mViewPager.setCurrentItem(pageIndex, false);
        if (pageIndex == 2) {
            pageIndex = 3;
        }
        for (int i = 0; i < HOME_TAB_COUNT; i++) {
            if (i == pageIndex) {
                mTvTabs[i].setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                mTvTabs[i].setTextColor(getResources().getColor(R.color.home_tab_text_color));
            }
        }
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
                DynamicActivity.start(this);
                break;
            case R.id.tv_me:
                switchContent(2);
                break;
        }
    }

    @Override
    public void showLoadingProgress(int msgResId) {
        showLoadingDialog(msgResId);
    }

    @Override
    public void dismissLoadingProgress() {
        dismissLoadingDialog();
    }
}
