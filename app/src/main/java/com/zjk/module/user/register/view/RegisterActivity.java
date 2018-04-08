package com.zjk.module.user.register.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zjk.common.chooselocalpicture.ui.ChooseLocalPictureActivity;
import com.zjk.common.sp.SpEditor;
import com.zjk.common.sp.SpFileName;
import com.zjk.common.sp.SpKey;
import com.zjk.common.ui.BaseActivity;
import com.zjk.common.ui.MyPageAdapter;
import com.zjk.common.ui.NoScrollViewPager;
import com.zjk.common.ui.ShapedImageView;
import com.zjk.model.UserInfo;
import com.zjk.module.user.login.view.LoginActivity;
import com.zjk.module.user.register.model.RegisterModelImpl;
import com.zjk.module.user.register.present.RegisterPresenter;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.ContactUtil;
import com.zjk.util.DateUtil;
import com.zjk.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class RegisterActivity extends BaseActivity implements IRegisterView {

    private static final String TAG = "RegisterActivity";

    private static final int REGISTER_STEP_COUNT = 3;
    private static final int FOR_HEAD_PICTURE = 0;

    private ConstraintLayout mClContainer;
    private Toolbar mToolBar;
    private NoScrollViewPager mViewPager;
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

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findWidget();
        setListener();
        init();

        mPresenter = new RegisterPresenter(this, new RegisterModelImpl());
        mPresenter.start();
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
                mTVBirthday.setText(DateUtil.dateToString(date));
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

    @Override
    protected void findWidget() {
        mClContainer = (ConstraintLayout) findViewById(R.id.cl_container);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_register);

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

    public void switchContent(int pageIndex) {
        mViewPager.setCurrentItem(pageIndex, false);
    }

    private void checkStep1() {
        if (!ContactUtil.checkMobile(mEtPhone.getText().toString().trim())) {
            ToastUtil.shortShow(RegisterActivity.this, R.string.error_phone_format);
            return;
        }
        if (mEtPassword.getText().toString().trim().length() < 6) {
            ToastUtil.shortShow(RegisterActivity.this, R.string.error_password_format);
            return;
        }
        if (!mEtPassword.getText().toString().trim().equals(mEtConfirmPassword.getText().toString().trim())) {
            ToastUtil.shortShow(RegisterActivity.this, R.string.error_password_confirm);
            return;
        }
        switchContent(1);
    }

    private void checkStep2() {
        // 头像

        if (TextUtils.isEmpty(mEtNickName.getText().toString().trim())) {
            ToastUtil.shortShow(RegisterActivity.this, R.string.error_nick_name);
            return;
        }
        if (TextUtils.isEmpty(mEtHeight.getText().toString().trim())) {
            ToastUtil.shortShow(RegisterActivity.this, R.string.error_height);
            return;
        }
        if (TextUtils.isEmpty(mEtWeight.getText().toString().trim())) {
            ToastUtil.shortShow(RegisterActivity.this, R.string.error_weight);
            return;
        }
        if (TextUtils.isEmpty(mTVBirthday.getText().toString().trim())) {
            ToastUtil.shortShow(RegisterActivity.this, R.string.error_birthday);
            return;
        }
        switchContent(2);
    }

    private void register() {
        if (!ContactUtil.checkMobile(mEtUrgentPhoto.getText().toString().trim())) {
            ToastUtil.shortShow(RegisterActivity.this, R.string.error_urgent_phone);
            return;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(mEtPhone.getText().toString().trim());
        userInfo.setPassword(mEtPassword.getText().toString().trim());
        userInfo.setUserName(mEtNickName.getText().toString().trim());
        userInfo.setHeight(Integer.valueOf(mEtHeight.getText().toString().trim()));
        userInfo.setWeight(Integer.valueOf(mEtWeight.getText().toString().trim()));
        userInfo.setBirthday(DateUtil.stringToDate(mTVBirthday.getText().toString().trim()));
        String gender = mTVGender.getText().toString().trim();
        userInfo.setGender((TextUtils.isEmpty(gender) ? 2 : (gender.equals(getResources().getString(R.string.male)) ? 0 : 1)));
        userInfo.setUrgentPhone(mEtUrgentPhoto.getText().toString().trim());
        mPresenter.doRegister(userInfo);
    }

    private void showRegisterSucDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        SpEditor.putAndApply(SpFileName.SP_USER, SpKey.USER_PHONE, mEtPhone.getText().toString().trim());
                        SpEditor.putAndApply(SpFileName.SP_USER, SpKey.USER_PASSWORD, mEtPassword.getText().toString().trim());
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setMessage(R.string.register_success);
        alertDialogBuilder.show();
    }

    private void showHasRegisteredDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setMessage(R.string.register_has);
        alertDialogBuilder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next1:
                checkStep1();
                break;
            case R.id.tv_next2:
                checkStep2();
                break;
            case R.id.tv_register:
                register();
                break;
            case R.id.iv_head_photo:
                hideKeyboard(mIvHeadPhoto);
                ChooseLocalPictureActivity.start(RegisterActivity.this, FOR_HEAD_PICTURE);
                break;
            case R.id.tv_birthday:
                hideKeyboard(mTVBirthday);
                selectBirthday();
                break;
            case R.id.tv_gender:
                hideKeyboard(mTVGender);
                selectGender();
                break;
        }
    }

    @Override
    public void showProgress() {
        showLoadingDialog(R.string.register_ing);
    }

    @Override
    public void hideProgress() {
        dismissLoadingDialog();
    }

    @Override
    public void registerFail(boolean onUIThread, Result result) {
        if (result.status == 2 && onUIThread) {
            showHasRegisteredDialog();
        } else if (onUIThread) {
            ToastUtil.shortShow(RegisterActivity.this, result.errMsg);
        }
    }

    @Override
    public void registerSuccess(boolean onUIThread) {
        if (onUIThread) {
            showRegisterSucDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
