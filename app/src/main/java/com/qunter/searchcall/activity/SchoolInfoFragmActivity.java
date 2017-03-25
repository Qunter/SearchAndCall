package com.qunter.searchcall.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qunter.searchcall.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/24.
 */

public class SchoolInfoFragmActivity extends Fragment {
    private final int GETJSOUPCONTENT=0x00;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETJSOUPCONTENT:
                    new Thread(runnable).start();
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
        return inflater.inflate(R.layout.activity_school_info_fragm, container, false);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getJsoupContent();
        }
    };

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
            String mUrl = element.attr("abs:href");
            String mTitle = element.text();
            Log.e("mytag", mUrl);
            Log.e("mytag", mTitle );
        }
    }
}
