package com.zjk.module.home.sports.walk.view;

import com.zjk.common.ui.BaseActivity;
import com.zjk.module.home.sports.base.view.BaseSportsFragment;

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

    public WalkFragment() {

    }
}
