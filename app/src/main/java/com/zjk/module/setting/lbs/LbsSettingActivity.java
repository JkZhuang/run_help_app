package com.zjk.module.setting.lbs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.zjk.common.sp.SpEditor;
import com.zjk.common.sp.SpFileName;
import com.zjk.common.sp.SpKey;
import com.zjk.common.ui.BaseActivity;
import com.zjk.okhttp.DefList;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/26
 */

public class LbsSettingActivity extends BaseActivity {

    private static final String TAG = "LbsSettingActivity";

    private Toolbar mToolbar;
    private EditText mEtIp;
    private EditText mEtPort;
    private FloatingActionButton mFabSave;

    public static void start(Context context) {
        Intent intent = new Intent(context, LbsSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_lbs);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        mEtIp = (EditText) findViewById(R.id.et_ip);
        mEtPort = (EditText) findViewById(R.id.et_port);
    }

    @Override
    protected void setListener() {
        mFabSave.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
        mEtIp.setText(DefList.ip);
        mEtPort.setText(String.valueOf(DefList.port));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_save:
                DefList.ip = mEtIp.getText().toString().trim();
                DefList.port = Integer.valueOf(mEtPort.getText().toString().trim());
                DefList.url = "http://" + DefList.ip + ":" + String.valueOf(DefList.port);
                SpEditor.putAndApply(SpFileName.SP_SETTING, SpKey.KEY_LBS, DefList.url);
                hideKeyboard(mEtIp);
                finish();
                break;
        }
    }
}
