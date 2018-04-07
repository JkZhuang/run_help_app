package com.zjk.common.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class MyPageAdapter<T extends View> extends PagerAdapter {

    private Context mContext;
    private T[] mViewArray;

    public MyPageAdapter(Context context) {
        mContext = context;
    }

    public MyPageAdapter(Context context, T[] viewArray) {
        mContext = context;
        this.mViewArray = viewArray;
    }

    public void setViewArray(T[] viewArray) {
        this.mViewArray = viewArray;
    }

    @Override
    public int getCount() {
        return mViewArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewArray[position]);
        return mViewArray[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewArray[position]);
    }

}
