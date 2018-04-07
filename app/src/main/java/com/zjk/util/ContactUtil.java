package com.zjk.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pandengzhe on 2018/4/7.
 */

public class ContactUtil {

    public static final int CHINA_MOBILE = 1;
    public static final int CHINA_UNICOM = 2;
    public static final int CHINA_TELECOM = 3;
    public static final int UNKNOWN = 0;

    private ContactUtil() {
        throw new IllegalAccessError("ContactUtil Utility class");
    }

    /**
     * 联系方式验证
     *
     * @param contact
     * @return
     */
    public static boolean checkContact(String contact) {
        return checkMobile(contact) || checkPhone(contact) || checkEmail(contact);
    }

    /**
     * 手机号验证
     *
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {
        String checkString = "^[1][3,4,5,7,8][0-9]{9}$";
        Pattern pattern = Pattern.compile(checkString);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 电话号码验证
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        String checkString = "^[0,8][0-9]{2,3}-[0-9]{5,10}$";
        Pattern pattern = Pattern.compile(checkString);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 验证输入的邮箱格式是否符合
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String checkString = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern pattern = Pattern.compile(checkString);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 返回运营商 需要加入权限
     *
     * @return 1 - CHINA_MOBILE - 代表中国移动
     * 2 - CHINA_UNICOM - 代表中国联通
     * 3 - CHINA_TELECOM - 代表中国电信
     * 0 - UNKNOWN - 代表未知
     */
    public static int getOperators(Context context) {
        String IMSI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimOperator();
        if (IMSI == null) {
            return UNKNOWN;
        }
        int OperatorsName = UNKNOWN;
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            OperatorsName = CHINA_MOBILE;
        } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
            OperatorsName = CHINA_UNICOM;
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
            OperatorsName = CHINA_TELECOM;
        }

        return OperatorsName;
    }
}