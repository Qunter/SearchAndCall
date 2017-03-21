package com.qunter.searchcall.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/3/21.
 */

public abstract class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initViews(savedInstanceState);
        initState();
    }
    //初始化变量
    protected abstract void initVariables();
    //初始化控件
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     *
     */
    private void initState() {
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
}
