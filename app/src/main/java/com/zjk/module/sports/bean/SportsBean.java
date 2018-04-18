package com.zjk.module.sports.bean;

import com.zjk.model.SportsData;
import com.zjk.model.SportsGranularityData;

import java.util.ArrayList;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public class SportsBean {

    private boolean isStart; // 是否第一次开始
    private boolean isRunning; // 是否在运动
    private double curSpeed;
    private boolean canLocationUsed; // 定位是否可用
    private SportsData sportsData;

    public SportsBean() {
        isStart = true;
        isRunning = false;
        curSpeed = 0d;
        canLocationUsed = false;
        sportsData = new SportsData();
        sportsData.setrGDList(new ArrayList<SportsGranularityData>());
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public double getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(double curSpeed) {
        this.curSpeed = curSpeed;
    }

    public boolean isCanLocationUsed() {
        return canLocationUsed;
    }

    public void setCanLocationUsed(boolean canLocationUsed) {
        this.canLocationUsed = canLocationUsed;
    }

    public SportsData getSportsData() {
        return sportsData;
    }

    public void setSportsData(SportsData sportsData) {
        this.sportsData = sportsData;
    }
}
