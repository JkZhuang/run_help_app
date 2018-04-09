package com.zjk.module.home.fragment.me.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjk.common.app.App;
import com.zjk.common.ui.BaseFragment;
import com.zjk.common.ui.ShapedImageView;
import com.zjk.module.user.information.view.PersonalActivity;
import com.zjk.run_help.R;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class MeFragment extends BaseFragment {

    private static final String TAG = "MeFragment";

    private View mView;
    private ConstraintLayout MClInfoContainer;
    private ShapedImageView mIvHeadPhoto;
    private TextView mTvNickName;
    private TextView mTvDynamicCount;
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
        findWidget();
        setListener();
        init();
        return mView;
    }

    @Override
    protected void findWidget() {
        MClInfoContainer = (ConstraintLayout) mView.findViewById(R.id.cl_info_container);
        mIvHeadPhoto = (ShapedImageView) mView.findViewById(R.id.iv_head_photo);
        mTvNickName = (TextView) mView.findViewById(R.id.tv_nick_name);
        mTvDynamicCount = (TextView) mView.findViewById(R.id.tv_dynamic_count);
        mRlSportsAchievement = (RelativeLayout) mView.findViewById(R.id.rl_sports_achievement);
        mRlSetting = (RelativeLayout) mView.findViewById(R.id.rl_setting);
    }

    @Override
    protected void setListener() {
        MClInfoContainer.setOnClickListener(this);
        mRlSportsAchievement.setOnClickListener(this);
        mRlSetting.setOnClickListener(this);
    }

    @Override
    protected void init() {
        Glide.with(getContext())
                .load(App.instance().getUserInfo().getHeadUrl())
                .into(mIvHeadPhoto);
        mTvNickName.setText(App.instance().getUserInfo().getUserName());
//        mTvDynamicCount.setText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_info_container:
                PersonalActivity.start(getContext());
                break;
            case R.id.rl_sports_achievement:

                break;
            case R.id.rl_setting:

                break;
        }
    }
}
