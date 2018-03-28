package com.zjk.util;

import android.util.Log;

import com.zjk.run_help.BuildConfig;

/**
 * <pre>
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2017/12/13
 * </pre>
 */
public class DebugUtil {

    private static int logLevel = Log.VERBOSE;
    private static boolean isDebug = BuildConfig.DEBUG;

    private static String getContent(String msg, int place, Object... args) {
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), place);
            return sourceLinks + String.format(msg, args);
        } catch (Throwable throwable) {
            return msg;
        }
    }

    private static String getNameFromTrace(StackTraceElement[] traceElements, int place) {
        StringBuilder taskName = new StringBuilder();
        if (traceElements != null && traceElements.length > place) {
            StackTraceElement traceElement = traceElements[place];
            taskName.append(traceElement.getMethodName());
            taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(")");
        }
        return taskName.toString();
    }

    public static void info(String tag, String content, Object... args) {
        if (isDebug && logLevel <= Log.INFO) {
            Log.i(tag, getContent(content, 4, args));
        }
    }

    public static void verbose(String tag, String content, Object... args) {
        if (isDebug && logLevel <= Log.VERBOSE) {
            Log.v(tag, getContent(content, 4, args));
        }
    }

    public static void warn(String tag, String content, Object... args) {
        if (isDebug && logLevel <= Log.WARN) {
            Log.w(tag, getContent(content, 4, args));
        }
    }

    public static void error(String tag, String content, Object... args) {
        if (isDebug && logLevel <= Log.ERROR) {
            Log.e(tag, getContent(content, 4, args));
        }
    }

    public static void error(String tag, String content, Exception exception) {
        if (isDebug && logLevel <= Log.ERROR) {
            Log.e(tag, getContent(content, 4, exception.getMessage()));
        }
    }

    public static void debug(String tag, String content, Object... args) {
        if (isDebug && logLevel <= Log.DEBUG) {
            Log.d(tag, getContent(content, 4, args));
        }
    }
}
