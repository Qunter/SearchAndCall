package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;

/**
 * Created by Administrator on 2017/4/22.
 */

public class UserSettingActivity extends BaseActivity {
    private ImageView userSettingBackBtn;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_setting);
        userSettingBackBtn = (ImageView) findViewById(R.id.user_setting_backBtn);

        userSettingBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
