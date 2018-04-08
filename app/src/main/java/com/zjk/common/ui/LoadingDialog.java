package com.zjk.common.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/08
 */

public class LoadingDialog extends Dialog {

    private static final String TAG = "LoadingDialog";

    private static final int LOADING_DIALOG_ALPHA = 220;

    private TextView mTvDialogText;
    private LinearLayout mLlContainer;
    private LinearLayout mLlDialogContainer;

    private String text;

    /**
     * style很关键
     */
    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialogStyle);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        findWidget();
        setListener();
        init();
    }

    private void findWidget() {
        mTvDialogText = (TextView) findViewById(R.id.tv_dialog);
        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);
        mLlDialogContainer = (LinearLayout) findViewById(R.id.ll_dialog_container);
    }

    private void setListener() {

    }

    private void init() {
        mTvDialogText.setText(text);
        mLlContainer.getBackground().setAlpha(LOADING_DIALOG_ALPHA / 2);
        mLlDialogContainer.getBackground().setAlpha(LOADING_DIALOG_ALPHA);
    }
}