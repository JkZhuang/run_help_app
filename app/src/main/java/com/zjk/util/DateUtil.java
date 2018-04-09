package com.zjk.util;

import android.text.TextUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pandengzhe on 2018/4/7.
 */

public class DateUtil {

    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(GsonUtil.DATE_PATTEN);
        return format.format(date);
    }

    public static Date stringToDate(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            strDate = dateToString(new Date());
        }
        SimpleDateFormat formatter = new SimpleDateFormat(GsonUtil.DATE_PATTEN);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }
}
