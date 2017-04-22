package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;
import com.qunter.searchcall.entity.UserInfo;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/3/21.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText usernameEt,passwordEt;
    private Button loginBtn,registerBtn;
    @Override
    protected void initVariablesAndService() {
        //初始化bmob
        Bmob.initialize(this,"8da888d03200ff2f6b403d064b805d60");
        super.setIfImmersive(true);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if(bmobUser != null){
            startActivity(MainActivity.class);
            this.finish();
        }
        usernameEt = (EditText) findViewById(R.id.login_usernameEt);
        passwordEt = (EditText) findViewById(R.id.login_passwordEt);
        loginBtn = (Button) findViewById(R.id.login_loginBtn);
        loginBtn.setOnClickListener(this);
        registerBtn = (Button) findViewById(R.id.login_registerBtn);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_loginBtn:
                login();
                break;
            case R.id.login_registerBtn:
                startActivity(RegisterActivity.class);
                this.finish();
                break;
        }
    }
    /**
     * 在bmob数据库上验证账号并登录
     */
    private void login(){
        UserInfo userInformation = new UserInfo();
        userInformation.setUsername(usernameEt.getText().toString());
        userInformation.setPassword(passwordEt.getText().toString());
        userInformation.login(new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if(e==null){
                    //Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(MainActivity.class);
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText(LoginActivity.this,"账号或密码错误  请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
