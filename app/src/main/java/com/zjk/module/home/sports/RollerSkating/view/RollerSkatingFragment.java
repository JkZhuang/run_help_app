package com.zjk.module.home.sports.RollerSkating.view;

import com.zjk.common.data.DefSports;
import com.zjk.common.ui.BaseActivity;
import com.zjk.module.home.sports.base.view.BaseSportsFragment;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class RollerSkatingFragment extends BaseSportsFragment {

    private static final String TAG = "RidingFragment";

    public static RollerSkatingFragment newInstance(BaseActivity activity) {
        RollerSkatingFragment fragment = new RollerSkatingFragment();
        fragment.setActivity(activity);
        fragment.setType(DefSports.ROLLER_SKATING);
        return fragment;
    }

    public RollerSkatingFragment() {

    }
}
