package com.zjk.module.home.sports.riding.view;

import com.zjk.common.ui.BaseActivity;
import com.zjk.module.home.sports.base.view.BaseSportsFragment;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class RidingFragment extends BaseSportsFragment {

    private static final String TAG = "RidingFragment";

    public static RidingFragment newInstance(BaseActivity activity) {
        RidingFragment fragment = new RidingFragment();
        fragment.setActivity(activity);
        return fragment;
    }

    public RidingFragment() {

    }
}
