package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;
import com.qunter.searchcall.entity.UserInfo;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/3/23.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText usernameEt,passwordEt,userPhoneEt,userNicknameEt;
    private Button registerBtn,loginBtn;
    private UserInfo userInformation;
    @Override
    protected void initVariablesAndService() {
        Bmob.initialize(this,"8da888d03200ff2f6b403d064b805d60");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        usernameEt = (EditText) findViewById(R.id.register_usernameEt);
        passwordEt = (EditText) findViewById(R.id.register_passwordEt);
        userPhoneEt = (EditText) findViewById(R.id.register_userPhoneEt);
        userNicknameEt = (EditText) findViewById(R.id.register_userNicknameEt);
        registerBtn = (Button) findViewById(R.id.register_registerBtn);
        registerBtn.setOnClickListener(this);
        loginBtn = (Button) findViewById(R.id.register_loginBtn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_registerBtn:
                register();
                break;
            case R.id.register_loginBtn:
                startActivity(LoginActivity.class);
                this.finish();
                break;
        }
    }
    /**
     * 注册账号至bmob
     */
    private void register(){
        userInformation = new UserInfo();
        userInformation.setUsername(usernameEt.getText().toString().trim());
        userInformation.setPassword(passwordEt.getText().toString().trim());
        userInformation.setUserPhone(userPhoneEt.getText().toString().trim());
        userInformation.setUserNickname(userNicknameEt.getText().toString().trim());
        userInformation.signUp(new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo s, BmobException e) {
                if(e==null){
                    Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                    userInformation.login(new SaveListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if(e==null){
                                Toast.makeText(RegisterActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                                startActivity(MainActivity.class);
                                RegisterActivity.this.finish();
                            }
                        }
                    });
                }else{
                    Log.e("失败代码",e.toString());
                }

            }
        });
    }
}
