package com.zjk.module.user.information.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.zjk.common.app.App;
import com.zjk.common.ui.BaseFragment;
import com.zjk.model.UserInfo;
import com.zjk.module.user.information.model.PersonModelImpl;
import com.zjk.module.user.information.present.PersonPresenter;
import com.zjk.result.Result;
import com.zjk.run_help.R;
import com.zjk.util.CommonsUtil;
import com.zjk.util.ContactUtil;
import com.zjk.util.DateUtil;
import com.zjk.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/04/09
 */

public class PersonFragment extends BaseFragment implements IPersonView {

    private static final String TAG = "PersonFragment";

    private static final int CHANGE_INFO_REQUEST = 1;

    private View mView;

    private RelativeLayout mRlHeadPhoto;
    private RelativeLayout mRlBirthday;
    private RelativeLayout mRlGender;

    private ImageView mIvHeadPhoto;
    private EditText mEtPhone;
    private EditText mEtPassword;
    private EditText mEtNickName;
    private EditText mEtHeight;
    private EditText mEtWeight;
    private TextView mTvBirthday;
    private TextView mTvGender;
    private EditText mEtUrgentPhone;

    private FloatingActionButton mFabEdit;

    private PersonPresenter mPresenter;
    private UserInfo userInfo;
    private ArrayList<String> genderItems = new ArrayList<>();
    private boolean isEdit = false;

    public static PersonFragment newInstance() {
        return new PersonFragment();
    }

