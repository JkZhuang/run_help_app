package com.zjk.module.home.fragment.dynamic.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjk.common.ui.BaseFragment;
import com.zjk.run_help.R;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class DynamicFragment extends BaseFragment {

    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    public DynamicFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic, container, false);
    }

    @Override
    protected void findWidget() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {

    }
}
