package com.zjk.module.home.sports.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.zjk.common.data.DefSports;
import com.zjk.common.ui.BaseFragment;
import com.zjk.module.sports.view.SportsActivity;
import com.zjk.run_help.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class BaseSportsFragment extends BaseFragment {

    private static final String TAG = "BaseSportsFragment";

    public static final int MAX_DISTANCE = 100;

    public static final String KEY_SPORTS_TYPE = "sports_type";
    public static final String KEY_DISTANCE = "distance";

    public static final int SPORTS_TYPE_WALK = 0;
    public static final int SPORTS_TYPE_RUN = 1;
    public static final int SPORTS_TYPE_RIDE = 2;
    public static final int SPORTS_ROLLER_SKATING = 3;

    protected View mView;
    protected TextView mTvTotalKilometers;
    protected TextView mTvSportsType;
    protected TextView mTvSetTarget;
    protected TextView mTvBegin;

    protected int mSportsType;
    protected double targetDistance = 0d;
    private List<Integer> distanceList;

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
        mTvSetTarget = (TextView) mView.findViewById(R.id.tv_set_target);
        mTvBegin = (TextView) mView.findViewById(R.id.tv_begin);
    }

    @Override
    protected void setListener() {
        mTvSetTarget.setOnClickListener(this);
        mTvBegin.setOnClickListener(this);
    }

    @Override
    protected void init() {
        distanceList = new ArrayList<>();
        for (int i = 0; i < MAX_DISTANCE; i++) {
            distanceList.add(i);
        }
        switch (mSportsType) {
            case DefSports.WALK:
                mTvSportsType.setText(getContext().getString(R.string.walking));
                mTvBegin.setText(getContext().getString(R.string.start_walk));
                break;
            case DefSports.RUNNING:
                mTvSportsType.setText(getContext().getString(R.string.running));
                mTvBegin.setText(getContext().getString(R.string.start_run));
                break;
            case DefSports.RIDING:
                mTvSportsType.setText(getContext().getString(R.string.ridding));
                mTvBegin.setText(getContext().getString(R.string.start_ride));
                break;
            case DefSports.ROLLER_SKATING:
                mTvSportsType.setText(getContext().getString(R.string.roller_skating_));
                mTvBegin.setText(getContext().getString(R.string.start_skating));
                break;
            default:
                mTvSportsType.setText(getContext().getString(R.string.walking));
                mTvBegin.setText(getContext().getString(R.string.start_walk));
                break;
        }
    }

    protected void setType(int type) {
        this.mSportsType = type;
    }

    private void setTarget() {
        int defaultOption;
        if (targetDistance == 0) {
            defaultOption = 4;
        } else {
            defaultOption = (int) targetDistance;
        }
        //条件选择器
        OptionsPickerView<Integer> pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                targetDistance = distanceList.get(options1);
                ;
                mTvTotalKilometers.setText(String.format("%.2f", targetDistance));
            }
        })
                .setSelectOptions(defaultOption)
                .setCancelText(getResources().getString(R.string.cancel))//取消按钮文字
                .setSubmitText(getResources().getString(R.string.sure))//确认按钮文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setSubmitColor(getResources().getColor(R.color.colorAccent))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.colorAccent))//取消按钮文字颜色
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false) //是否显示为对话框样式
                .build();
        pvOptions.setPicker(distanceList);
        pvOptions.show();
    }

    @Override
    public void setArgs(Bundle args) {
        targetDistance = args.getDouble(BaseSportsFragment.KEY_DISTANCE, targetDistance);
        mTvTotalKilometers.setText(String.format("%.2f", targetDistance));
    }

    private void startActivity() {
        Bundle args = new Bundle();
        args.putInt(BaseSportsFragment.KEY_SPORTS_TYPE, mSportsType);
        args.putDouble(BaseSportsFragment.KEY_DISTANCE, targetDistance);
        Intent intent = new Intent(getContext(), SportsActivity.class);
        intent.putExtras(args);
        getContext().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set_target:
                setTarget();
                break;
            case R.id.tv_begin:
                startActivity();
                break;
        }
    }
}
