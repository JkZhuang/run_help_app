package com.zjk.common.util;

import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created on 2017/12/23
 * Description:
 *
 * @author zhanglinwei(G7901)
 */
public class RomUtils {

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUIv6() {
        return isMIUI() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isMIUI() {
        final BuildProperties prop = BuildProperties.getDefault();
        return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
    }

    public static boolean isMIUIV9() {
        final BuildProperties prop = BuildProperties.getDefault();
        String versionName = prop.getProperty(KEY_MIUI_VERSION_NAME, null);
        if (TextUtils.equals(versionName, "V9")) {
            return true;
        }
        return false;
    }

    public static boolean isFlyme4() {
        return isMeizuDevice() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isMeizuDevice() {
        return Build.MANUFACTURER.equalsIgnoreCase("Meizu");
    }

    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    public static boolean isOPPODevice() {
        String manufacturer = Build.MANUFACTURER;
        return !TextUtils.isEmpty(manufacturer) && "OPPO".equalsIgnoreCase(manufacturer);
    }

    public static boolean isVivoDevice() {
        String manufacturer = Build.MANUFACTURER;
        return !TextUtils.isEmpty(manufacturer) && "vivo".equalsIgnoreCase(manufacturer);
    }
}
