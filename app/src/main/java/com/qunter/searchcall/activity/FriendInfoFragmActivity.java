package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qunter.searchcall.R;

/**
 * Created by Administrator on 2017/3/24.
 */

public class FriendInfoFragmActivity extends Fragment {
    public static SchoolInfoFragmActivity newInstance() {
        return new SchoolInfoFragmActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext().getApplicationContext(),"好友界面",Toast.LENGTH_SHORT);
        return inflater.inflate(R.layout.activity_friend_info_fragm, container, false);
    }
}
