package com.qunter.searchcall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.qunter.searchcall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private AHBottomNavigation bottomNavigationView;
    private List<Fragment> fragments = new ArrayList<>();
    private int currentTabIndex;
    private TextView titleTv;
    private ImageView toolbarAddBtn;
    private String TitleString[] = {"学校资讯","周边活动","我的好友","我的设置"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    protected void initViews() {
        //标题栏标题textview初始化
        titleTv = (TextView) findViewById(R.id.toolbarTitleTv);
        fragments.add(SchoolInfoFragmActivity.newInstance());
        fragments.add(EventInfoFragmActivity.newInstance());
        fragments.add(FriendInfoFragmActivity.newInstance());
        fragments.add(UserInfoFragmActivity.newInstance());
        //活动&好友添加按钮初始化
        toolbarAddBtn = (ImageView) findViewById(R.id.toolbarAddBtn);
        toolbarAddBtn.setOnClickListener(this);
        toolbarAddBtn.setVisibility(View.GONE);

        showFragment(fragments.get(0));
        //标题栏内容随fragment切换而改变
        titleTv.setText(TitleString[0]);
        initBottomNav();

    }
    private void initBottomNav() {
        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.bottom_navigation_view);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(TitleString[0],
                R.drawable.ic_tab_temporary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(TitleString[1],
                R.drawable.ic_tab_temporary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(TitleString[2],
                R.drawable.ic_tab_temporary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(TitleString[3],
                R.drawable.ic_tab_temporary);

        bottomNavigationView.addItem(item1);
        bottomNavigationView.addItem(item2);
        bottomNavigationView.addItem(item3);
        bottomNavigationView.addItem(item4);

        bottomNavigationView.setColored(false);
        bottomNavigationView.setForceTint(false);
        bottomNavigationView.setBehaviorTranslationEnabled(true);
        bottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigationView.setAccentColor(getResources().getColor(R.color.black_90));
        bottomNavigationView.setInactiveColor(getResources().getColor(R.color.nav_text_color_mormal));
        bottomNavigationView.setCurrentItem(0);
        bottomNavigationView.setDefaultBackgroundColor(
                getResources().getColor(R.color.bottom_tab_bar_color));
        bottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (currentTabIndex != position) {
                    FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                    trx.hide(fragments.get(currentTabIndex));
                    if(position==1||position==2){
                        toolbarAddBtn.setVisibility(View.VISIBLE);
                    }else{
                        toolbarAddBtn.setVisibility(View.GONE);
                    }
                    if (!fragments.get(position).isAdded()) {
                        trx.add(R.id.content, fragments.get(position));
                    }
                    trx.show(fragments.get(position)).commit();
                    titleTv.setText(TitleString[position]);
                }
                currentTabIndex = position;
                return true;
            }
        });
    }
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.toolbarAddBtn){
            if(currentTabIndex==1){
                Intent intent = new Intent(this,EventCreateActivity.class);
                startActivity(intent);
            }else{

            }
        }
    }
}
