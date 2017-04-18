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
import com.qunter.searchcall.adapter.SchoolInfoListAdapter;
import com.qunter.searchcall.entity.SchoolInfo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */

public class SchoolInfoFragmActivity extends Fragment {
    private List<SchoolInfo> schoolInfoList;
    private final int GETJSOUPCONTENT=0x00,INITRECYLERVIEW=0x01,REFRESH=0x02;
    private SwipeRefreshLayout schoolSwipeRefreshLayout;
    private SchoolInfoListAdapter adapter;
    private RecyclerView schoolRecyclerView;
    private boolean ifFirstInitData=true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETJSOUPCONTENT:
                    new Thread(runnable).start();
                    break;
                case INITRECYLERVIEW:
                    adapter = new SchoolInfoListAdapter(getContext(),schoolInfoList,schoolRecyclerView);
                    adapter.setOnItemClickListener(new SchoolInfoListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //Log.e("onitemclickTag", schoolInfoList.get(position).getTitle());
                            Intent intent = new Intent(getContext(),SchoolDateilActivity.class);
                            intent.putExtra("dateilUrl",schoolInfoList.get(position).getPageUrl());
                            startActivity(intent);
                        }
                    });
                    schoolRecyclerView.setAdapter(adapter);
                    break;
                case REFRESH:
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                    schoolSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    public static SchoolInfoFragmActivity newInstance() {
        return new SchoolInfoFragmActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_school_info_fragm, container, false);
        schoolSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_school);
        schoolSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(GETJSOUPCONTENT);
            }
        });
        schoolRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_school);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        schoolRecyclerView.setLayoutManager(layoutManager);
        handler.sendEmptyMessage(GETJSOUPCONTENT);
        return view;

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getJsoupContent();
        }
    };
    /**
     * 使用jsoup获取学校官网数据
     */
    private void getJsoupContent(){
        schoolInfoList = new ArrayList<>();
        String url = "http://www.jxut.edu.cn/";
        Connection conn = Jsoup.connect(url);
        // 修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = doc.select("[class=tabContent blog]");
        Elements elements1 = elements.select("font");
        Elements elements2 = elements1.select("a");
        Document dt = null;
        for(Element element : elements2){
            String Title = element.text();
            String PageUrl = element.attr("abs:href");
            Connection ct = Jsoup.connect(PageUrl);
            ct.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
            try {
                dt = ct.get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements elements3 = dt.select("[class=listpicabs_text_show]");
            String ImgUrl = "";
            if(elements3.select("img").first()==null){
            }else{
                ImgUrl = elements3.select("img").first().absUrl("src");
            }
            schoolInfoList.add(new SchoolInfo(Title,PageUrl,ImgUrl));
            Log.e("mytag", Title);
            Log.e("mytag", PageUrl );
            Log.e("mytag", ImgUrl );
        }
        if (ifFirstInitData){
            ifFirstInitData=false;
            handler.sendEmptyMessage(INITRECYLERVIEW);
        }else{
            handler.sendEmptyMessage(REFRESH);
        }

    }

}
