package com.zjk.model;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/20
 */

public class UserConfig {

    public UserInfo userInfo;
    public int dynamicCount;
    public FallThreshold fallThreshold;
    public TrainingSuggestData trainingSuggestData;

    @Override
    public String toString() {
        return "UserConfig{" +
                "userInfo=" + userInfo.toString() +
                ", dynamicCount=" + dynamicCount +
                ", fallThreshold=" + fallThreshold.toString() +
                ", trainingSuggestData=" + trainingSuggestData.toString() +
                '}';
    }
}
