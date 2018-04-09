package com.zjk.module.user.information.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zjk.common.chooselocalpicture.ui.ChooseLocalPictureActivity;
import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.BaseFragment;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class PersonalActivity extends BaseActivity implements BaseFragment.IProgress, BaseFragment.IStartActivity {

    private static final String TAG = "PersonalActivity";

    private Toolbar mToolbar;

    private PersonFragment mPersonFragment;

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
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
        hideKeyboard(mToolbar);
        setupActionBar(mToolbar);
        mPersonFragment = PersonFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_per_info_container, mPersonFragment).commit();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoadingProgress(int msgResId) {
        showLoadingDialog(msgResId);
    }

    @Override
    public void dismissLoadingProgress() {
        dismissLoadingDialog();
    }

    @Override
    public void startActivityForResult(int req) {
        Intent intent = new Intent(PersonalActivity.this, ChooseLocalPictureActivity.class);
        startActivityForResult(intent, req);
    }
}
