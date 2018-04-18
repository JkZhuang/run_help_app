package com.zjk.module.sports.gps.model;

/**
 * Created by zjk on 17/10/8.
 */
public class GpsSportsData {

    private boolean isFirstTime;
    private boolean isRunning;
    private int type;
    private long startTime;
    private long curTime;
    private long endTime;

    private double distance; // 距离
    private double maxSpeed; // 最大速度
    private double curSpeed; // 现在速度

    private onGpsServiceUpdateListener listener;

    public interface onGpsServiceUpdateListener {
        public void update();
    }

    public void setListener(onGpsServiceUpdateListener listener){
        this.listener = listener;
    }

    public void update(){
        listener.update();
    }

    public GpsSportsData() {
        isRunning = false;
        distance = 0;
        curSpeed = 0;
        maxSpeed = 0;
        endTime = 0;
    }

    public GpsSportsData(onGpsServiceUpdateListener listener){
        this();
        setListener(listener);
    }

    public void addDistance(double distance){
        this.distance = this.distance + distance;
    }

    public double getDistance(){
        return distance;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getAverageSpeed(){
        double average;
        if (curTime <= 0) {
            average = 0.0;
        } else {
            average = distance * 3600.0 / curTime;
        }
        return average;
    }

    public double getAverageSpeedMotion(){
        double motionTime = curTime - endTime;
        double average;
        if (motionTime <= 0){
            average = 0.0;
        } else {
            average = distance * 3600.0 / motionTime;
        }
        return average;
    }

    public void setCurSpeed(double curSpeed) {
        this.curSpeed = curSpeed;
        if (curSpeed > maxSpeed){
            maxSpeed = curSpeed;
        }
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void setEndTime(long endTime) {
        this.endTime += endTime;
    }

    public double getCurSpeed() {
        return curSpeed;
    }

    public long getCurTime() {
        return curTime;
    }

    public void setCurTime(long curTime) {
        this.curTime = curTime;
    }
}
