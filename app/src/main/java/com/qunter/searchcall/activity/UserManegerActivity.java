package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/4/22.
 */

public class UserManegerActivity extends BaseActivity{
    private ImageView userManegerBackBtn;
    private Button userManegerExitBtn;
    private final int EXITAPP=0x00;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case EXITAPP:
                    exitApp();
                    break;
            }
        }
    };
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_maneger);
        userManegerBackBtn = (ImageView) findViewById(R.id.user_maneger_backBtn);
        userManegerExitBtn = (Button) findViewById(R.id.user_maneger_exit);

        userManegerBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userManegerExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"退出登录成功  请重新运行程序", Toast.LENGTH_LONG).show();
                handler.sendEmptyMessageDelayed(EXITAPP,1500);
            }
        });
    }
    private void exitApp(){
        BmobUser.logOut();   //清除缓存用户对象
        android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
        System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
    }
}
