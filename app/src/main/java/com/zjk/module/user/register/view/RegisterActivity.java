package com.zjk.module.user.register.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zjk.common.ui.BaseActivity;
import com.zjk.run_help.R;

/**
 * author : ZhuangJinKun
 * e-mail : zhuangjinkun@bigo.sg
 * time   : 2018/03/28
 */

public class RegisterActivity extends BaseActivity implements IRegisterView, View.OnClickListener {

    private EditText mFirstNameEditText, mLastNameEditText, mIdEditText;
    private Button mSaveButton, mLoadButton;
//    private UserPresenter mUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupActionBar((Toolbar) findViewById(R.id.toolbar));
        findWidgets();
//        mUserPresenter = new UserPresenter(this);
//        mSaveButton.setOnClickListener(this);
//        mLoadButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
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
    public void setFirstName(String firstName) {
        // TODO Auto-generated method stub
        mFirstNameEditText.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        // TODO Auto-generated method stub
        mLastNameEditText.setText(lastName);
    }

    @Override
    public int getID() {
        // TODO Auto-generated method stub
        return Integer.parseInt(mIdEditText.getText().toString());
    }

    @Override
    public String getFristName() {
        // TODO Auto-generated method stub
        return mFirstNameEditText.getText().toString();
    }

    @Override
    public String getLastName() {
        // TODO Auto-generated method stub
        return mLastNameEditText.getText().toString();
    }

    void findWidgets() {
//        mFirstNameEditText = (EditText) findViewById(R.id.first_name_edt);
//        mLastNameEditText = (EditText) findViewById(R.id.last_name_edt);
//        mIdEditText = (EditText) findViewById(R.id.id_edt);
//
//        mSaveButton = (Button) findViewById(R.id.saveButton);
//        mLoadButton = (Button) findViewById(R.id.loadButton);
    }
}
