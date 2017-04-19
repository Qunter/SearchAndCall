package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;
import com.qunter.searchcall.engine.GlideImageLoader;
import com.youth.banner.Banner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */

public class SchoolDateilActivity extends BaseActivity {
    private String dateilUrl,detailContent;
    private List<String> dateilImgUrl = new ArrayList<String>();
    private Spanned contentsp;
    private TextView detailTitleTv,detailTimeTv,detailContentTv;
    private Banner banner;
    private String detailTitleString,detailTimeString;
    private final int GETDETAILDATA=0x00,PUTVIEWSDATA=0X01;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETDETAILDATA:
                    new Thread(deailDataRunnable).start();
                    break;
                case PUTVIEWSDATA:
                    putViewsData();
                    initBanner();
                    detailTitleTv.setText(detailTitleString);
                    detailTimeTv.setText(detailTimeString);
                    break;
            }
        }
    };
    @Override
    protected void initVariablesAndService() {
        //获取详情页链接
        dateilUrl = getIntent().getExtras().get("dateilUrl").toString();
        handler.sendEmptyMessage(GETDETAILDATA);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_school_dateil);
        detailTitleTv = (TextView) findViewById(R.id.detail_title);
        detailTimeTv = (TextView) findViewById(R.id.detail_time);
        detailContentTv = (TextView) findViewById(R.id.detail_content);
        banner = (Banner) findViewById(R.id.detail_image);
    }

    Runnable deailDataRunnable = new Runnable() {
        @Override
        public void run() {
            getDetailData();
        }
    };
    /**
     * 获取详情页所需数据
     */
    private void getDetailData(){
        Connection conn = Jsoup.connect(dateilUrl);
        // 修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        detailTitleString = doc.select("[class=listpicabs_text_title]").text();
        detailTimeString = doc.select("[class=listpicabs_text_info]").text();
        detailTimeString  = detailTimeString.substring(6,16);
        Elements detailImgUrlEles = doc.select("[class=listpicabs_text_show]").select("img");
        Element element = doc.select("span").first();
        doc.select("img").remove();
        for(Element et:detailImgUrlEles){
            dateilImgUrl.add(et.absUrl("src"));
            Log.e("detailimg", et.absUrl("src"));
        }
        Log.e("ttag", element.toString());
        detailContent = element.toString();
        while(detailContent.indexOf("<br> <br>")!=-1){
            detailContent = detailContent.replace("<br> <br>","<br>");
        }
        detailContent = detailContent.replace("。<br>","。<br> <br>");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            contentsp = Html.fromHtml(detailContent,Html.FROM_HTML_MODE_LEGACY);
        } else {
            contentsp = Html.fromHtml(detailContent);
        }
        handler.sendEmptyMessage(PUTVIEWSDATA);
    }
    private void putViewsData(){
        detailContentTv.setText(contentsp);
    }
    /**
     * 初始化Banner轮播
     */
    private void initBanner(){
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(dateilImgUrl);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
}
