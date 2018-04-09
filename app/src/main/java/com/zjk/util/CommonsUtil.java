package com.zjk.util;

import android.content.Context;
import android.text.TextUtils;

import com.zjk.run_help.R;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class CommonsUtil {

    public static int getCoreNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static String getGender(Context context, int gender) {
        if (gender == 0) {
            return context.getResources().getString(R.string.male);
        } else if (gender == 1) {
            return context.getResources().getString(R.string.female);
        } else {
            return context.getResources().getString(R.string.confidentiality);
        }
    }

    public static int genderToInt(Context context, String gender) {
        if (TextUtils.isEmpty(gender)) {
            return 2;
        } else if (gender.equals(context.getResources().getString(R.string.female))) {
            return 1;
        } else {
            return 0;
        }
    }

    // 0-行走；1-跑步；2-骑行；3-轮滑
    public static String getSportsType(Context context, int type) {
        if (type == 0) {
            return context.getResources().getString(R.string.walk);
        } else if (type == 1) {
            return context.getResources().getString(R.string.run);
        } else if (type == 2) {
            return context.getResources().getString(R.string.riding);
        } else if (type == 3) {
            return context.getResources().getString(R.string.roller_skating);
        }
        return context.getResources().getString(R.string.walk);
    }
}
