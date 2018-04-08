package com.zjk.module.user.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.zjk.common.app.App;
import com.zjk.common.sp.SpEditor;
import com.zjk.common.sp.SpFileName;
import com.zjk.common.sp.SpKey;
import com.zjk.common.ui.BaseActivity;
import com.zjk.model.UserInfo;
import com.zjk.module.home.view.HomeActivity;
import com.zjk.module.user.login.model.LoginModelImpl;
import com.zjk.module.user.login.present.LoginPresenter;
import com.zjk.module.user.register.view.IRegisterView;
import com.zjk.module.user.register.view.RegisterActivity;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.ContactUtil;
import com.zjk.util.ToastUtil;

/**
 * Created by pandengzhe on 2018/4/1.
 */

public class LoginActivity extends BaseActivity implements ILoginView {

    private static final String TAG = "LoginActivity";

    private Toolbar mToolbar;
    private AutoCompleteTextView mTvPhone;
    private EditText mEtPassword;
    private TextView mTvLogin;
    private TextView mTvRegister;

    private LoginPresenter mPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findWidget();
        setListener();
        init();
        mPresenter = new LoginPresenter(new LoginModelImpl(), this);
        mPresenter.start();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvPhone = (AutoCompleteTextView) findViewById(R.id.tv_phone);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mTvLogin = (TextView) findViewById(R.id.tv_login);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
    }

    @Override
    protected void setListener() {
        mTvLogin.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
        if (!TextUtils.isEmpty(SpEditor.get(SpFileName.SP_USER, SpKey.USER_PHONE, ""))) {
            mTvPhone.setText(SpEditor.get(SpFileName.SP_USER, SpKey.USER_PHONE, ""));
            mEtPassword.setText(SpEditor.get(SpFileName.SP_USER, SpKey.USER_PASSWORD, ""));
        }
    }

    private void login() {
        if (!ContactUtil.checkMobile(mTvPhone.getText().toString().trim())) {
            ToastUtil.shortShow(LoginActivity.this, R.string.error_phone_format);
            return;
        }
        if (mEtPassword.getText().toString().trim().length() < 6) {
            ToastUtil.shortShow(LoginActivity.this, R.string.error_password_format);
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(mTvPhone.getText().toString().trim());
        userInfo.setPassword(mEtPassword.getText().toString().trim());
        mPresenter.doLogin(userInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_register:
                RegisterActivity.start(LoginActivity.this);
                break;
        }
    }

    @Override
    public void showProgress() {
        showLoadingDialog(R.string.login_ing);
    }

    @Override
    public void hideProgress() {
        dismissLoadingDialog();
    }

    @Override
    public void loginFail(boolean onUIThread, Result result) {
        if (onUIThread) {
            ToastUtil.shortShow(LoginActivity.this, result.errMsg);
        }
    }

    @Override
    public void loginSuccess(boolean onUIThread, UserInfo userInfo) {
        if (onUIThread) {
            ToastUtil.shortShow(LoginActivity.this, R.string.login_success);
        }
        App.instance().setUserInfo(userInfo);
        SpEditor.putAndApply(SpFileName.SP_USER, SpKey.USER_PHONE, userInfo.getPhone());
        SpEditor.putAndApply(SpFileName.SP_USER, SpKey.USER_PASSWORD, userInfo.getPassword());
        HomeActivity.start(LoginActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
