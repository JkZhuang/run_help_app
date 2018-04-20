package com.zjk.module.setting.permission.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zjk.common.ui.BaseActivity;
import com.zjk.run_help.R;
import com.zjk.util.OpenAutoStartUtil;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public class PermissionActivity extends BaseActivity {

    private static final String TAG = "PermissionActivity";

    private Toolbar mToolbar;
    private TextView mTvBackstageProtected;
    private TextView mTvAvoidSleeping;

    public static void start(Context context) {
        Intent intent = new Intent(context, PermissionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvBackstageProtected = (TextView) findViewById(R.id.tv_backstage_protected);
        mTvAvoidSleeping = (TextView) findViewById(R.id.tv_avoid_sleeping);
    }

    @Override
    protected void setListener() {
        mTvBackstageProtected.setOnClickListener(this);
        mTvAvoidSleeping.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_backstage_protected:
                OpenAutoStartUtil.jumpStartInterface(PermissionActivity.this);
                break;
            case R.id.tv_avoid_sleeping:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                break;
        }
    }
}
