package com.zjk.common.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zjk.common.util.DisplayUtils;
import com.zjk.common.util.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BaseActivity";

    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private LoadingDialog mLoadingDialog;

    private boolean isFinished = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayUtils.setStatusBarFontDark(getWindow(), useDarkMode());
    }

    protected abstract void findWidget();

    protected abstract void setListener();

    protected abstract void init();

    protected void setupActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    /**
     * 状态栏使用是否使用 DarkMode
     */
    protected boolean useDarkMode() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 适配小米状态栏
     *
     * @param darkMode
     */
    protected void setMiUIStatusBar(boolean darkMode) {
        Window window = getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (darkMode) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag); // 状态栏透明且黑色字体
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag); // 清除黑色字体
                }
            } catch (Exception e) {

            }
        }
    }

    protected boolean shouldSetWindowTranslucentStatus() {
        return true;
    }

    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(final EditText edit) {
        if (edit == null) return;
        edit.post(new Runnable() {
            @Override
            public void run() {
                edit.requestFocus();
                InputMethodManager imm = (InputMethodManager) edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
    }

    protected void showLoadingDialog(final int msgResId) {
        if (Utils.isMainThread()) {
            _showProgress(msgResId);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    _showProgress(msgResId);
                }
            });
        }
    }

    private void _showProgress(int msgResId) {
        if (isFinished()) {
            return;
        }
        try {
            dialogInstance().setCancelable(false);
            dialogInstance().setText(String.valueOf(getText(msgResId)));
            dialogInstance().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LoadingDialog dialogInstance() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setCanceledOnTouchOutside(false);
        }

        return mLoadingDialog;
    }

    protected void dismissLoadingDialog() {
        if (isFinished()) {
            return;
        }
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
            mLoadingDialog = null;
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
        isFinished = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinished = true;
    }
}
