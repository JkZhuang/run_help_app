package com.zjk.util;

import android.view.View;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/26
 */

public class ViewUtil {

    public static void setViewVisible(View view, int visible) {
        if (view != null && view.getVisibility() != visible) {
            view.setVisibility(visible);
        }
    }
}
