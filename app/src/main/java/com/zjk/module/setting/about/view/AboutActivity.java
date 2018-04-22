package com.zjk.module.setting.about.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zjk.common.ui.BaseActivity;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public class AboutActivity extends BaseActivity {

    private static final String TAG = "AboutActivity";

    private Toolbar mToolbar;

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
    }

    @Override
    public void onClick(View v) {

    }
}
