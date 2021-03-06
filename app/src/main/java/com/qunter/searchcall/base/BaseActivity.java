package com.qunter.searchcall.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/3/21.
 * Activity基类
 */

public abstract class BaseActivity extends Activity{
    //是否设置沉浸式
    private boolean IfTranslucent = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariablesAndService();
        initViews(savedInstanceState);
        if(IfTranslucent){
            initTranslucent();
        }

    }
    //初始化变量
    protected abstract void initVariablesAndService();
    //初始化控件
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 设置是否沉浸式  在子类中super调用
     */
    public void setIfTranslucent(boolean IfTranslucent){
        this.IfTranslucent = IfTranslucent;
    }
    /**
     * 动态设置状态栏  实现透明式状态栏
     */
    private void initTranslucent() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }

    /**
     * 跳转到新页面
     */
    protected void startActivity(Class classes){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), classes);
        //设置要跳转到的页面以及跳转时的动画
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
