package com.qunter.searchcall.activity;


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
    private List<SchoolInfo> schoolInfoList = new ArrayList<>();
    private final int GETJSOUPCONTENT=0x00,INITRECYLERVIEW=0x01;
    private SwipeRefreshLayout schoolSwipeRefreshLayout;
    private RecyclerView schoolRecyclerView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETJSOUPCONTENT:
                    new Thread(runnable).start();
                    break;
                case INITRECYLERVIEW:
                    SchoolInfoListAdapter adapter = new SchoolInfoListAdapter(schoolInfoList);
                    schoolRecyclerView.setAdapter(adapter);
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
        handler.sendEmptyMessage(GETJSOUPCONTENT);
        View view = inflater.inflate(R.layout.activity_school_info_fragm, container, false);
        schoolSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_school);
        schoolRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_school);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        schoolRecyclerView.setLayoutManager(layoutManager);
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
        /*
        // 获取tbody元素下的所有tr元素
        Elements elements = doc.select("tbody tr");
        for(Element element : elements) {
            String companyName = element.getElementsByTag("company").text();
            Log.e("jsoup", "公司："+companyName );
            Log.e("jsoup", "---------------------------------" );
        }
        */
        //“椒麻鸡”和它对应的图片都在<div class="pic">中
        Elements elements = doc.select("[class=tabContent blog]");
        Elements elements1 = elements.select("font");
        Elements elements2 = elements1.select("a");
        for(Element element : elements2){
            String mTitle = element.text();
            String mUrl = element.attr("abs:href");
            schoolInfoList.add(new SchoolInfo(mTitle,mUrl));
            Log.e("mytag", mUrl);
            Log.e("mytag", mTitle );
        }
        handler.sendEmptyMessage(INITRECYLERVIEW);
    }
    /**
     * 加载下拉刷新组件
     */
    private void initSwipeRefreshLayout(){

    }
    /**
     * 加载RecyclerView
     */
    private void initRecyclerView(){

    }
}
