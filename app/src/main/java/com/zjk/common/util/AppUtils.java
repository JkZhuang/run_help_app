package com.zjk.common.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

/***
 * Description:全局对象管理
 * Creator: wangwei7@yy.com
 * Date:17-7-6
 ***/
public final class AppUtils {
    private static final String TAG = "AppUtils";

    private static Context sBaseContext;
    private static Context sAppContext;
    private static boolean sDebug;
    private static boolean sIsBackground = true;
    private static Activity sCurrentActivity;

    //for content
    public static void setBaseContext(Context context) {
        sBaseContext = context;
    }

    public static void setDebugMode(boolean isDebugMode) {
        sDebug = isDebugMode;
    }

    public static void init(Application application) {
        if (sAppContext != null) {
            throw new ExceptionInInitializerError("Environment should only init once!");
        }


        sAppContext = application;
        application.registerActivityLifecycleCallbacks(new EmptyActivityLifecycleCallbacks() {

            @Override
            protected void onResumed(Activity activity) {
                sCurrentActivity = activity;
            }

            @Override
            protected void onAppDestroyed() {
                sCurrentActivity = null;
            }

            @Override
            protected void onEnterForeground() {
                sIsBackground = false;
            }

            @Override
            protected void onEnterBackground() {
                sIsBackground = true;
            }
        });
    }

    @Nullable
    public static final Activity getCurrentActivity() {
        return sCurrentActivity;
    }

    public static final boolean isBackground() {
        return sIsBackground;
    }

    public static final Context getContext() {
        return sAppContext == null ? sBaseContext : sAppContext;
    }

    public static final boolean isDebug() {
        return sDebug;
    }

    public static <T> T getSystemService(String serviceName) {
        return (T) getContext().getSystemService(serviceName);
    }


}
