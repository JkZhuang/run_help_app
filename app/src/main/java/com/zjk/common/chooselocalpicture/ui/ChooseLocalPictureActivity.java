package com.zjk.common.chooselocalpicture.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.zjk.common.chooselocalpicture.adapter.ChooserPictureAdapter;
import com.zjk.common.ui.BaseActivity;
import com.zjk.run_help.R;
import com.zjk.util.BrandUtil;
import com.zjk.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandengzhe on 2018/4/7.
 */

public class ChooseLocalPictureActivity extends BaseActivity {

    private static final String TAG = "ChooseLocalPictureActivity";

    private static final int CODE_REQUEST_CAMERA_PERMISSIONS = 0;

    private Toolbar mToolbar;
    private RecyclerView mRecylerView;

    private ChooserPictureAdapter mAdapter;
    private ArrayList<String> photoFileNameList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_picture);

        findWidget();
        setListener();
        init();
        requestReadPermission();
    }

    public void requestReadPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showDialog();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            }
        } else {
            photoFileNameList = ImageUtil.instance(this).getImageFileNameList();
            mAdapter.setData(photoFileNameList);
        }
    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settingPermissionActivity();
                    }
                })
                .setMessage(R.string.photo_need_read_permission);
        alertDialogBuilder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    photoFileNameList = ImageUtil.instance(this).getImageFileNameList();
                    mAdapter.setData(photoFileNameList);
                } else {
                    showDialog();
                }
            }
        }
    }

    private void settingPermissionActivity() {
        //判断是否为小米系统
        if (TextUtils.equals(BrandUtil.getSystemInfo().getOs(), BrandUtil.SYS_MIUI)) {
            Intent miuiIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            miuiIntent.putExtra("extra_pkgname", getPackageName());
            //检测是否有能接受该Intent的Activity存在
            List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(miuiIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfos.size() > 0) {
                startActivityForResult(miuiIntent, CODE_REQUEST_CAMERA_PERMISSIONS);
                return;
            }
        }
        //如果不是小米系统 则打开Android系统的应用设置页
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, CODE_REQUEST_CAMERA_PERMISSIONS);
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecylerView = (RecyclerView) findViewById(R.id.rv_picture);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
        mRecylerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ChooserPictureAdapter(ChooseLocalPictureActivity.this);
        mRecylerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
