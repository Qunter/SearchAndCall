package com.qunter.searchcall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    public static final String EVENTMODE = "eventmode";
    private int eventmode = 0;
    private SwipeRefreshLayout eventSwipeRefreshLayout;
    private EventInfoListAdapter adapter;
    private List<EventInfo> aroundEventData, mineEventData;
    private RecyclerView eventRecyclerView;
    private boolean ifFirstInitData=true;
    private final int GETAROUNDEVENTDATA=0x00,GETMINEEVENTDATA=0x01,REFRESHAROUNDEVENT=0x02,REFRESHMINEEVENT=0x03,REFRESH=0x04;
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
                    if(ifFirstInitData){
                        ifFirstInitData = false;
                    }else{
                        handler.sendEmptyMessage(REFRESH);
                    }
                    break;
                case REFRESHMINEEVENT:
                    loadMineEventRecycleView();
                    if(ifFirstInitData){
                        ifFirstInitData = false;
                    }else{
                        handler.sendEmptyMessage(REFRESH);
                    }
                    break;
                case REFRESH:
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                    eventSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    public static EventPageFragment newInstance(int eventmode) {
        Bundle args = new Bundle();
        args.putInt(EVENTMODE, eventmode);
        EventPageFragment fragment = new EventPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventmode = getArguments().getInt(EVENTMODE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_event_list,container,false);
        eventSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_event);
        eventSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        eventRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_event);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        eventRecyclerView.setLayoutManager(layoutManager);
        initData();
        return view;
    }
    /**
     * 根据eventmode判断是周边活动页还是我的活动页并发送相应的msg以获取对应页面所需数据
     */
    private void initData(){
        if(eventmode==0){
            handler.sendEmptyMessage(GETAROUNDEVENTDATA);
        }else if (eventmode==1){
            handler.sendEmptyMessage(GETMINEEVENTDATA);
        }
    }
    /**
     * 查询周边活动
     */
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
    /**
     * 查询我的活动
     */
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
    /**
     * 加载周边活动数据至RecycleView
     */
    private void loadAroundEventRecycleView(){
        adapter = new EventInfoListAdapter(getContext(),aroundEventData,eventRecyclerView);
        adapter.setOnItemClickListener(new EventInfoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(),EventDetailActivity.class);
                intent.putExtra("eventInfo",aroundEventData.get(position));
                startActivity(intent);
            }
        });
        eventRecyclerView.setAdapter(adapter);
    }
    /**
     * 加载我的活动数据至RecycleView
     */
    private void loadMineEventRecycleView(){
        adapter = new EventInfoListAdapter(getContext(),mineEventData,eventRecyclerView);
        adapter.setOnItemClickListener(new EventInfoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(),EventDetailActivity.class);
                intent.putExtra("eventInfo",mineEventData.get(position));
                startActivity(intent);
            }
        });
        eventRecyclerView.setAdapter(adapter);
    }
}