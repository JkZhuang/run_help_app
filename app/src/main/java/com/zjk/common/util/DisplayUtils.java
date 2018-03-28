package com.zjk.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lianzhan on 2017/12/14.
 *
 * @author lianzhan
 */

public final class DisplayUtils {

    public static int dp2px(float dpValue) {
        float scale = ResourceUtils.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, ResourceUtils.getDisplayMetrics());
    }

    public static int px2dp(float pxValue) {
        float scale = ResourceUtils.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getDensityDpi() {
        return ResourceUtils.getDisplayMetrics().densityDpi;
    }

    public static int getScreenHeight() {
        return ResourceUtils.getDisplayMetrics().heightPixels;
    }


    public static int getScreenWidth() {
        return ResourceUtils.getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(@Nullable Context context) {
        if (context != null) { //fix部分机型切换到横屏时，锁屏再开屏计算错误问题
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        return getScreenHeight();
    }


    public static int getScreenWidth(@Nullable Context context) {
        if (context != null) {//fix部分机型切换到横屏时，锁屏再开屏计算错误问题
            return context.getResources().getDisplayMetrics().widthPixels;
        }
        return getScreenWidth();
    }

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme()
                .resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources()
                            .getDisplayMetrics());
        }
        return 0;
    }

    private volatile static boolean mHasCheckAllScreen;
    private volatile static boolean mIsAllScreenDevice;

    public static boolean isAllScreenDevice() {
        if (mHasCheckAllScreen) {
            return mIsAllScreenDevice;
        }
        mHasCheckAllScreen = true;
        mIsAllScreenDevice = false;
        // 低于 API 21的，都不会是全面屏。。。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        WindowManager windowManager = (WindowManager) AppUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            if (height / width >= 1.97f) {
                mIsAllScreenDevice = true;
            }
        }
        return mIsAllScreenDevice;
    }

    private static final int PORTRAIT = 0;
    private static final int LANDSCAPE = 1;
    @NonNull
    private volatile static Point[] mRealSizes = new Point[2];

    private static int getScreenRealWidth(@Nullable Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return getScreenWidth(context);
        }

        int orientation = context != null
                ? context.getResources().getConfiguration().orientation
                : AppUtils.getContext().getResources().getConfiguration().orientation;
        orientation = orientation == Configuration.ORIENTATION_PORTRAIT ? PORTRAIT : LANDSCAPE;

        if (mRealSizes[orientation] == null) {
            WindowManager windowManager = context != null
                    ? (WindowManager) context.getSystemService(Context.WINDOW_SERVICE)
                    : (WindowManager) AppUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                return getScreenWidth(context);
            }
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            mRealSizes[orientation] = point;
        }
        return mRealSizes[orientation].x;
    }

    private static int getScreenRealHeight(@Nullable Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return getScreenHeight(context);
        }

        int orientation = context != null
                ? context.getResources().getConfiguration().orientation
                : AppUtils.getContext().getResources().getConfiguration().orientation;
        orientation = orientation == Configuration.ORIENTATION_PORTRAIT ? PORTRAIT : LANDSCAPE;

        if (mRealSizes[orientation] == null) {
            WindowManager windowManager = context != null
                    ? (WindowManager) context.getSystemService(Context.WINDOW_SERVICE)
                    : (WindowManager) AppUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                return getScreenHeight(context);
            }
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            mRealSizes[orientation] = point;
        }
        return mRealSizes[orientation].y;
    }

    public static int getFullActivityWidth(@Nullable Context context) {
        if (!isAllScreenDevice()) {
            return getScreenWidth(context);
        }
        return getScreenRealWidth(context);
    }

    public static int getFullActivityHeight(@Nullable Context context) {
        if (!isAllScreenDevice()) {
            return getScreenHeight(context);
        }
        return getScreenRealHeight(context);
    }

    /**
     * 页面内容离屏幕最上方的距离
     * actionbar高度加上沉浸式状态下的状态栏高度
     *
     * @return
     */
    public static int getContentMarginTop(Activity activity) {
        int top = getActionBarHeight(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            top += getStatusBarHeight(activity);
        }
        return top;
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体和图标是否为深色
     */
    public static void setStatusBarFontDark(Window window, boolean dark) {
        // 小米MIUI
        if (RomUtils.isMIUI()) {
            try {
                Class clazz = window.getClass();
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {    //状态栏亮色且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {       //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 魅族FlymeUI
        if (RomUtils.isFlyme()) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // android6.0+系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }

    private static float sDensityFactor = -1.0f;

    public static float densityFactor() {
        if (sDensityFactor <= 0) {
            sDensityFactor = getDisplayMetrics().density;
        }
        return sDensityFactor;
    }

    public static float dipToPixels(float dipValue) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static float spToPt(int sp) {
        return sp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = AppUtils.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    public static int getStatusBarHeight(Activity activity) {
        return getStatusBarHeight(activity.getWindow());
    }

    public static int getStatusBarHeight(Window window) {
        Rect localRect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(localRect);
        int mStatusBarHeight = localRect.top;
        if (0 == mStatusBarHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                mStatusBarHeight = ResourceUtils.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        if (0 == mStatusBarHeight) {
            int resourceId = ResourceUtils.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                mStatusBarHeight = ResourceUtils.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return mStatusBarHeight;
    }
}
