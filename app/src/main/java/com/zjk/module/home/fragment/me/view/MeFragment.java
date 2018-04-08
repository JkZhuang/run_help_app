package com.zjk.module.home.fragment.me.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.zjk.common.ui.BaseFragment;
import com.zjk.run_help.R;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class MeFragment extends BaseFragment {

    private static final String TAG = "MeFragment";

    private View mView;
    private RelativeLayout mRlSportsAchievement;
    private RelativeLayout mRlSetting;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    public MeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_me, container, false);
        return mView;
    }

    @Override
    protected void findWidget() {
        mRlSportsAchievement = (RelativeLayout) mView.findViewById(R.id.rl_sports_achievement);
        mRlSetting = (RelativeLayout) mView.findViewById(R.id.rl_setting);
    }

    @Override
    protected void setListener() {
        mRlSportsAchievement.setOnClickListener(this);
        mRlSetting.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sports_achievement:

                break;
            case R.id.rl_setting:

                break;
        }
    }
}
