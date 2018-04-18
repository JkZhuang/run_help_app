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

    private boolean isStart;
    private boolean isRunning;
    private double curSpeed;
    private SportsData sportsData;

    public SportsBean() {
        isStart = true;
        isRunning = false;
        curSpeed = 0d;
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

    public SportsData getSportsData() {
        return sportsData;
    }

    public void setSportsData(SportsData sportsData) {
        this.sportsData = sportsData;
    }
}
