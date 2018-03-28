package com.zjk.common.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.HashSet;
import java.util.Set;

/***
 * Description:
 * Creator: wangwei7@yy.com
 * Date:2017-09-14 06:15:20 PM
 ***/
public class EmptyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private Set<Activity> mFlags = new HashSet<>();
    private boolean mHasEnterBackground = true;

    @Override
    public final void onActivityStarted(Activity activity) {
        onStarted(activity);

        mFlags.add(activity);
        if (mHasEnterBackground) {
            mHasEnterBackground = false;
            onEnterForeground();
        }
    }

    @Override
    public final void onActivityPaused(Activity activity) {
        onPaused(activity);
    }

    @Override
    public final void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        onSaveInstanceState(activity, outState);
    }

    @Override
    public final void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        onCreated(activity, savedInstanceState);

    }

    @Override
    public final void onActivityResumed(Activity activity) {
        onResumed(activity);
    }


    @Override
    public final void onActivityStopped(Activity activity) {
        onStopped(activity);

        mFlags.remove(activity);
        if (mFlags.size() == 0) {
            mHasEnterBackground = true;
            onEnterBackground();
        }
    }


    @Override
    public final void onActivityDestroyed(Activity activity) {
        onDestroyed(activity);

        if (mFlags.size() == 0) {
            onAppDestroyed();
        }

    }

    protected void onCreated(Activity activity, Bundle savedInstanceState) {
    }

    protected void onResumed(Activity activity) {
    }

    protected void onStopped(Activity activity) {
    }

    protected void onStarted(Activity activity) {
    }

    protected void onPaused(Activity activity) {
    }

    protected void onSaveInstanceState(Activity activity, Bundle outState) {
    }

    protected void onDestroyed(Activity activity) {
    }


    protected void onEnterForeground() {

    }

    protected void onEnterBackground() {

    }

    protected void onAppDestroyed() {

    }

}
