package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class MainActivity extends AppCompatActivity {
    private AHBottomNavigation bottomNavigationView;
    private List<Fragment> fragments = new ArrayList<>();
    private int currentTabIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    protected void initViews() {
        fragments.add(SchoolInfoFragmActivity.newInstance());
        fragments.add(EventInfoFragmActivity.newInstance());
        fragments.add(FriendInfoFragmActivity.newInstance());
        fragments.add(UserInfoFragmActivity.newInstance());

        showFragment(fragments.get(0));
        initBottomNav();

    }
    private void initBottomNav() {
        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.bottom_navigation_view);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("学校资讯",
                R.drawable.ic_tab_temporary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("周边活动",
                R.drawable.ic_tab_temporary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("我的好友",
                R.drawable.ic_tab_temporary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("我的设置",
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
                    if (!fragments.get(position).isAdded()) {
                        trx.add(R.id.content, fragments.get(position));
                    }
                    trx.show(fragments.get(position)).commit();
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
}
