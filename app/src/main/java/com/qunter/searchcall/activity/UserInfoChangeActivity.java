package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/4.
 */

public class UserInfoChangeActivity extends BaseActivity implements View.OnClickListener{
    private ImageView userInfoChangeBackBtn;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info_change);
        userInfoChangeBackBtn = (ImageView) findViewById(R.id.user_info_change_backBtn);

        userInfoChangeBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_info_change_backBtn:
                finish();
        }
    }
}
