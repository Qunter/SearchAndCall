package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qunter.searchcall.R;
import com.qunter.searchcall.adapter.EventPagerAdapter;

/**
 * Created by Administrator on 2017/3/24.
 */

public class EventInfoFragmActivity extends Fragment {
    public static EventInfoFragmActivity newInstance() {
        return new EventInfoFragmActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event_info_fragm,container,false);
        //Fragment+ViewPager+FragmentViewPager组合的使用
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.eventViewPaper);
        EventPagerAdapter adapter = new EventPagerAdapter(getActivity().getSupportFragmentManager(), getContext());
        viewPager.setAdapter(adapter);
        //TabLayout
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.eventTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
