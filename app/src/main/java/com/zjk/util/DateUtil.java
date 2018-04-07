package com.zjk.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pandengzhe on 2018/4/7.
 */

public class DateUtil {

    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(GsonUtil.TIME_FORMAT);
        return format.format(date);
    }

    public static Date stringToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(GsonUtil.TIME_FORMAT);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }
}
