package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qunter.searchcall.R;
import com.qunter.searchcall.adapter.EventInfoListAdapter;
import com.qunter.searchcall.entity.EventInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/4/17.
 */

public class EventPageFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private int mPage;
    private SwipeRefreshLayout eventSwipeRefreshLayout;
    private EventInfoListAdapter adapter;
    private List<EventInfo> aroundEventData, mineEventData;
    private RecyclerView eventRecyclerView;
    private final int GETAROUNDEVENTDATA=0x00,GETMINEEVENTDATA=0x01,REFRESHAROUNDEVENT=0x02,REFRESHMINEEVENT=0x03;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETAROUNDEVENTDATA:
                    getAroundEventData();
                    break;
                case GETMINEEVENTDATA:
                    getMineEventData();
                    break;
                case REFRESHAROUNDEVENT:
                    loadAroundEventRecycleView();
                    break;
                case REFRESHMINEEVENT:
                    loadMineEventRecycleView();
                    break;
            }
        }
    };
    public static EventPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        EventPageFragment fragment = new EventPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event_list,container,false);
        eventSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_event);
        eventRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_event);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        eventRecyclerView.setLayoutManager(layoutManager);
        handler.sendEmptyMessage(GETAROUNDEVENTDATA);
        return view;
    }
    private void getAroundEventData(){
        BmobQuery<EventInfo> aroundEventQuery = new BmobQuery<EventInfo>();
        //执行查询方法
        aroundEventQuery.findObjects(new FindListener<EventInfo>() {
            @Override
            public void done(List<EventInfo> object, BmobException e) {
                if(e==null){
                    aroundEventData = object;
                    handler.sendEmptyMessage(REFRESHAROUNDEVENT);
                }else{
                }
            }
        });
    }
    private void getMineEventData(){
        BmobQuery<EventInfo> aroundEventQuery = new BmobQuery<EventInfo>();
        //执行查询方法
        aroundEventQuery.findObjects(new FindListener<EventInfo>() {
            @Override
            public void done(List<EventInfo> object, BmobException e) {
                if(e==null){
                    mineEventData = object;
                    handler.sendEmptyMessage(REFRESHMINEEVENT);
                }else{
                }
            }
        });
    }
    private void loadAroundEventRecycleView(){
        adapter = new EventInfoListAdapter(getContext(),aroundEventData,eventRecyclerView);
        eventRecyclerView.setAdapter(adapter);
    }
    private void loadMineEventRecycleView(){
        adapter = new EventInfoListAdapter(getContext(),mineEventData,eventRecyclerView);
        eventRecyclerView.setAdapter(adapter);
    }
}