package com.zjk.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pandengzhe on 2018/4/7.
 */

public class ToastUtil {

    public static void shortShow(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }

    public static void shortShow(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void longShow(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_LONG).show();
    }

    public static void longShow(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
