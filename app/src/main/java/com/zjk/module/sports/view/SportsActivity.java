package com.zjk.module.sports.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.zjk.common.ui.BaseActivity;
import com.zjk.module.sports.UpdateSportsDataListener;
import com.zjk.module.sports.bean.SportsBean;
import com.zjk.module.sports.gps.service.GpsService;
import com.zjk.module.sports.presenter.ISportsPresenter;
import com.zjk.module.sports.presenter.SportsPresenter;
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

    public static final String SPORTS_TYPE = "sports_type";

    private static final int REQUEST_FOR_ACCESS_FINE_LOCATION = 6;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    private TextView mTvTitle;
    private TextView mTvDistance;
    private TextView mTvCalculateType;
    private TextView mTvSpeed;
    private TextView mTvUseTime;
    private TextView mTvPause;
    private TextView mTvCarryOn;
    private TextView mTvEnd;

    private ServiceConnection mServiceConnection = null;
    private GpsService mGpsService;
    private LocationManager mLocationManager;

    private SportsPresenter mPresenter;
    private SportsBean mBean;

    public static void start(BaseActivity activity, int type) {
        Intent intent = new Intent(activity, SportsActivity.class);
        intent.putExtra(SPORTS_TYPE, type);
        activity.startActivity(intent);
    }

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
        mTvUseTime = (TextView) findViewById(R.id.tv_use_time);
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
        mBean.getSportsData().setType(getIntent().getIntExtra(SPORTS_TYPE, 0));
        bindService();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setGpsListener();
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
                    ToastUtil.shortShow(SportsActivity.this, "onStarted 1");

                }

                @Override
                public void onStopped() {
                    super.onStopped();
                    ToastUtil.shortShow(SportsActivity.this, "onStopped 2");
                }

                @Override
                public void onFirstFix(int ttffMillis) {
                    super.onFirstFix(ttffMillis);
                }

                @Override
                public void onSatelliteStatusChanged(GnssStatus status) {
                    super.onSatelliteStatusChanged(status);
                }
            });
        } else {
            mLocationManager.addGpsStatusListener(new GpsStatus.Listener() {
                @Override
                public void onGpsStatusChanged(int event) {
                    switch (event) {
                        case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                            ToastUtil.shortShow(SportsActivity.this, "GPS_EVENT_SATELLITE_STATUS 1");
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
//                int satsInView = 0;
//                int statusUsed = 0;
//                Iterable<GpsSatellite> sats = gpsStatus.getSatellites();
//                for (GpsSatellite sat : sats) {
//                    satsInView++;
//                    if (sat.usedInFix()) {
//                        statusUsed++;
//                    }
//                }
//                if (statusUsed == 0) {
//                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
//                    data.setRunning(false);
//                    status.setText("");
//                    getActivity().stopService(new Intent(getActivity().getBaseContext(), GpsServices.class));
//                    fab.setVisibility(View.INVISIBLE);
//                    refresh.setVisibility(View.INVISIBLE);
//                    stop.setVisibility(View.INVISIBLE);
//                    status.setText(getResources().getString(R.string.waiting_for_fix));
//                    firstfix = true;
//                }
                            break;

                        case GpsStatus.GPS_EVENT_STOPPED:
                            ToastUtil.shortShow(SportsActivity.this, "GPS_EVENT_STOPPED 2");
//                if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    showGpsDisabledDialog();
//                }
                            break;
                        case GpsStatus.GPS_EVENT_FIRST_FIX:
                            ToastUtil.shortShow(SportsActivity.this, "GPS_EVENT_FIRST_FIX 3");
                            break;
                    }
                }
            });
        }
    }

    private void updateUIStatus(boolean isLocationCanUsed) {
        if (isLocationCanUsed) {

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

                break;
            case R.id.tv_keep:

                break;
            case R.id.tv_end:

                break;
        }
    }

    @Override
    public void update() {
        mTvDistance.setText(String.valueOf(mBean.getSportsData().getDistance()));
//        mTvCalculateType.setText();
        mTvSpeed.setText(String.valueOf(mBean.getCurSpeed()));
//        mTvUseTime.setText();
        if (mBean.isRunning()) {
            mTvPause.setVisibility(View.VISIBLE);
            mTvCarryOn.setVisibility(View.GONE);
            mTvEnd.setVisibility(View.GONE);
        } else {
            mTvPause.setVisibility(View.GONE);
            mTvCarryOn.setVisibility(View.VISIBLE);
            mTvEnd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERMISSION_SETTING && resultCode == RESULT_OK) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_FOR_ACCESS_FINE_LOCATION);
        }
    }
}
