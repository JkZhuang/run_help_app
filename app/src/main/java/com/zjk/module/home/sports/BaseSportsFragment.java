package com.zjk.module.home.sports;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjk.common.ui.BaseFragment;
import com.zjk.run_help.R;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class BaseSportsFragment extends BaseFragment {

    private static final String TAG = "BaseSportsFragment";

    public static final int SPORTS_TYPE_WALK = 0;
    public static final int SPORTS_TYPE_RUN = 1;
    public static final int SPORTS_TYPE_RIDE = 2;

    private View mView;
    private TextView mTvTotalKilometers;
    private TextView mTvSportsType;
    private TextView mTvBegin;

    private int mSportsTyle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sports_common, container, false);
        findWidget();
        setListener();
        init();
        return mView;
    }

    @Override
    protected void findWidget() {
        mTvTotalKilometers = (TextView) mView.findViewById(R.id.tv_total_kilometers);
        mTvSportsType = (TextView) mView.findViewById(R.id.tv_sports_type);
        mTvBegin = (TextView) mView.findViewById(R.id.tv_begin);
    }

    @Override
    protected void setListener() {
        mTvBegin.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_begin:

                break;
        }
    }
}
