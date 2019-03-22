package com.zjk.common.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Utils {

    private static final String TAG = "Utils";

    /**
     * 判断当前手机是否有ROOT权限
     *
     * @return
     */
    public static boolean isRoot() {
        boolean isRoot = false;
        try {
            if ((new File("/system/bin/su").exists()) || (new File("/system/xbin/su").exists())) {
                isRoot = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRoot;
    }

    /**
     * 获取注册网络的MCC+MNC码
     */
    public static String getNetworkOperator(Context context) {
        String networkOperator = "";

        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            networkOperator = tm.getNetworkOperator();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return networkOperator;
    }

    /**
     * 获取SIM卡的MCC+MNC码，统计协议使用可能需要的是注册网络的getNetworkOperator
     */
    public static String getSimOperator(Context context) {
        String simOperator = "";

        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperator = tm.getSimOperator();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return simOperator;
    }

    //true: simOperator false: netWorkOperator
    public static int getMcc(Context context, String mcc_mnc) {
        if (context == null)
            return 0;

        if (null != mcc_mnc && mcc_mnc.length() >= 3) {
            StringBuilder mcc = new StringBuilder();
            mcc.append(mcc_mnc, 0, 3);
            try {
                return Integer.parseInt(mcc.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        return 0;
    }

    //true: simOperator false: netWorkOperator
    public static int getMnc(Context context, String mcc_mnc) {
        if (context == null)
            return 0;

        if (null != mcc_mnc && mcc_mnc.length() >= 4) {
            StringBuilder mcc = new StringBuilder();
            mcc.append(mcc_mnc.substring(3));
            try {
                return Integer.parseInt(mcc.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        return 0;
    }

    public static void putJsonSafely(JSONObject object, String key, Object obj) {
        try {
            object.put(key, obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final int OPERATOR_UNKNOWN = 0;
    public static final int OPERATOR_CM = 1;
    public static final int OPERATOR_CU = 2;
    public static final int OPERATOR_CT = 3;

    public static int getNetworkOperatorType(Context context) {
        String operator = getNetworkOperator(context);
        if (TextUtils.isEmpty(operator)) {
            return OPERATOR_UNKNOWN;
        } else if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
            return OPERATOR_CM;
        } else if (operator.equals("46001") || operator.equals("46006")) {
            return OPERATOR_CU;
        } else if (operator.equals("46003") || operator.equals("46005") || operator.equals("46011") || operator.equals("46099")) {
            return OPERATOR_CT;
        } else {
            return OPERATOR_UNKNOWN;
        }
    }

    public static String md5(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        try {
            java.security.MessageDigest md5 = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] digest = md5.digest(bytes);
            sb.append(bytesToHexString(digest));
        } catch (Exception e) {
            Log.e(TAG, "md5", e);
        }
        return sb.toString();
    }

    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        return md5(str.getBytes());
    }

    public static String fileMd5(String filePath) {
        File file = new File(filePath);
        return fileMd5(file);
    }

    public static String fileMd5(File file) {
        if (file == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int readCount = 0;
            java.security.MessageDigest md5 = java.security.MessageDigest
                    .getInstance("MD5");
            while ((readCount = in.read(buffer)) != -1) {
                md5.update(buffer, 0, readCount);
            }
            sb.append(bytesToHexString(md5.digest()));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "fileMd5", e);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "fileMd5", e);
        } catch (IOException e) {
            Log.e(TAG, "fileMd5", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int val = b & 0xff;
            if (val < 0x10) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String hex) {
        final byte[] encodingTable = {(byte) '0', (byte) '1', (byte) '2',
                (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
                (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c',
                (byte) 'd', (byte) 'e', (byte) 'f'};
        final byte[] decodingTable = new byte[128];
        for (int i = 0; i < encodingTable.length; i++) {
            decodingTable[encodingTable[i]] = (byte) i;
        }
        decodingTable['A'] = decodingTable['a'];
        decodingTable['B'] = decodingTable['b'];
        decodingTable['C'] = decodingTable['c'];
        decodingTable['D'] = decodingTable['d'];
        decodingTable['E'] = decodingTable['e'];
        decodingTable['F'] = decodingTable['f'];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte b1, b2;
        int end = hex.length();
        while (end > 0) {
            if (!isSpace(hex.charAt(end - 1))) {
                break;
            }
            end--;
        }
        int i = 0;
        while (i < end) {
            while (i < end && isSpace(hex.charAt(i))) {
                i++;
            }
            b1 = decodingTable[hex.charAt(i++)];
            while (i < end && isSpace(hex.charAt(i))) {
                i++;
            }
            b2 = decodingTable[hex.charAt(i++)];
            out.write((b1 << 4) | b2);
        }
        return out.toByteArray();
    }

    public static boolean isSpace(char c) {
        return (c == '\n' || c == '\r' || c == '\t' || c == ' ');
    }

    public static String getIpString(int ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip & 0xff);
        sb.append(".");
        sb.append(ip >>> 8 & 0xff);
        sb.append(".");
        sb.append(ip >>> 16 & 0xff);
        sb.append(".");
        sb.append(ip >>> 24 & 0xff);
        return sb.toString();
    }

    public static byte[] getIpArray(int ip) {
        byte[] ipArray = new byte[4];
        ipArray[0] = (byte) (ip & 0xff);
        ipArray[1] = (byte) (ip >>> 8 & 0xff);
        ipArray[2] = (byte) (ip >>> 16 & 0xff);
        ipArray[3] = (byte) (ip >>> 24 & 0xff);
        return ipArray;
    }

    public static long getIpV6() {
        //// TODO: 16/5/4 没有找到好的方法直接获得ipv6地址
        return 0;
    }

    public static String getIpV6String() {
        try {
            List<NetworkInterface> networkList = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface network : networkList) {
                if (!network.isLoopback()) {
                    List<InetAddress> addrList = Collections.list(network.getInetAddresses());
                    for (InetAddress addr : addrList) {
                        String addrStr = addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final HashSet<Integer> MODE_SET_4G = new HashSet<Integer>();
    public static final HashSet<Integer> MODE_SET_3G = new HashSet<Integer>();
    public static final HashSet<Integer> MODE_SET_2G = new HashSet<Integer>();

    static {
        //for 2G and 2.5G
        MODE_SET_2G.add(TelephonyManager.NETWORK_TYPE_GPRS); // ~ 100 kbps
        MODE_SET_2G.add(TelephonyManager.NETWORK_TYPE_EDGE); // ~ 50-100 kbps
        MODE_SET_2G.add(TelephonyManager.NETWORK_TYPE_CDMA); // ~ 14-64 kbps
        MODE_SET_2G.add(TelephonyManager.NETWORK_TYPE_1xRTT);// ~ 50-100 kbps
        MODE_SET_2G.add(TelephonyManager.NETWORK_TYPE_IDEN); // ~ 25 kbps

        //for 3G or 4G
        MODE_SET_3G.add(TelephonyManager.NETWORK_TYPE_UMTS);  // ~ 400-7000 kbps
        MODE_SET_3G.add(TelephonyManager.NETWORK_TYPE_EVDO_0);// ~ 400-1000 kbps
        MODE_SET_3G.add(TelephonyManager.NETWORK_TYPE_EVDO_A);// ~ 600-1400 kbps
        MODE_SET_3G.add(TelephonyManager.NETWORK_TYPE_HSDPA); // ~ 2-14 Mbps
        MODE_SET_3G.add(TelephonyManager.NETWORK_TYPE_HSUPA); // ~ 1-23 Mbps
        MODE_SET_3G.add(TelephonyManager.NETWORK_TYPE_HSPA);  // ~ 700-1700 kbps
        MODE_SET_3G.add(TelephonyManager.NETWORK_TYPE_EVDO_B);// ~ 5 Mbps
        MODE_SET_3G.add(TelephonyManager.NETWORK_TYPE_EHRPD); // ~ 1-2 Mbps
        MODE_SET_3G.add(TelephonyManager.NETWORK_TYPE_HSPAP); // ~ 10-20 Mbps

        MODE_SET_4G.add(TelephonyManager.NETWORK_TYPE_LTE);   // ~ 10+ Mbps
    }

    public static class NetworkType {
        public static final String Unknown = "";
        public static final String Wifi = ",w";
        public static final String Mobile4G = ",4";
        public static final String Mobile3G = ",3";
        public static final String Mobile2G = ",2";
    }

    public static String getNetworkType(Context context) {
        String networkType = NetworkType.Unknown;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        try {
            netInfo = cm.getActiveNetworkInfo();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (netInfo != null) {
            int type = netInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                networkType = NetworkType.Wifi;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                int subType = netInfo.getSubtype();
                if (MODE_SET_2G.contains(subType)) {
                    networkType = NetworkType.Mobile2G;
                } else if (MODE_SET_3G.contains(subType)) {
                    networkType = NetworkType.Mobile3G;
                } else {
                    //unknown mobile connection type, consider as 4G
                    Log.w(TAG, "[getNetworkType]unknown mobile subtype:" + subType + ", consider as 4G.");
                    networkType = NetworkType.Mobile4G;
                }
            }
        }
        return networkType;
    }

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager conMan = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan == null) {
            return false;
        }

        NetworkInfo mobileInfo = null;
        try {
            mobileInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (mobileInfo != null && mobileInfo.isConnectedOrConnecting()) {
            return true;
        }

        NetworkInfo wifiInfo = null;
        try {
            wifiInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (wifiInfo != null && wifiInfo.isConnectedOrConnecting()) {
            return true;
        }

        NetworkInfo activeInfo = null;
        try {
            activeInfo = conMan.getActiveNetworkInfo();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (activeInfo != null && activeInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }

    public static final int NET_UNAVAILABLE = 0;
    public static final int NET_WIFI = 1;
    public static final int NET_2G = 2;
    public static final int NET_3G = 3;
    public static final int NET_UNKNOWN_TYPE = 5;

    public static boolean externalStorageExist() {
        boolean ret = false;
        ret = Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED);
        return ret;
    }

    public static void openNetworkSetting(Context context) {
        Intent intent;
        if (Build.VERSION.SDK_INT > 10) {
            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);
    }

    public static byte[] intArrayToByteArray(int[] array) {
        if (array == null) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(array.length * 4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.asIntBuffer().put(array);
        return buffer.array();
    }

    public static int[] byteArrayToIntArray(byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length % 4 != 0) {
            throw new IllegalArgumentException("byte array length should be a multiple of 4.");
        }
        int[] result = new int[array.length / 4];
        ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer().get(result);
        return result;
    }

    public static final int[] TEA_KEYS = new int[]{0x3d7d4276, 0x6e247752, 0x752d3622, 0x595d443a};

    /**
     * Tiny Encryption Algorithm encrypt delta.
     */
    private static final int TEA_DELTA = 0x9E3779B9;

    /**
     * Tiny Encryption Algorithm decrypt sum.
     */
    private static final int TEA_SUM = 0xC6EF3720;

    /**
     * Tiny Encryption Algorithm encrypt.
     *
     * @param plainText
     * @param key
     */
    public static int[] teaEncrypt(int[] plainText, int[] key) {
        if (plainText.length % 2 != 0) {
            throw new IllegalArgumentException("value length should be even.");
        }
        if (key.length != 4) {
            throw new IllegalArgumentException("key length should be 4.");
        }
        int[] cipherText = new int[plainText.length];
        int k0 = key[0], k1 = key[1], k2 = key[2], k3 = key[3];
        int v0, v1, sum;
        for (int i = 0; i < plainText.length; i += 2) {
            v0 = plainText[i];
            v1 = plainText[i + 1];
            sum = 0;
            for (int j = 32; j > 0; j--) {
                sum += TEA_DELTA;
                v0 += ((v1 << 4) + k0) ^ (v1 + sum) ^ ((v1 >>> 5) + k1);
                v1 += ((v0 << 4) + k2) ^ (v0 + sum) ^ ((v0 >>> 5) + k3);
            }
            cipherText[i] = v0;
            cipherText[i + 1] = v1;
        }
        return cipherText;
    }

    /**
     * Tiny Encryption Algorithm decrypt.
     *
     * @param cipherText
     * @param key
     */
    public static int[] teaDecrypt(int[] cipherText, int[] key) {
        if (cipherText.length % 2 != 0) {
            throw new IllegalArgumentException("value length should be even.");
        }
        if (key.length != 4) {
            throw new IllegalArgumentException("key length should be 4.");
        }
        int[] plainText = new int[cipherText.length];
        int k0 = key[0], k1 = key[1], k2 = key[2], k3 = key[3];
        int v0, v1, sum;
        for (int i = 0; i < cipherText.length; i += 2) {
            v0 = cipherText[i];
            v1 = cipherText[i + 1];
            sum = TEA_SUM;
            for (int j = 32; j > 0; j--) {
                v1 -= ((v0 << 4) + k2) ^ (v0 + sum) ^ ((v0 >>> 5) + k3);
                v0 -= ((v1 << 4) + k0) ^ (v1 + sum) ^ ((v1 >>> 5) + k1);
                sum -= TEA_DELTA;
            }
            plainText[i] = v0;
            plainText[i + 1] = v1;
        }
        return plainText;
    }

    public static boolean isUIProcess(String processName) {
        return processName == null || !processName.contains(":");
    }

    public static boolean isServiceProcess(String processName) {
        return processName != null && processName.endsWith(":service");
    }

    public static boolean isWebProcess(String processName) {
        return processName != null && processName.contains("web");
    }

    public static boolean isUIInForeground(Context context) {
        try {
            // 当前前端应用
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
            String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();

            if (TextUtils.equals(context.getPackageName(), foregroundTaskPackageName)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.w(TAG, "isUIInForeground throw exception", e);
            return false;
        }
    }

    public static Locale getLocale(Context context) {
        Resources resources = null;
        Locale locale = null;

        if (context == null) {
            return Locale.US;
        }

        resources = context.getResources();

        if (resources == null) {
            return Locale.US;
        }

        locale = resources.getConfiguration().locale;

        if (locale != null) {
            return locale;
        } else {
            return Locale.US;
        }
    }

    /**
     * Returns the ISO country code equivalent of the current registered operator's MCC (Mobile Country Code).
     *
     * @return 接入网络的国家码
     */
    public static String getNetWorkCountryCode(Context context) {
        if (context == null) return null;
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telMgr != null) {
            String countryCode = telMgr.getNetworkCountryIso();
            if (!TextUtils.isEmpty(countryCode)) {
                return countryCode;
            }
        }
        return null;
    }

    /**
     * Returns the ISO country code equivalent for the SIM provider's country code
     *
     * @return SIM的国家码
     */
    public static String getSIMCountryCode(Context context) {
        if (context == null) return null;
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telMgr != null) {
            String countryCode = telMgr.getSimCountryIso();
            if (!TextUtils.isEmpty(countryCode)) {
                return countryCode;
            }
        }
        return null;
    }

    /**
     * @param context
     * @return 返回手机系统的语言码
     */
    public static String getLanguageCode(Context context) {
        Resources resources = null;
        Locale locale = null;

        if (context == null) {
            return Locale.US.getLanguage();
        }

        resources = context.getResources();

        if (resources == null) {
            return Locale.US.getLanguage();
        }

        locale = resources.getConfiguration().locale;

        if (locale != null) {
            return locale.getLanguage();
        } else {
            return Locale.US.getLanguage();
        }
    }


    /**
     * 获取系统国家码，跟系统语言挂钩，切换语言
     *
     * @return
     */
    public static String getCountryCode(Context context) {
        Resources resources = null;
        Locale locale = null;

        if (context == null) {
            return Locale.US.getCountry();
        }

        resources = context.getResources();

        if (resources == null) {
            return Locale.US.getCountry();
        }

        locale = resources.getConfiguration().locale;

        if (locale != null) {
            return locale.getCountry();
        } else {
            return Locale.US.getCountry();
        }

    }

    /**
     * 判断是否是俄语
     *
     * @param context
     * @return
     */
    public static boolean isLanguageRU(Context context) {
        if (getLanguageCode(context).equalsIgnoreCase("ru"))
            return true;
        return false;
    }

    public static String getUidPlatformUuid(long billId) {
        if (billId == 0) {
            return "";
        }
        BigDecimal unsingedLongDecimal;
        if (billId >= 0) {
            unsingedLongDecimal = new BigDecimal(billId);
        } else {
            long lowValue = billId & 0x7FFFFFFFFFFFFFFFL;
            unsingedLongDecimal = (BigDecimal.valueOf(lowValue).add(BigDecimal.valueOf(Long.MAX_VALUE)).add(BigDecimal.valueOf(1)));
        }
        return unsingedLongDecimal.toString();
    }

    public static <T> T cast(Object obj, Class<T> type) {
        if (type.isInstance(obj) == false) {
            return null;
        }
        return type.cast(obj);
    }

    public static int[] integerListToIntArray(Collection<Integer> arrays) {
        if (arrays == null) {
            return null;
        }

        int size = arrays.size();
        int[] results = new int[size];
        int index = 0;
        Iterator<Integer> iterator = arrays.iterator();
        while (iterator.hasNext()) {
            results[index] = iterator.next();
            index++;
        }
        return results;
    }

    public static List<Integer> intArrayToIntegerList(int[] array) {
        if (array == null) {
            return null;
        }
        List<Integer> arrayList = new ArrayList<>();
        for (int a : array) {
            arrayList.add(a);
        }
        return arrayList;
    }

    public static List<String> stringArrayToList(String[] array) {
        if (array == null) {
            return null;
        }
        List<String> arrayList = new ArrayList<>();
        for (String a : array) {
            arrayList.add(a);
        }
        return arrayList;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }

    public static String getApplicationWorkspaceInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("SOURCE_PATH=");
            sb.append(context.getApplicationInfo().sourceDir);
            sb.append(" :");
            sb.append(new File(context.getApplicationInfo().sourceDir).length());
            sb.append('\n');
            sb.append("FILES_PATH=");
            sb.append(context.getFilesDir().getAbsolutePath());
            sb.append('\n');
            //			sb.append("FILES_LIST=");
            //			getFilesList(sb, context.getFilesDir().list());
            //			sb.append('\n');
            sb.append("LIB_PATH=");
            sb.append(context.getApplicationInfo().nativeLibraryDir);
            sb.append('\n');
            sb.append("LIB_LIST=");
            File libFile = new File(context.getApplicationInfo().nativeLibraryDir);
            getFilesList(sb, libFile, libFile.list());
            sb.append('\n');
            sb.append("LIB_EXT_LIST=");
            File libExtFile = new File(context.getFilesDir().getAbsolutePath().replace("files", "app_lib_ext"));
            getFilesList(sb, libExtFile, libExtFile.list());
            sb.append('\n');
            sb.append("libs.7z=");
            getFilesList(sb, context.getCacheDir(), context.getCacheDir().list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.startsWith("libs.7z");
                }
            }));
            sb.append('\n');
        } catch (Exception e) {
        }

        return sb.toString();
    }

    private static void getFilesList(StringBuilder sb, File folder, String[] files) {
        if (files == null) {
            return;
        }

        for (String file : files) {
            sb.append(file);
            sb.append(' ');
            sb.append(new File(folder, file).length());
            sb.append(' ');
        }
    }

    public static int covertDp2Px(Context context, float dp) {
        Resources r = context.getResources();
        float convertedDp = dp;

        if (r != null) {
            convertedDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        }

        return (int) convertedDp;
    }

    public static void dumpStderr(final Process process, final long timeout) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream stderr = process.getErrorStream();
                    byte[] dummy = new byte[4096];
                    while (stderr.read(dummy) >= 0) {
                        // do nothing
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    java.lang.Thread.sleep(timeout);
                    process.destroy();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }).start();
    }

    public static byte[] readFileLocked(File file) {
        File backupFile = makeBackupFile(file);
        if (backupFile.exists()) {
            file.delete();
            backupFile.renameTo(file);
        }
        if (!file.exists()) {
            return null;
        }
        FileInputStream fileInputStream = null;
        try {
            int fileLength = (int) file.length();
            if (fileLength != 0) {
                fileInputStream = new FileInputStream(file);
                byte data[] = new byte[fileLength];
                if (fileInputStream.read(data) == fileLength) {
                    return data;
                }
            }

            Log.e(TAG, "readFileLocked length=" + fileLength + ", fileName=" + file.getName());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            byte[] data = out.toByteArray();
            Log.e(TAG, "readFileLocked data=" + data.length + ", fileName=" + file.getName());
            if (data.length == 0) {
                return null;
            }

            return data;
        } catch (Exception e) {
            Log.e(TAG, "read file " + file.getPath() + " failed", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    Log.w(TAG, "close file " + file.getPath() + " failed", e);
                }
            }
        }
        return null;
    }

    public static void writeFileLocked(File file, byte[] data) {
        File backupFile = makeBackupFile(file);
        if (file.exists()) {
            if (!backupFile.exists()) {
                if (!file.renameTo(backupFile)) {
                    Log.e(TAG, "rename locked file failed: " + file.getName());
                }
            } else {
                if (!file.delete()) {
                    Log.e(TAG, "delete locked file failed: " + file.getName());
                }
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.getFD().sync();
            fileOutputStream.close();
            if (backupFile.exists() && !backupFile.delete()) {
                Log.e(TAG, "delete backup file failed: " + backupFile.getName());
            }
        } catch (Exception e) {
            Log.e(TAG, "write file " + file.getPath() + " failed", e);
            if (file.exists()) {
                if (!file.delete()) {
                    Log.e(TAG, "delete locked file with exception failed: " + file.getName());
                }
            }
        }
    }

    public static void clearFileLocked(File file) {
        File backupFile = makeBackupFile(file);
        if (file.exists()) {
            if (!file.delete()) {
                Log.e(TAG, "delete file when clear file failed: " + file.getName());
            }
        }
        if (backupFile.exists()) {
            if (!backupFile.delete()) {
                Log.e(TAG, "delete backup file when clear file failed: " + backupFile.getName());
            }
        }
    }

    private static File makeBackupFile(File originalFile) {
        return new File(originalFile.getPath() + ".bak");
    }

    private static final SimpleDateFormat DATE_FORMAT_yyMMdd_HHmmss = new SimpleDateFormat("yyMMdd_HHmmss", Locale.ENGLISH);
    private static final SimpleDateFormat CLOCK_FORMAT_HHmmss = new SimpleDateFormat("HHmmss", Locale.ENGLISH);

    public static String getFormatedTime(Date date) {
        return DATE_FORMAT_yyMMdd_HHmmss.format(date);
    }

    public static String getFormatedClock(Date date) {
        return CLOCK_FORMAT_HHmmss.format(date);
    }

    /***
     * Trying to fix: java.lang.SecurityException: Unable to find app for caller
     * android.app.ApplicationThreadProxy@42cc0b90 (pid=1036) when starting service Intent
     * { act=com.yy.yymeet.ACTION_RECOMMEND_COMMON_CONTACT
     *   cmp=com.yy.yymeet/com.yy.iheima.fgservice.FgWorkService }
     * @param context
     * @param it
     */
    public static void startServiceQuietly(Context context, Intent it) {
        try {
            context.startService(it);
        } catch (Exception ex) {
            Log.e(TAG, "startServiceQuietly failed", ex);
        }
    }

    public static boolean isMainThread() {
        return (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId());
    }

    //TODO zhaobo 去掉模拟器判断，固定返回false
    public static boolean isEmulator(Context context) {
        return false;
    }

    public static Bitmap textAsBitmap(String text, TextPaint textPaint) {
        if (TextUtils.isEmpty(text)) {
            Log.e(TAG, "textAsBitmap error, text is empty.");
            return null;
        }

        if (textPaint == null) {
            Log.e(TAG, "textAsBitmap error, textPaint is null.");
            return null;
        }

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float width = textPaint.measureText(text);
        float height = fontMetrics.bottom - fontMetrics.top;
        Log.i(TAG, String.format("textAsBitmap, width=%f, height=%f", width, height));

        Bitmap bitmap = Bitmap.createBitmap((int) Math.ceil(width), (int) Math.ceil(height), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 0, Math.abs(fontMetrics.top), textPaint);
        canvas.save();

        Log.i(TAG, "textAsBitmap, " + String.format("bitmap[width=%d, height=%d]", bitmap.getWidth(), bitmap.getHeight()));
        return bitmap;
    }

    public static Drawable textAsDrawable(String text, TextPaint textPaint) {
        Bitmap bitmap = textAsBitmap(text, textPaint);

        Drawable drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width > 0 ? width : 0, height > 0 ? height : 0);
        return drawable;
    }

    public static boolean collectionEquals(List<Integer> lhs, int[] rhs) {
        if (lhs == null || rhs == null) {
            if (lhs != null) {
                return lhs.isEmpty();
            } else if (rhs != null) {
                return rhs.length == 0;
            }
            return true;
        }

        if (lhs.size() != rhs.length) {
            return false;
        }

        Collections.sort(lhs);
        Arrays.sort(rhs);

        int pos = 0;
        for (int val : lhs) {
            if (val != rhs[pos]) {
                return false;
            }
            pos++;
        }
        return true;
    }

    public static <T> List<List<T>> splitLargeList(Set<T> wholeSet, final int kCapacity) {
        List<List<T>> collections = new ArrayList<List<T>>();

        final int kWholeSize = wholeSet.size();

        ArrayList<T> valueList = new ArrayList<T>();
        int curSize = 0;
        for (T val : wholeSet) {
            valueList.add(val);
            curSize++;

            if (curSize == kWholeSize ||
                    (curSize != 0 && curSize % kCapacity == 0)) {
                collections.add(valueList);
                valueList = new ArrayList<T>();
            }
        }

        return collections;
    }

    /**
     * 我的版本是否低于当前房间要求的最低版本（是否需要更新）
     *
     * @param myClientVersion  客户端的版本 格式：3.0.1 或1.2.0-SNAPSHOT(内测)
     * @param minClientVersion 某个房间需要的最低版本 格式：3.0.1
     * @return true if it's out of date
     */
    public static boolean isMyClientVersionOutOfDate(String myClientVersion, String minClientVersion) {
        if (TextUtils.isEmpty(myClientVersion) || TextUtils.isEmpty(minClientVersion)) {
            return false;
        }
        Log.i(TAG, "myClientVersion:" + myClientVersion + " minClientVersion:" + minClientVersion);

        String tmp[] = myClientVersion.split("-");
        String[] myVersion = tmp[0].split("\\.");

        String tmp2[] = minClientVersion.split("-");
        String[] minVersion = tmp2[0].split("\\.");
        for (int i = 0; i < myVersion.length && i < minVersion.length; i++) {
            try {
                if (Integer.parseInt(myVersion[i]) > Integer.parseInt(minVersion[i])) {
                    return false;
                }
                if (Integer.parseInt(myVersion[i]) < Integer.parseInt(minVersion[i])) {
                    return true;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean haveEmojiText(final String content) {
        if (TextUtils.isEmpty(content)) return false;
        final int codePointCount = content.codePointCount(0, content.length());
        int codePoint;
        Character.UnicodeBlock ub;
        for (int i = 0; i < codePointCount; i++) {
            codePoint = content.codePointAt(i);

            ub = Character.UnicodeBlock.of(codePoint);
            if (ub == Character.UnicodeBlock.CONTROL_PICTURES
                    || ub == Character.UnicodeBlock.BASIC_LATIN
                    || ub == Character.UnicodeBlock.LATIN_1_SUPPLEMENT
                    || ub == Character.UnicodeBlock.LATIN_EXTENDED_A
                    || ub == Character.UnicodeBlock.LATIN_EXTENDED_B
                    || ub == Character.UnicodeBlock.LATIN_EXTENDED_ADDITIONAL
                    || ub == Character.UnicodeBlock.GREEK
                    || ub == Character.UnicodeBlock.GREEK_EXTENDED
                    || ub == Character.UnicodeBlock.CYRILLIC
                    || ub == Character.UnicodeBlock.CYRILLIC_SUPPLEMENTARY
                    || ub == Character.UnicodeBlock.ARMENIAN
                    || ub == Character.UnicodeBlock.ARABIC
                    || ub == Character.UnicodeBlock.ARABIC_PRESENTATION_FORMS_A
                    || ub == Character.UnicodeBlock.ARABIC_PRESENTATION_FORMS_B
                    || ub == Character.UnicodeBlock.THAI
                    || ub == Character.UnicodeBlock.MONGOLIAN
                    || ub == Character.UnicodeBlock.LAO)
                continue;
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
                continue;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                /* Unicode 6.0 */
                if (ub == Character.UnicodeBlock.EMOTICONS
                        || ub == Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS
                        || ub == Character.UnicodeBlock.TRANSPORT_AND_MAP_SYMBOLS) return true;
            }

			/*
            * @see http://apps.timwhitlock.info/emoji/tables/unicode
			* 因码点分布不均，会误判或漏判一部分字符
			*/
			/* Emoticons */
            if (0x1F600 <= codePoint && codePoint <= 0x1F64F) {
                return true;
            }
			/* Transport & map symbols */
            else if (0x1F680 <= codePoint && codePoint <= 0x1F6FF) {
                return true;
            }
			/* Enclosed characters */
            else if (0x24C2 == codePoint) {
                return true;
            } else if (0x1F170 <= codePoint && codePoint <= 0x1F251) {
                return true;
            }
			/* symbols & pictographs */
            else if (0x00A9 == codePoint || 0x00AE == codePoint || 0x203C == codePoint || 0x2049 == codePoint
                    || 0x2122 == codePoint || 0x2139 == codePoint) {
                return true;
            } else if (0x1F300 <= codePoint && codePoint <= 0x1F5FF) {
                return true;
            }
			/* Dingbats */
            else if (ub == Character.UnicodeBlock.DINGBATS) {
                if (0x2702 == codePoint || 0x2705 == codePoint || 0x2714 == codePoint || 0x2716 == codePoint
                        || 0x2728 == codePoint || 0x2733 == codePoint || 0x2734 == codePoint
                        || 0x2744 == codePoint || 0x2747 == codePoint || 0x274C == codePoint
                        || 0x274E == codePoint || 0x2764 == codePoint) {
                    return true;
                } else if (0x2708 <= codePoint && codePoint <= 0x270F) {
                    return true;
                } else if (0x2753 <= codePoint && codePoint <= 0x2755) {
                    return true;
                } else if (0x2795 <= codePoint && codePoint <= 0x2797) {
                    return true;
                }
            }
			/* Other */
            else if (0x2600 <= codePoint && codePoint <= 0x2B55) {
                return true;
            }
        }
        return false;
    }

    /**
     * 当前View 是否在当前点击坐标内
     *
     * @param view view
     * @param rawX float
     * @param rawY float
     * @return boolean
     */
    public static boolean isViewOnTouchXY(View view, float rawX, float rawY) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        RectF rectF = new RectF(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight());
        return rectF.contains(rawX, rawY);
    }

    public static byte stringToByte(String byteStr) {
        try {
            return Byte.parseByte(byteStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Byte.MIN_VALUE;
    }

    public static int stringToInt(String integerStr) {
        return stringToInt(integerStr, Integer.MIN_VALUE);
    }

    public static int stringToInt(String integerStr, int def) {
        try {
            return Integer.parseInt(integerStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return def;
    }

    public static long stringToLong(String longStr) {
        return stringToLong(longStr, Long.MIN_VALUE);
    }

    public static long stringToLong(String longStr, long def) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return def;
    }

    public static short stringToShort(String shortStr) {
        return stringToShort(shortStr, Short.MIN_VALUE);
    }

    public static short stringToShort(String shortStr, short def) {
        try {
            Short.parseShort(shortStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return def;
    }

    public static float stringToFloat(String floatStr) {
        return stringToFloat(floatStr, Float.MIN_VALUE);
    }

    public static float stringToFloat(String floatStr, float def) {
        try {
            return Float.parseFloat(floatStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return def;
    }

    public static Double stringToDouble(String doubleStr) {
        return stringToDouble(doubleStr, Double.MIN_VALUE);
    }

    public static Double stringToDouble(String doubleStr, double def) {
        try {
            return Double.parseDouble(doubleStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return def;
    }
}
