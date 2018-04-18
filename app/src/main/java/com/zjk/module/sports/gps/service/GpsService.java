package com.zjk.module.sports.gps.service;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.zjk.module.sports.UpdateSportsDataListener;
import com.zjk.module.sports.bean.SportsBean;
import com.zjk.module.sports.view.SportsActivity;
import com.zjk.run_help.R;

import java.util.Date;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public class GpsService extends Service implements LocationListener {

    private static final String TAG = "GpsService";

    private static final int REQUEST_GPS = 0;
    private static final int TIME_GPS_UPDATE = 500;

    private SportsBean bean;
    private UpdateSportsDataListener listener;

    private PendingIntent contentIntent;
    private LocationManager mLocationManager;
    private Location mLastLocation = new Location("lastLocation");

    private double lastLon = 0; // 经度
    private double lastLat = 0; // 纬度

    @Override
    public void onCreate() {
        super.onCreate();
        initNotificationIntent();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_GPS_UPDATE, 0, this);
        }
    }

    private void initNotificationIntent() {
        Intent notificationIntent = new Intent(this, SportsActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        contentIntent = PendingIntent.getActivity(this, REQUEST_GPS, notificationIntent, 0);
        updateNotification(false);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void updateNotification(boolean hasData) {
        Notification.Builder builder = new Notification.Builder(getBaseContext())
                .setContentTitle(getString(R.string.sports_ing))
                .setSmallIcon(R.mipmap.ic_logo)
                .setContentIntent(contentIntent);

        if (hasData) {
            builder.setContentText(String.format(getString(R.string.notification_text),
                    String.valueOf(bean.getSportsData().getMaxSpeed()),
                    String.valueOf(bean.getSportsData().getDistance())));
        } else {
            builder.setContentText(String.format(getString(R.string.notification_text), '-', '-'));
        }
        Notification notification = builder.build();
        startForeground(R.string.notification_id, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void setBean(SportsBean bean) {
        this.bean = bean;
    }

    public void setListener(UpdateSportsDataListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new GpsBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.removeUpdates(this);
        stopForeground(true);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (bean.isRunning() && bean.isCanLocationUsed()) {
            double currentLat = location.getLatitude();
            double currentLon = location.getLongitude();

            if (bean.isStart()) {
                lastLat = currentLat;
                lastLon = currentLon;
                bean.setStart(false);
            }

            mLastLocation.setLatitude(lastLat);
            mLastLocation.setLongitude(lastLon);
            double distance = mLastLocation.distanceTo(location);

            if (location.getAccuracy() < distance) {
                bean.getSportsData().setDistance(distance);

                lastLat = currentLat;
                lastLon = currentLon;
            }

            if (location.hasSpeed()) {
                bean.setCurSpeed(location.getSpeed() * 3.6);
                if (bean.getCurSpeed() > bean.getSportsData().getMaxSpeed()) {
                    bean.getSportsData().setMaxSpeed(bean.getCurSpeed());
                }
                if (location.getSpeed() == 0) {
                    new isStillStopped().execute();
                }
            }
            listener.update();
            updateNotification(true);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public class GpsBinder extends Binder {

        public GpsService getService() {
            return GpsService.this;
        }
    }

    class isStillStopped extends AsyncTask<Void, Integer, String> {
        int timer = 0;

        @Override
        protected String doInBackground(Void... unused) {
            try {
                while (bean.getCurSpeed() == 0) {
                    Thread.sleep(1000);
                    timer++;
                }
            } catch (InterruptedException t) {
                return ("The sleep operation failed");
            }
            return ("return object when task is finished");
        }

        @Override
        protected void onPostExecute(String message) {
            bean.getSportsData().setEndTime(new Date());
        }
    }
}
