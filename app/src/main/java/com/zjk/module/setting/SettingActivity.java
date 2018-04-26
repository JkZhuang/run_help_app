package com.zjk.module.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zjk.common.ui.BaseActivity;
import com.zjk.module.setting.about.view.AboutActivity;
import com.zjk.module.setting.feedback.view.FeedbackActivity;
import com.zjk.module.setting.lbs.LbsSettingActivity;
import com.zjk.module.setting.permission.view.PermissionActivity;
import com.zjk.run_help.R;
import com.zjk.util.ActivityCollector;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public class SettingActivity extends BaseActivity {

    private static final String TAG = "SettingActivity";

    private Toolbar mToolbar;

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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
        findViewById(R.id.rl_feedback).setOnClickListener(this);
        findViewById(R.id.rl_permission).setOnClickListener(this);
        findViewById(R.id.rl_set_lbs).setOnClickListener(this);
        findViewById(R.id.rl_about).setOnClickListener(this);
        findViewById(R.id.tv_sign_out).setOnClickListener(this);
    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
    }

    private void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.make_sure_sign_out));
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.instance().finishAll();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_feedback:
                FeedbackActivity.start(SettingActivity.this);
                break;
            case R.id.rl_permission:
                PermissionActivity.start(SettingActivity.this);
                break;
            case R.id.rl_set_lbs:
                LbsSettingActivity.start(SettingActivity.this);
                break;
            case R.id.rl_about:
                AboutActivity.start(SettingActivity.this);
                break;
            case R.id.tv_sign_out:
                signOut();
                break;
        }
    }
}
