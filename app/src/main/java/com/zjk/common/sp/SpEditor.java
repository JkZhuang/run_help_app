package com.zjk.common.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zjk.common.app.App;
import com.zjk.common.util.AppUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class SpEditor {

    private static SharedPreferences preferences;

    public static <T> void putAndApply(@Nullable String spName, @Nullable String key, @NonNull T value) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        put(editor, key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static <T> void putAndApplyDisByUId(@Nullable String spName, @Nullable String key, @NonNull T value) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        put(editor, key + getUId(), value);
        SharedPreferencesCompat.apply(editor);
    }

    public static <T> void putAllAndApply(@Nullable String spName, @Nullable Map<String, T> map) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String key = entry.getKey();
            T value = entry.getValue();
            put(editor, key, value);
        }
        SharedPreferencesCompat.apply(editor);
    }

    public static <T> void putAllAndApplyDisByUId(@Nullable String spName, @Nullable Map<String, T> map) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String key = entry.getKey();
            T value = entry.getValue();
            put(editor, key + getUId(), value);
        }
        SharedPreferencesCompat.apply(editor);
    }

    @SuppressWarnings("unchecked")
    private static <T> void put(SharedPreferences.Editor editor, String key, T value) {
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set) value);
        } else {
            editor.putString(key, value.toString());
        }
    }

    public static <T> T get(@Nullable String spName, @Nullable String key, @Nullable T defaultObject) {
        return get(AppUtils.getContext(), spName, key, defaultObject);
    }

    public static <T> T getDisByUId(@Nullable String spName, @Nullable String key, @NonNull T defaultObject) {
        return get(AppUtils.getContext(), spName, key + getUId(), defaultObject);
    }

    @SuppressWarnings("unchecked")
    private static <T> T get(Context context, String spName, String key, T defaultObject) {
        preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return (T) preferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return (T) ((Integer) preferences.getInt(key, (Integer) defaultObject));
        } else if (defaultObject instanceof Boolean) {
            return (T) ((Boolean) preferences.getBoolean(key, (Boolean) defaultObject));
        } else if (defaultObject instanceof Float) {
            return (T) ((Float) preferences.getFloat(key, (Float) defaultObject));
        } else if (defaultObject instanceof Long) {
            return (T) ((Long) preferences.getLong(key, (Long) defaultObject));
        } else if (defaultObject instanceof Set) {
            return (T) preferences.getStringSet(key, (Set) defaultObject);
        } else {
            return null;
        }
    }

    public static void remove(@Nullable String spName, @Nullable String key) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void removeDisByUId(@Nullable String spName, @Nullable String key) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key + getUId());
        SharedPreferencesCompat.apply(editor);
    }

    public static void clear(@Nullable String spName) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(@Nullable String spName, @Nullable String key) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.contains(key);
    }

    public static boolean containsDisByUId(@Nullable String spName, @Nullable String key) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.contains(key + getUId());
    }

    public static Map<String, ?> getAll(@Nullable String spName) {
        preferences = AppUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.getAll();
    }

    private static class SharedPreferencesCompat {

        private static final Method sApplyMethod = findApply();

        private static Method findApply() {
            try {
                Class<SharedPreferences> cls = SharedPreferences.class;
                return cls.getMethod("apply");
            } catch (NoSuchMethodException e) {
                // ignore
            }
            return null;
        }

        private static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                // ignore
            } catch (IllegalAccessException e) {
                // ignore
            } catch (InvocationTargetException e) {
                // ignore
            }
            editor.commit();
        }
    }

    private static String getUId() {
        int uId = 0;
        try {
            uId = App.instance().getUserInfo().getuId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(uId);
    }
}
