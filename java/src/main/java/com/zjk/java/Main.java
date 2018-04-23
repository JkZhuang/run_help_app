package com.zjk.java;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        System.out.println(isYesterday(stringToDate("2018-4-25 11:02:20")));
    }

    public static boolean isYesterday(Date date) {
        Date curDate = new Date();
        String standardDate = dateToString(curDate);
        standardDate = standardDate.split(" ")[0];
        standardDate += " 00:00:00";
        try {
            curDate = stringToDate(standardDate);
            return date.before(curDate);
        } catch (NullPointerException e) {
            return true;
        }
    }

    public static final String DATE_PATTEN = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTEN);
        return format.format(date);
    }

    public static Date stringToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTEN);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }
}
