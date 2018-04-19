package com.zjk.module.home.sports.walk.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjk.common.data.DefSports;
import com.zjk.common.ui.BaseActivity;
import com.zjk.module.home.sports.base.view.BaseSportsFragment;
import com.zjk.util.DebugUtil;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class WalkFragment extends BaseSportsFragment {

    private static final String TAG = "WalkFragment";

    public static WalkFragment newInstance(BaseActivity activity) {
        WalkFragment fragment = new WalkFragment();
        fragment.setActivity(activity);
        return fragment;
    }

    public static WalkFragment newInstance(BaseActivity activity, Bundle bundle) {
        WalkFragment fragment = new WalkFragment();
        fragment.setArguments(bundle);
        fragment.setActivity(activity);
        fragment.setType(DefSports.WALK);
        return fragment;
    }

    public WalkFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
