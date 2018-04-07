package com.zjk.module.user.register.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zjk.common.chooselocalpicture.ui.ChooseLocalPictureActivity;
import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.MyPageAdapter;
import com.zjk.common.ui.ShapedImageView;
import com.zjk.module.user.register.present.RegisterPresenter;
import com.zjk.run_help.R;
import com.zjk.util.BrandUtil;
import com.zjk.util.DebugUtil;
import com.zjk.util.GsonUtil;
import com.zjk.util.ImageUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class RegisterActivity extends BaseActivity implements IRegisterView {

    private static final String TAG = "RegisterActivity";

    private static final int REGISTER_STEP_COUNT = 3;

    private ConstraintLayout mClContainer;
    private Toolbar mToolBar;
    private ViewPager mViewPager;
    private View[] mViewArray;
    private MyPageAdapter mAdapter;

    private LinearLayout mLlStep1;
    private EditText mEtPhone;
    private EditText mEtPassword;
    private EditText mEtConfirmPassword;
    private TextView mTvNextStep1;

    private RelativeLayout mRlStep2;
    private ShapedImageView mIvHeadPhoto;
    private EditText mEtNickName;
    private EditText mEtHeight;
    private EditText mEtWeight;
    private TextView mTVBirthday;
    private TextView mTVGender;
    private TextView mTVNextStep2;

    private LinearLayout mLlStep3;
    private EditText mEtUrgentPhoto;
    private TextView mTvRegister;

    private RegisterPresenter mPresenter;

    private ArrayList<String> genderItems = new ArrayList<>();
    private ArrayList<String> photoFileNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findWidget();
        setListener();
        init();

        mPresenter = new RegisterPresenter(this);
    }

    private void selectBirthday() {
        //时间选择器
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(1994, 0, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        TimePickerView pvTime = new TimePickerBuilder(RegisterActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(RegisterActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setCancelText(getResources().getString(R.string.cancel))//取消按钮文字
                .setSubmitText(getResources().getString(R.string.sure))//确认按钮文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setSubmitColor(getResources().getColor(R.color.colorAccent))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.colorAccent))//取消按钮文字颜色
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    private void selectGender() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(RegisterActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String gender = genderItems.get(options1);
                mTVGender.setText(gender);
            }
        })
                .setCancelText(getResources().getString(R.string.cancel))//取消按钮文字
                .setSubmitText(getResources().getString(R.string.sure))//确认按钮文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setSubmitColor(getResources().getColor(R.color.colorAccent))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.colorAccent))//取消按钮文字颜色
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false) //是否显示为对话框样式
                .build();
        pvOptions.setPicker(genderItems);
        pvOptions.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        DebugUtil.debug(TAG, "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat(GsonUtil.TIME_FORMAT);
        return format.format(date);
    }

    @Override
    protected void findWidget() {
        mClContainer = (ConstraintLayout) findViewById(R.id.cl_container);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.vp_register);

        mLlStep1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_register_step_1, mClContainer, false);
        mEtPhone = (EditText) mLlStep1.findViewById(R.id.et_phone);
        mEtPassword = (EditText) mLlStep1.findViewById(R.id.et_password);
        mEtConfirmPassword = (EditText) mLlStep1.findViewById(R.id.et_password_confirm);
        mTvNextStep1 = (TextView) mLlStep1.findViewById(R.id.tv_next1);

        mRlStep2 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_register_step_2, mClContainer, false);
        mIvHeadPhoto = (ShapedImageView) mRlStep2.findViewById(R.id.iv_head_photo);
        mEtNickName = (EditText) mRlStep2.findViewById(R.id.et_nick_name);
        mEtHeight = (EditText) mRlStep2.findViewById(R.id.et_height);
        mEtWeight = (EditText) mRlStep2.findViewById(R.id.et_weight);
        mTVBirthday = (TextView) mRlStep2.findViewById(R.id.tv_birthday);
        mTVGender = (TextView) mRlStep2.findViewById(R.id.tv_gender);
        mTVNextStep2 = (TextView) mRlStep2.findViewById(R.id.tv_next2);

        mLlStep3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_register_step_3, mClContainer, false);
        mEtUrgentPhoto = (EditText) mLlStep3.findViewById(R.id.et_urgent_phone);
        mTvRegister = (TextView) mLlStep3.findViewById(R.id.tv_register);
    }

    @Override
    protected void setListener() {
        mTvNextStep1.setOnClickListener(this);
        mTVNextStep2.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mIvHeadPhoto.setOnClickListener(this);
        mTVBirthday.setOnClickListener(this);
        mTVGender.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setupActionBar(mToolBar);
        mViewArray = new View[REGISTER_STEP_COUNT];
        mViewArray[0] = mLlStep1;
        mViewArray[1] = mRlStep2;
        mViewArray[2] = mLlStep3;
        mAdapter = new MyPageAdapter<View>(this, mViewArray);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(REGISTER_STEP_COUNT);
        genderItems.add(getResources().getString(R.string.male));
        genderItems.add(getResources().getString(R.string.female));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next1:

                break;
            case R.id.tv_next2:

                break;
            case R.id.tv_register:

                break;
            case R.id.iv_head_photo:
                startActivity(new Intent(RegisterActivity.this, ChooseLocalPictureActivity.class));
                break;
            case R.id.tv_birthday:
                selectBirthday();
                break;
            case R.id.tv_gender:
                selectGender();
                break;
//            case R.id.saveButton:
//                mUserPresenter.saveUser(getID(), getFristName(),
//                        getLastName());
//                break;
//            case R.id.loadButton:
//                mUserPresenter.loadUser(getID());
//                break;
//            default:
//                break;
        }
    }

    @Override
    public String getPhone() {
        return mEtPhone.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString().trim();
    }

    @Override
    public String getHeadPhotoUrl() {
        return "";
    }

    @Override
    public String getNickName() {
        return mEtNickName.getText().toString().trim();
    }

    @Override
    public int getHeight() {
        return Integer.valueOf(mEtHeight.getText().toString().trim());
    }

    @Override
    public int getWeight() {
        return Integer.valueOf(mEtWeight.getText().toString().trim());
    }

    @Override
    public Date getBirthday() {
        return null;
    }

    @Override
    public int getGender() {
        return 0;
    }

    @Override
    public String getUrgentPhone() {
        return mEtUrgentPhoto.getText().toString().trim();
    }
}
