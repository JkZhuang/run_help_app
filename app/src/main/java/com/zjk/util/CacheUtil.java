package com.zjk.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class CacheUtil {

    /**
     * 清除缓存
     * @param file
     */
    public static void clearingCache(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (!TextUtils.isEmpty(file.getName())) {
                File[] files = file.listFiles();
                for (File item : files) {
                    if (item.isFile()) {
                        item.delete();
                    } else {
                        clearingCache(item);
                    }
                }
            }
        }
    }

    /**
     * 获取文件大小
     * @param file
     * @return
     */
    public String getCacheSize(File file) {
        long cacheSize;
        if (file.isDirectory()) {
            cacheSize = getFileSizes(file);
        } else {
            cacheSize = getFileSize(file);
        }
        return formatFileSize(cacheSize);
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return size
     */
    private long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                assert fis != null;
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    /**
     * 获取文件夹大小
     *
     * @param file
     * @return size
     */
    private long getFileSizes(File file) {
        long size = 0;
        File files[] = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                size = size + getFileSizes(file1);
            } else {
                size = size + file1.length();
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param file
     * @return
     */
    private String formatFileSize(long file) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        if (file < 1024) {
            fileSizeString = df.format((double) file) + "B";
        } else if (file < 1048576) {
            fileSizeString = df.format((double) file / 1024) + "K";
        } else if (file < 1073741824) {
            fileSizeString = df.format((double) file / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) file / 1073741824) + "G";
        }
        return fileSizeString;
    }
}