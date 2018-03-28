package com.zjk.common.util;

import android.os.Environment;

import com.zjk.util.DebugUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created on 2017/12/23
 * Description:
 * 系统属性缓存，从build.prop文件中读取
 *
 * @author zhanglinwei(G7901)
 */
public class BuildProperties {
    private static final String TAG = BuildProperties.class.getSimpleName();

    static class SingletonHolder {
        static BuildProperties INSTANCE = new BuildProperties();
    }

    public static BuildProperties getDefault() {
        return SingletonHolder.INSTANCE;
    }

    private final Properties properties;

    public BuildProperties() {
        properties = new Properties();
        FileInputStream fis = null;
        try {
            File file = new File(Environment.getRootDirectory(), "build.prop");
            fis = new FileInputStream(file);
            properties.load(fis);
        } catch (IOException ex) {
            DebugUtil.debug(TAG, "failed to create BuildProperties" + ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public boolean containsKey(final Object key) {
        return properties.containsKey(key);
    }

    public boolean containsValue(final Object value) {
        return properties.containsValue(value);
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return properties.entrySet();
    }

    public String getProperty(final String name) {
        return properties.getProperty(name);
    }

    public String getProperty(final String name, final String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public Enumeration<Object> keys() {
        return properties.keys();
    }

    public Set<Object> keySet() {
        return properties.keySet();
    }

    public int size() {
        return properties.size();
    }

    public Collection<Object> values() {
        return properties.values();
    }

}