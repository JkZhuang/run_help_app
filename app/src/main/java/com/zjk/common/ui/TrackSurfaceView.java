package com.zjk.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zjk.common.util.DeviceUtils;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/05/09
 */
public class TrackSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private static final String TAG = "TrackSurfaceView";

    private Context mContext;

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Paint mPaint;
    private Path mPath;

    private boolean isDrawing;
    private float mX = 0;
    private float mY = 0;

    private float startX = 0f;
    private float startY = 120f;

    private int width;
    private int height;

    private float perW;
    private float perH;

    public TrackSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public TrackSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TrackSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(DeviceUtils.dpToPx(3));
        mPath = new Path();
        initView();
    }

    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = this.getWidth();
        height = this.getHeight();
        perW = (float) width * 111.0f / 8.0f;
        perH = (float) height * 111.0f / 8.0f;
        startX = (float) width / 3.0f;
        startY = (float) height / 3.0f;
        mX = startX;
        mY = startY;
        if (mPath != null) {
            mPath.moveTo(startX, startY);
        }
    }

    public void setCoordinate(float x, float y) {
        this.mX = x * perW + startX;
        this.mY = y * perH + startY;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing) {
            painting();
            // 加入新的坐标点
            mPath.lineTo(mX, mY);
        }
    }

    private void painting() {
        try {
            // 获得canvas对象
            mCanvas = mSurfaceHolder.lockCanvas();
            // 绘制路径
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {
            //
        } finally {
            if (mCanvas != null) {
                // 释放canvas对象并提交画布
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
