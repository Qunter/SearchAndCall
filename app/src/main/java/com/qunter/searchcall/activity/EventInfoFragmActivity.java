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

public class EventInfoFragmActivity extends Fragment {
    public static EventInfoFragmActivity newInstance() {
        return new EventInfoFragmActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_event_info_fragm, container, false);
    }
}
