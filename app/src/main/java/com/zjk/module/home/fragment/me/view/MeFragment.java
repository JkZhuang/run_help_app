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
import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.BaseFragment;
import com.zjk.common.ui.ShapedImageView;
import com.zjk.module.home.fragment.me.present.IMePresenter;
import com.zjk.module.home.fragment.me.present.MePresenter;
import com.zjk.module.ranking.view.RankingActivity;
import com.zjk.module.setting.SettingActivity;
import com.zjk.module.sportsachievement.activity.view.SportsAchievementActivity;
import com.zjk.module.user.information.view.PersonalActivity;
import com.zjk.run_help.R;
import com.zjk.util.CommonsUtil;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class MeFragment extends BaseFragment<IMePresenter> implements IMeView {

    private static final String TAG = "MeFragment";

    private View mView;
    private ConstraintLayout MClInfoContainer;
    private ShapedImageView mIvHeadPhoto;
    private TextView mTvNickName;
    private TextView mTvDynamicCount;
    private RelativeLayout mRlSportsAchievement;
    private RelativeLayout mRlRankingVersion;
    private RelativeLayout mRlSetting;

    public static MeFragment newInstance(BaseActivity activity) {
        MeFragment fragment = new MeFragment();
        fragment.setActivity(activity);
        return fragment;
    }

    public MeFragment() {
        mPresenter = new MePresenter(this);
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
        mRlRankingVersion = (RelativeLayout) mView.findViewById(R.id.rl_ranking_version);
        mRlSetting = (RelativeLayout) mView.findViewById(R.id.rl_setting);
    }

    @Override
    protected void setListener() {
        MClInfoContainer.setOnClickListener(this);
        mRlSportsAchievement.setOnClickListener(this);
        mRlRankingVersion.setOnClickListener(this);
        mRlSetting.setOnClickListener(this);
    }

    @Override
    protected void init() {
        Glide.with(getContext())
                .load(CommonsUtil.getImageUrl(getUserInfo().getHeadUrl()))
                .placeholder(R.drawable.head_photo_default)
                .into(mIvHeadPhoto);
        mTvNickName.setText(getUserInfo().getUserName());
        mTvNickName.setCompoundDrawablesWithIntrinsicBounds(null, null, CommonsUtil.getGenderIcon(getContext(), getUserInfo().getGender()), null);
        mTvDynamicCount.setText(String.valueOf(App.instance().getUserConfig().dynamicCount));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_info_container:
                PersonalActivity.start(getContext());
                break;
            case R.id.rl_sports_achievement:
                SportsAchievementActivity.start(getContext());
                break;
            case R.id.rl_ranking_version:
                RankingActivity.start(getContext());
                break;
            case R.id.rl_setting:
                SettingActivity.start(getContext());
                break;
        }
    }
}
