package com.zjk.model;

import java.util.ArrayList;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public class UserConfig {

    public UserInfo userInfo;
    public int dynamicCount;
    public FallThreshold fallThreshold;
    public ArrayList<TrainingSuggestData> trainingSuggestDataArrayList;

    @Override
    public String toString() {
        return "UserConfig{" +
                "userInfo=" + userInfo.toString() +
                ", dynamicCount=" + dynamicCount +
                ", fallThreshold=" + fallThreshold.toString() +
                ", trainingSuggestData=" + trainingSuggestDataArrayList.toString() +
                '}';
    }
}