    public PersonFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_person, container, false);
        findWidget();
        setListener();
        init();
        mPresenter = new PersonPresenter(this, new PersonModelImpl());
        mPresenter.start();
        return mView;
    }

    @Override
    protected void findWidget() {
        mRlHeadPhoto = (RelativeLayout) mView.findViewById(R.id.rl_head_photo);
        mRlBirthday = (RelativeLayout) mView.findViewById(R.id.rl_birthday);
        mRlGender = (RelativeLayout) mView.findViewById(R.id.rl_gender);
        mIvHeadPhoto = (ImageView) mView.findViewById(R.id.iv_head_photo);
        mEtPhone = (EditText) mView.findViewById(R.id.et_phone);
        mEtPassword = (EditText) mView.findViewById(R.id.et_password);
        mEtNickName = (EditText) mView.findViewById(R.id.et_nick_name);
        mEtHeight = (EditText) mView.findViewById(R.id.et_height);
        mEtWeight = (EditText) mView.findViewById(R.id.et_weight);
        mTvBirthday = (TextView) mView.findViewById(R.id.tv_birthday);
        mTvGender = (TextView) mView.findViewById(R.id.tv_gender);
        mEtUrgentPhone = (EditText) mView.findViewById(R.id.et_urgent_phone);
        mFabEdit = (FloatingActionButton) mView.findViewById(R.id.fab_edit);
    }

    @Override
    protected void setListener() {
        mRlHeadPhoto.setOnClickListener(this);
        mRlBirthday.setOnClickListener(this);
        mRlGender.setOnClickListener(this);
        mFabEdit.setOnClickListener(this);
    }

    @Override
    protected void init() {
        userInfo = App.instance().getUserInfo();
        Glide.with(getContext())
                .load(userInfo.getHeadUrl())
                .into(mIvHeadPhoto);
        mEtPhone.setText(userInfo.getPhone());
        mEtPassword.setText(userInfo.getPassword());
        mEtNickName.setText(userInfo.getUserName());
        mEtWeight.setText(String.valueOf(userInfo.getWeight()));
        mEtHeight.setText(String.valueOf(userInfo.getHeight()));
        mTvBirthday.setText(DateUtil.dateToString(userInfo.getBirthday()));
        mTvGender.setText(CommonsUtil.getGender(getContext(), userInfo.getGender()));
        mEtUrgentPhone.setText(userInfo.getUrgentPhone());
        genderItems.add(getResources().getString(R.string.male));
        genderItems.add(getResources().getString(R.string.female));

        mRlHeadPhoto.setClickable(isEdit);
        mRlBirthday.setClickable(isEdit);
        mRlGender.setClickable(isEdit);
    }

    private void changeInfo() {
        if (!ContactUtil.checkMobile(mEtPhone.getText().toString().trim())) {
            ToastUtil.shortShow(getContext(), R.string.error_phone_format);
            return;
        }
        if (mEtPassword.getText().toString().trim().length() < 6) {
            ToastUtil.shortShow(getContext(), R.string.error_password_format);
            return;
        }
        // 头像

        if (TextUtils.isEmpty(mEtNickName.getText().toString().trim())) {
            ToastUtil.shortShow(getContext(), R.string.error_nick_name);
            return;
        }
        if (TextUtils.isEmpty(mEtHeight.getText().toString().trim())) {
            ToastUtil.shortShow(getContext(), R.string.error_height);
            return;
        }
        if (TextUtils.isEmpty(mEtWeight.getText().toString().trim())) {
            ToastUtil.shortShow(getContext(), R.string.error_weight);
            return;
        }
        if (TextUtils.isEmpty(mTvBirthday.getText().toString().trim())) {
            ToastUtil.shortShow(getContext(), R.string.error_birthday);
            return;
        }
        if (!ContactUtil.checkMobile(mEtUrgentPhone.getText().toString().trim())) {
            ToastUtil.shortShow(getContext(), R.string.error_urgent_phone);
            return;
        }

        userInfo = new UserInfo();
        userInfo.setuId(App.instance().getUserInfo().getuId());
        userInfo.setPhone(mEtPhone.getText().toString().trim());
        userInfo.setPassword(mEtPassword.getText().toString().trim());
        userInfo.setUserName(mEtNickName.getText().toString().trim());
        userInfo.setHeight(Integer.valueOf(mEtHeight.getText().toString().trim()));
        userInfo.setWeight(Integer.valueOf(mEtWeight.getText().toString().trim()));
        userInfo.setBirthday(DateUtil.stringToDate(mTvBirthday.getText().toString().trim()));
        String gender = mTvGender.getText().toString().trim();
        userInfo.setGender(CommonsUtil.genderToInt(getContext(), gender));
        userInfo.setUrgentPhone(mEtUrgentPhone.getText().toString().trim());
        mPresenter.doChangeInfo(userInfo);
    }

    private void changeEditTextState() {
        isEdit = !isEdit;
        mRlHeadPhoto.setClickable(isEdit);//
        mEtPhone.setFocusable(isEdit);
        mEtPassword.setFocusable(isEdit);
        mEtNickName.setFocusable(isEdit);
        mEtWeight.setFocusable(isEdit);
        mEtHeight.setFocusable(isEdit);
        mRlBirthday.setClickable(isEdit);//
        mRlGender.setClickable(isEdit);//
        mEtUrgentPhone.setFocusable(isEdit);

        mEtPhone.setFocusableInTouchMode(isEdit);
        mEtPassword.setFocusableInTouchMode(isEdit);
        mEtNickName.setFocusableInTouchMode(isEdit);
        mEtWeight.setFocusableInTouchMode(isEdit);
        mEtHeight.setFocusableInTouchMode(isEdit);
        mEtUrgentPhone.setFocusableInTouchMode(isEdit);

        if (isEdit) {
            mEtPhone.requestFocus();
            mFabEdit.setImageResource(R.drawable.ic_save);
        } else {
            mFabEdit.setImageResource(R.drawable.ic_edit);
        }
    }

    private void selectBirthday() {
        //时间选择器
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(1994, 0, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTvBirthday.setText(DateUtil.dateToString(date));
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
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String gender = genderItems.get(options1);
                mTvGender.setText(gender);
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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_head_photo:
                if (getActivity() instanceof IStartActivity) {
                    ((IStartActivity) getActivity()).startActivityForResult(CHANGE_INFO_REQUEST);
                }
                break;
            case R.id.rl_birthday:
                selectBirthday();
                break;
            case R.id.rl_gender:
                selectGender();
                break;
            case R.id.fab_edit:
                if (isEdit) {
                    changeInfo();
                } else {
                    changeEditTextState();
                }
                break;
        }
    }

    @Override
    public void showProgress(int msgId) {
        if (getActivity() instanceof IProgress) {
            ((IProgress) getActivity()).showLoadingProgress(msgId);
        }
    }

    @Override
    public void hideProgress() {
        if (getActivity() instanceof IProgress) {
            ((IProgress) getActivity()).dismissLoadingProgress();
        }
    }

    @Override
    public void changeInfoFail(boolean onUIThread, Result result) {
        if (onUIThread) {
            ToastUtil.shortShow(getContext(), result.errMsg);
        }
    }

    @Override
    public void changeInfoSuccess(boolean onUIThread, boolean bool) {
        if (onUIThread && bool) {
            changeEditTextState();
            ToastUtil.shortShow(getContext(), R.string.change_info_success);
            mPresenter.getUpdateInfo(userInfo);
        } else if (onUIThread) {
            ToastUtil.shortShow(getContext(), R.string.change_info_fail);
        }
    }

    @Override
    public void getUpdateInfoFail(boolean onUIThread, Result result) {
        if (onUIThread) {
            ToastUtil.shortShow(getContext(), result.errMsg);
        }
    }

    @Override
    public void getUpdateInfoSuccess(boolean onUIThread, UserInfo userInfo) {
        if (onUIThread) {
            App.instance().setUserInfo(userInfo);
            ToastUtil.shortShow(getContext(), R.string.get_update_info_success);
        }
    }
}
