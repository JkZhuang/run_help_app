package com.zjk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zjk.common.ui.BaseActivity;
import com.zjk.logic.LogicHandler;
import com.zjk.logic.LogicImpl;
import com.zjk.okhttp.DefList;
import com.zjk.param.Param;
import com.zjk.param.UploadImageParam;
import com.zjk.result.Result;
import com.zjk.result.UploadImageResult;
import com.zjk.run_help.R;
import com.zjk.util.DebugUtil;
import com.zjk.util.ToastUtil;

/**
 * Created by pandengzhe on 2018/3/27.
 */

public class Test extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });

        ImageView iv = (ImageView) findViewById(R.id.iv_test);
        Glide.with(this)
                .load(DefList.url + "/image/getImage?imageDir=head_photo&imageName=188132952402018-04-13-19-43-3646969189141c41aeaba239fa2.jpg")
                .placeholder(R.drawable.photo_default)
                .into(iv);
    }

    @Override
    protected void findWidget() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {

    }

    private void test() {
        UploadImageParam param = new UploadImageParam();
        param.page = "/file/uploadImage";
        param.path = "/storage/emulated/0/Tencent/QQfile_recv/IMG_20160810_184256.jpg";
        LogicImpl.getInstance().uploadImage(param, new LogicHandler<UploadImageResult>() {
            @Override
            public void onResult(UploadImageResult result, boolean onUIThread) {
                if (onUIThread && result.isOk()) {
                    ToastUtil.shortShow(Test.this, "上传成功");
                } else if (onUIThread) {
                    ToastUtil.shortShow(Test.this, result.errMsg);
                }
            }
        });
    }
}
