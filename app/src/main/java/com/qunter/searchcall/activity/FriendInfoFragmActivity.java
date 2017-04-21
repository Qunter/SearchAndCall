package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qunter.searchcall.R;
import com.qunter.searchcall.adapter.FriendPagerAdapter;

/**
 * Created by Administrator on 2017/3/24.
 */

public class FriendInfoFragmActivity extends Fragment {
    public static FriendInfoFragmActivity newInstance() {
        return new FriendInfoFragmActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_friend_info_fragm, container, false);
        //Fragment+ViewPager+FragmentViewPager组合的使用
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.friendViewPaper);
        FriendPagerAdapter adapter = new FriendPagerAdapter(getActivity().getSupportFragmentManager(), getContext());
        viewPager.setAdapter(adapter);
        //TabLayout
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.friendTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
