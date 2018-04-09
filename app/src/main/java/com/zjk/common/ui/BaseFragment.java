package com.zjk.common.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected BaseActivity mActivity;

    public BaseFragment() {

    }

    public BaseFragment(BaseActivity activity) {
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
