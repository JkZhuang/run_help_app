package com.zjk.common.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by pandengzhe on 2018/3/31.
 */

public class MyFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private Context mContext;
    private T[] mFragmentArray;
    private String[] mTitles;

    public MyFragmentStatePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public MyFragmentStatePagerAdapter(Context context, FragmentManager fm, T[] fragmentArray, String[] titles) {
        super(fm);
        mContext = context;
        mFragmentArray = fragmentArray;
        mTitles = titles;
    }

    public void setFragmentArray(T[] fragmentArray) {
        mFragmentArray = fragmentArray;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentArray[position];
    }

    @Override
    public int getCount() {
        return mFragmentArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? super.getPageTitle(position) : mTitles[position];
    }
}
