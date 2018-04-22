package com.zjk.util;

import com.zjk.common.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    private List<BaseActivity> activities = new ArrayList<>();
    private static ActivityCollector instance;

    private ActivityCollector() {

    }

    public static ActivityCollector instance() {
        if (instance == null) {
            synchronized (ActivityCollector.class) {
                if (instance == null) {
                    instance = new ActivityCollector();
                }
            }
        }
        return instance;
    }

    public void add(BaseActivity activity) {
        activities.add(activity);
    }

    public void remove(BaseActivity activity) {
        activities.remove(activity);
    }

    public boolean exist(Class<? extends BaseActivity> clazz) {
        for (BaseActivity activity : activities) {
            if (activity.getClass() == clazz) {
                return true;
            }
        }
        return false;
    }

    public void finishAll() {
        for (BaseActivity activity : activities) {
            activity.finish();
        }
    }
}
