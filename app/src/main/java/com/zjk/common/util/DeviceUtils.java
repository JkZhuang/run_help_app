package com.zjk.common.util;


import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.zjk.run_help.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/***
 * Description:device info utils
 * Creator: wangwei7@bigo.sg
 * Date:2017-10-26 02:42:20 PM
 ***/
public class DeviceUtils {
    public static final int DEVICE_INFO_UNKNOWN = -1;
    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();
            //regex is slow, so checking char by char.
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (!Character.isDigit(path.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };
    private static int sCPUCount = DEVICE_INFO_UNKNOWN;
    private static boolean sIsCpuCountInit = false;
    private static int sCPUMaxFreqKHz = DEVICE_INFO_UNKNOWN;
    private static boolean sIsCPUFreqInit = false;
    private static int sTotalMem = DEVICE_INFO_UNKNOWN;//MB
    private static boolean mIsMemInfoInit = false;

    public static int getNumberOfCPUCores() {
        if (sIsCpuCountInit) {
            return sCPUCount;
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            // Gingerbread doesn't support giving a single application access to both cores, but a
            // handful of devices (Atrix 4G and Droid X2 for example) were released with a dual-core
            // chipset and Gingerbread; that can let an app in the background run without impacting
            // the foreground application. But for our purposes, it makes them single core.
            sCPUCount = 1;
        } else {
            try {
                sCPUCount = getCoresFromFileInfo("/sys/devices/system/cpu/possible");
                if (sCPUCount == DEVICE_INFO_UNKNOWN) {
                    sCPUCount = getCoresFromFileInfo("/sys/devices/system/cpu/present");
                }
                if (sCPUCount == DEVICE_INFO_UNKNOWN) {
                    sCPUCount = getCoresFromCPUFileList();
                }
            } catch (Exception e) {
            }
        }
        sIsCpuCountInit = true;
        return sCPUCount;
    }

    private static int getCoresFromFileInfo(String fileLocation) {
        InputStream is = null;
        try {
            is = new FileInputStream(fileLocation);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String fileContents = buf.readLine();
            return getCoresFromFileString(fileContents);
        } catch (IOException e) {
            return DEVICE_INFO_UNKNOWN;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private static int getCoresFromFileString(String str) {
        if (str == null || !str.matches("0-[\\d]+$")) {
            return DEVICE_INFO_UNKNOWN;
        }
        int cores = Integer.valueOf(str.substring(2)) + 1;
        return cores;
    }

    private static int getCoresFromCPUFileList() {
        return new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
    }

    public static int getCPUMaxFreqKHz() {
        if (sIsCPUFreqInit) {
            return sCPUMaxFreqKHz;
        }

        try {
            for (int i = 0; i < getNumberOfCPUCores(); i++) {
                String filename =
                        "/sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq";
                File cpuInfoMaxFreqFile = new File(filename);
                if (cpuInfoMaxFreqFile.exists()) {
                    byte[] buffer = new byte[128];
                    FileInputStream stream = new FileInputStream(cpuInfoMaxFreqFile);
                    try {
                        stream.read(buffer);
                        int endIndex = 0;
                        //Trim the first number out of the byte buffer.
                        while (Character.isDigit(buffer[endIndex]) && endIndex < buffer.length) {
                            endIndex++;
                        }
                        String str = new String(buffer, 0, endIndex);
                        Integer freqBound = Integer.parseInt(str);
                        if (freqBound > sCPUMaxFreqKHz) {
                            sCPUMaxFreqKHz = freqBound / 1000;
                        }
                    } catch (NumberFormatException e) {
                        //Fall through and use /proc/cpuinfo.
                    } finally {
                        stream.close();
                    }
                }
            }
            if (sCPUMaxFreqKHz == DEVICE_INFO_UNKNOWN) {
                FileInputStream stream = new FileInputStream("/proc/cpuinfo");
                try {
                    int freqBound = parseFileForValue("cpu MHz", stream);
                    freqBound *= 1000; //MHz -> kHz
                    if (freqBound > sCPUMaxFreqKHz) sCPUMaxFreqKHz = freqBound / 1000;
                } finally {
                    stream.close();
                }
            }
        } catch (IOException e) {
        }
        sIsCPUFreqInit = true;
        return sCPUMaxFreqKHz;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int getTotalMemory(Context c) {
        if (mIsMemInfoInit) {
            return sTotalMem;
        }

        // memInfo.totalMem not supported in pre-Jelly Bean APIs.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            ActivityManager am = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
            am.getMemoryInfo(memInfo);
            if (memInfo != null) {
                sTotalMem = (int) (memInfo.totalMem / 1024 / 1024);
            }
        } else {
            try {
                FileInputStream stream = new FileInputStream("/proc/meminfo");
                try {
                    sTotalMem = parseFileForValue("MemTotal", stream) / 1024;
                } finally {
                    stream.close();
                }
            } catch (IOException e) {
            }
        }
        mIsMemInfoInit = true;
        return sTotalMem;
    }

    public static int getFreeMemory(Context context) {
        // memInfo.totalMem not supported in pre-Jelly Bean APIs.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            am.getMemoryInfo(memInfo);
            if (memInfo != null) {
                return (int) (memInfo.availMem / 1024 / 1024);
            } else {
                return DEVICE_INFO_UNKNOWN;
            }
        } else {
            long totalMem = DEVICE_INFO_UNKNOWN;
            try {
                FileInputStream stream = new FileInputStream("/proc/meminfo");
                try {
                    totalMem = parseFileForValue("MemAvailable", stream);
                } finally {
                    stream.close();
                }
            } catch (IOException e) {
            }
            return (int) (totalMem / 1024);
        }
    }


    private static int parseFileForValue(String textToMatch, FileInputStream stream) {
        byte[] buffer = new byte[1024];
        try {
            int length = stream.read(buffer);
            for (int i = 0; i < length; i++) {
                if (buffer[i] == '\n' || i == 0) {
                    if (buffer[i] == '\n') i++;
                    for (int j = i; j < length; j++) {
                        int textIndex = j - i;
                        //Text doesn't match query at some point.
                        if (buffer[j] != textToMatch.charAt(textIndex)) {
                            break;
                        }
                        //Text matches query here.
                        if (textIndex == textToMatch.length() - 1) {
                            return extractValue(buffer, j);
                        }
                    }
                }
            }
        } catch (IOException e) {
            //Ignore any exceptions and fall through to return unknown value.
        } catch (NumberFormatException e) {
        }
        return DEVICE_INFO_UNKNOWN;
    }


    private static int extractValue(byte[] buffer, int index) {
        while (index < buffer.length && buffer[index] != '\n') {
            if (Character.isDigit(buffer[index])) {
                int start = index;
                index++;
                while (index < buffer.length && Character.isDigit(buffer[index])) {
                    index++;
                }
                String str = new String(buffer, 0, start, index - start);
                return Integer.parseInt(str);
            }
            index++;
        }
        return DEVICE_INFO_UNKNOWN;
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.md_is_tablet);
    }

    /**
     * 获取CPU型号
     *
     * @return
     */
    public static String getCPUModel() {
        String cpuModel = null;
        FileReader fr = null;
        String brand = Build.BRAND != null ? Build.BRAND.toLowerCase() : null;

        // 华为机型通过Build.HARDWARE获取
        if (!TextUtils.isEmpty(brand) && (brand.equals("huawei") || brand.equals("honor"))) {
            cpuModel = Build.HARDWARE;
        } else {
            // 其他机型通过读取 /proc/cpuinfo 文件获取CPU型号,如果 /proc/cpuinfo 没有获取到，则返回Build.BOARD
            try {
                fr = new FileReader("/proc/cpuinfo");
                BufferedReader br = new BufferedReader(fr);
                String line;
                do {
                    line = br.readLine();
                    // 找到Hardware这一行
                    if (!TextUtils.isEmpty(line) && line.contains("Hardware")) {
                        String[] array = line.split(" ");
                        // 这一行最后显示的就是CPU型号
                        if (array != null && array.length > 0) {
                            cpuModel = array[array.length - 1].trim().toUpperCase();
                        }
                        break;
                    }
                } while (line != null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fr != null) {
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 部分手机无法通过cpuinfo获取芯片名，则返回Build.BOARD
            if (TextUtils.isEmpty(cpuModel)) {
                cpuModel = Build.BOARD;
            }
        }

        return cpuModel;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float dipToPixels(float dipValue) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static float spToPt(int sp) {
        return sp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static int sp2px(float sp) {
        return (int) (sp * Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f);
    }
}
