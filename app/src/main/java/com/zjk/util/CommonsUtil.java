package com.zjk.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Chronometer;
import android.widget.TextView;

import com.zjk.common.app.App;
import com.zjk.model.TrainingSuggestData;
import com.zjk.okhttp.DefList;
import com.zjk.run_help.R;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class CommonsUtil {

    public static int getCoreNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static Drawable getGenderIcon(Context context, int gender) {
        if (gender == 0) {
            return ContextCompat.getDrawable(context, R.drawable.ic_male);
        } else if (gender == 1) {
            return ContextCompat.getDrawable(context, R.drawable.ic_female);
        } else {
            return ContextCompat.getDrawable(context, R.drawable.ic_secret_gender);
        }
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

    public static String getImageUrl(String url) {
        return DefList.url + "/image/getImage?" + url;
    }

    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return context.getString(R.string.default_version);
        }
    }

    public static long getUseTime(TextView tvTime) {
        String stringTime = tvTime.getText().toString().trim();
        DebugUtil.debug("getTime", stringTime);
        String[] arrayTime = stringTime.split(":");
        long resultTime = 0;
        for (String s : arrayTime) {
            resultTime = resultTime * 60 + Long.valueOf(s);
        }
        return resultTime;
    }

    public static TrainingSuggestData getTrainingSuggestData(int type) {
        for (TrainingSuggestData data : App.instance().getUserConfig().trainingSuggestDataArrayList) {
            if (type == data.getType()) {
                return data;
            }
        }
        return null;
    }
}
