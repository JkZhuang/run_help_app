package com.zjk.common.util;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;

/**
 * Created by lianzhan on 2017/12/14.
 *
 * @author lianzhan
 */

public final class ResourceUtils {

    public static final Resources getResources() {
        return AppUtils.getContext().getResources();
    }

    public static final AssetManager getAssets() {
        return AppUtils.getContext().getAssets();
    }

    public static final String getString(int resId) {
        return AppUtils.getContext().getString(resId);
    }

    public static final DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    public static ContentResolver getContentResolver() {
        return AppUtils.getContext().getContentResolver();
    }

    public static int getColor(int id) {
        return getResources().getColor(id);
    }

    public static float getDimension(int id) {
        return getResources().getDimension(id);
    }

    public static float getDimensionPixelSize(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    public static Uri getResourceUri(int resId) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(resId) + '/' +
                getResources().getResourceTypeName(resId) + '/' +
                getResources().getResourceEntryName(resId));
    }
}
