package com.zjk.common.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zjk.common.mvp.presenter.IBasePresenter;
import com.zjk.model.UserInfo;
import com.zjk.module.home.sports.base.view.BaseSportsFragment;
import com.zjk.util.DebugUtil;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements View.OnClickListener {

    protected static final String TAG = "BaseFragment";
    protected BaseActivity mActivity;

    @Nullable
    protected T mPresenter;

    public BaseFragment() {

    }

    protected void setActivity(BaseActivity activity) {
        this.mActivity = activity;
    }

    public BaseFragment(BaseActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DebugUtil.debug(TAG, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugUtil.debug(TAG, "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DebugUtil.debug(TAG, "");
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DebugUtil.debug(TAG, "");
    }

    @Override
    public void onStart() {
        super.onStart();
        DebugUtil.debug(TAG, "");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        DebugUtil.debug(TAG, "");
    }

    @Override
    public void onStop() {
        super.onStop();
        DebugUtil.debug(TAG, "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DebugUtil.debug(TAG, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DebugUtil.debug(TAG, "");
    }

    public void setArgs(Bundle args) {

    }

    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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

    public UserInfo getUserInfo() {
        return mActivity.getUserInfo();
    }

    /**
     * resultCode在activity里判断
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {

    }

    protected abstract void findWidget();

    protected abstract void setListener();

    protected abstract void init();

    public interface IProgress {
        void showLoadingProgress(final int msgResId);

        void dismissLoadingProgress();
    }

    public interface IStartActivity {
        void startActivityForResult(int req);
    }
}
