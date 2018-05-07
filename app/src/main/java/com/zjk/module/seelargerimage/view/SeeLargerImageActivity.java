package com.zjk.module.seelargerimage.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.photoview.PhotoView;
import com.zjk.run_help.R;
import com.zjk.util.CommonsUtil;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/05/07
 */

public class SeeLargerImageActivity extends BaseActivity {

    private static final String TAG = "SeeLargerImageActivity";

    public static final String KEY_IMAGE_URL = "image_url";

    private PhotoView mPv;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, SeeLargerImageActivity.class);
        intent.putExtra(KEY_IMAGE_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_see_larger_image);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mPv = (PhotoView) findViewById(R.id.iv_photo);
        mPv.enable();
    }

    @Override
    protected void setListener() {
        mPv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        String imageUrl = getIntent().getStringExtra(KEY_IMAGE_URL);
        Glide.with(this)
                .load(CommonsUtil.getImageUrl(imageUrl))
                .asBitmap()
                .placeholder(R.drawable.photo_default)
                .into(mPv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_photo:
                finish();
                break;
        }
    }
}
