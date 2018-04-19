package com.zjk.module.sports.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.zjk.common.data.DefSports;
import com.zjk.common.ui.BaseActivity;
import com.zjk.module.home.sports.base.view.BaseSportsFragment;
import com.zjk.module.sports.UpdateSportsDataListener;
import com.zjk.module.sports.bean.SportsBean;
import com.zjk.module.sports.gps.service.GpsService;
import com.zjk.module.sports.presenter.ISportsPresenter;
import com.zjk.module.sports.presenter.SportsPresenter;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.DebugUtil;
import com.zjk.util.ToastUtil;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/16
 */

public class SportsActivity extends BaseActivity<ISportsPresenter>
        implements ISportsView, UpdateSportsDataListener {

    private static final String TAG = "SportsActivity";

    private static final int REQUEST_FOR_ACCESS_FINE_LOCATION = 6;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    private TextView mTvTitle;
    private TextView mTvDistance;
    private TextView mTvCalculateType;
    private TextView mTvSpeed;
    private Chronometer mCtUseTime;
    private TextView mTvPause;
    private TextView mTvCarryOn;
    private TextView mTvEnd;

    private ServiceConnection mServiceConnection = null;
    private GpsService mGpsService;
    private LocationManager mLocationManager;

    private SportsPresenter mPresenter;
    private SportsBean mBean;
    private double targetDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        findWidget();
        if (hasPermission()) {
            setListener();
            init();
        } else {
            requestPermission();
        }
    }

    @Override
    protected void findWidget() {
        mTvTitle = (TextView) findViewById(R.id.tv_title_sports_type);
        mTvDistance = (TextView) findViewById(R.id.tv_distance);
        mTvCalculateType = (TextView) findViewById(R.id.tv_calculate_way);
        mTvSpeed = (TextView) findViewById(R.id.tv_speed);
        mCtUseTime = (Chronometer) findViewById(R.id.ct_time);
        mTvPause = (TextView) findViewById(R.id.tv_pause);
        mTvCarryOn = (TextView) findViewById(R.id.tv_keep);
        mTvEnd = (TextView) findViewById(R.id.tv_end);
    }

    @Override
    protected void setListener() {
        mTvCarryOn.setOnClickListener(this);
        mTvPause.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
    }

    @Override
    protected void init() {
        mPresenter = new SportsPresenter(this);
        mBean = new SportsBean();
        mBean.setRunning(true);
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mBean.getSportsData().setType(args.getInt(BaseSportsFragment.KEY_SPORTS_TYPE, 0));
            targetDistance = args.getDouble(BaseSportsFragment.KEY_DISTANCE, 0.0d);
        }
        initTitle();
        setUseTime();
        bindService();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setGpsListener();
    }

    private void initTitle() {
        switch (mBean.getSportsData().getType()) { // 0-行走；1-跑步；2-骑行；3-轮滑
            case DefSports.WALK:
                mTvTitle.setText(R.string.walk_ing);
                break;
            case DefSports.RUNNING:
                mTvTitle.setText(R.string.run_ing);
                break;
            case DefSports.RIDING:
                mTvTitle.setText(R.string.riding_ing);
                break;
            case DefSports.ROLLER_SKATING:
                mTvTitle.setText(R.string.roller_skating_ing);
                break;
        }
    }

    private void setUseTime() {
        mCtUseTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            boolean isPair = true;

            @Override
            public void onChronometerTick(Chronometer chrono) {
                long time;
                if (mBean.isRunning()) {
                    time = SystemClock.elapsedRealtime() - chrono.getBase();
                    mBean.getSportsData().setUsedTime(time);
                } else {
                    time = mBean.getSportsData().getUsedTime();
                }

                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                chrono.setText(hh + ":" + mm + ":" + ss);

                if (mBean.isRunning()) {
                    chrono.setText(hh + ":" + mm + ":" + ss);
                } else {
                    if (isPair) {
                        isPair = false;
                        chrono.setText(hh + ":" + mm + ":" + ss);
                    } else {
                        isPair = true;
                        chrono.setText("");
                    }
                }

            }
        });
    }

    private void bindService() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DebugUtil.debug(TAG, "onServiceConnected");
                GpsService.GpsBinder mBinder = (GpsService.GpsBinder) service;
                mGpsService = mBinder.getService();
                mGpsService.setBean(mBean);
                mGpsService.setListener(SportsActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                DebugUtil.debug(TAG, "onServiceDisconnected");
            }
        };

        Intent intent = new Intent(this, GpsService.class);
        this.bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        if (mServiceConnection != null) {
            this.unbindService(mServiceConnection);
        }
    }

    private boolean hasPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FOR_ACCESS_FINE_LOCATION);
        }
    }

    private void setGpsListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocationManager.registerGnssStatusCallback(new GnssStatus.Callback() {
                @Override
                public void onStarted() {
                    super.onStarted();
                }

                @Override
                public void onStopped() {
                    super.onStopped();
                }

                @Override
                public void onFirstFix(int ttffMillis) {
                    super.onFirstFix(ttffMillis);
                    if (mBean.isRunning()) {
                        mBean.setCanLocationUsed(true);
                        updateUIStatus(true);
                    }
                }

                @TargetApi(Build.VERSION_CODES.N)
                @Override
                public void onSatelliteStatusChanged(GnssStatus status) {
                    super.onSatelliteStatusChanged(status);
                    if (!mBean.isRunning()) {
                        return;
                    }
                    boolean bool = false;
                    for (int i = 0; i < status.getSatelliteCount(); i++) {
                        if (status.usedInFix(i)) {
                            bool = true;
                            break;
                        }
                    }
                    if (bool != mBean.isCanLocationUsed()) {
                        updateUIStatus(bool);
                        mBean.setCanLocationUsed(bool);
                        ToastUtil.shortShow(SportsActivity.this, bool ? "可用" : "不可用");
                    }
                }
            });
            return;
        }
        mLocationManager.addGpsStatusListener(new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        if (ActivityCompat.checkSelfPermission(SportsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
                        int statusUsed = 0;
                        Iterable<GpsSatellite> iterable = gpsStatus.getSatellites();
                        for (GpsSatellite sat : iterable) {
                            if (sat.usedInFix()) {
                                statusUsed++;
                            }
                        }
                        if (statusUsed == 0) {
                            updateUIStatus(false);
                        } else {
                            updateUIStatus(true);
                        }
                        break;
                    case GpsStatus.GPS_EVENT_STOPPED:
                        break;
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        break;
                }
            }
        });

    }

    private void updateUIStatus(boolean isLocationCanUsed) {
        if (isLocationCanUsed) {
            mTvCalculateType.setText(R.string.gps_calculate);
            mTvPause.setVisibility(View.VISIBLE);
            mTvCarryOn.setVisibility(View.GONE);
            mTvEnd.setVisibility(View.GONE);
            mCtUseTime.start();
        } else {
            mTvCalculateType.setText(R.string.gps_no_signal);
            mTvPause.setVisibility(View.GONE);
            mTvCarryOn.setVisibility(View.VISIBLE);
            mTvEnd.setVisibility(View.VISIBLE);
            mCtUseTime.stop();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != REQUEST_FOR_ACCESS_FINE_LOCATION) {
            return;
        }
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setListener();
            init();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage(R.string.request_location_permission)
                    .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pause:
                pause();
                break;
            case R.id.tv_keep:
                carryOn();
                break;
            case R.id.tv_end:
                stop();
                break;
        }
    }

    private void pause() {
        mBean.setRunning(false);
        updateUI(true);
    }

    private void carryOn() {
        mBean.setRunning(true);
        updateUI(false);
    }

    private void stop() {
        mBean.setRunning(false);
        if (mBean.getSportsData().getrGDList() == null || mBean.getSportsData().getrGDList().size() < 10) {
            ToastUtil.shortShow(this, getString(R.string.no_sports_data));
            return;
        }
        if (mPresenter != null) {
            mPresenter.uploadSportsData(mBean.getSportsData());
        }
    }

    private void updateUI(boolean isPause) {
        mTvPause.setVisibility(isPause ? View.GONE : View.VISIBLE);
        mTvCarryOn.setVisibility(isPause ? View.VISIBLE : View.GONE);
        mTvEnd.setVisibility(isPause ? View.VISIBLE : View.GONE);
    }

    @Override
    public void update() {
        mTvDistance.setText(String.format("%.2f", mBean.getSportsData().getDistance()));
        mTvSpeed.setText(String.format("%.2f", mBean.getCurSpeed()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERMISSION_SETTING && resultCode == RESULT_OK) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_FOR_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void showProgress(int msgId) {
        showLoadingDialog(msgId);
    }

    @Override
    public void hideProgress() {
        dismissLoadingDialog();
    }

    @Override
    public void uploadSportsDataSuccess(boolean bool) {
        if (bool) {
            ToastUtil.shortShow(this, R.string.upload_sports_data_success);
        } else {
            ToastUtil.shortShow(this, R.string.upload_sports_data_fail);
        }
    }

    @Override
    public void uploadSportsDataFail(Result result) {
        ToastUtil.shortShow(this, R.string.upload_sports_data_fail);
    }
}
