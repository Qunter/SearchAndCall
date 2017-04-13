package com.qunter.searchcall.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */

public class SchoolDateilActivity extends BaseActivity {
    private String dateilUrl,detailContent;
    private List<String> dateilImgUrl = new ArrayList<String>();
    private List<Bitmap> dateilBitmap = new ArrayList<Bitmap>();
    private Spanned contentsp;
    private TextView detailTitleTv,detailTimeTv,detailContentTv;
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
                    break;
            }
        }
    };
    @Override
    protected void initVariablesAndService() {
        dateilUrl = getIntent().getExtras().get("dateilUrl").toString();
        handler.sendEmptyMessage(GETDETAILDATA);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dateil);
        detailTitleTv = (TextView) findViewById(R.id.detail_title);
        detailTimeTv = (TextView) findViewById(R.id.detail_time);
        detailContentTv = (TextView) findViewById(R.id.detail_content);
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
        doc.select("img").remove();
        Element element = doc.select("span").first();
        Elements detailImgUrlEles = element.select("img");
        for(Element et:detailImgUrlEles){
            dateilImgUrl.add(et.absUrl("src"));
        }
        Log.e("ttag", element.toString());
        detailContent = element.toString();
        detailContent = detailContent.replace("。<br>","。<br> <br>");
        detailContent = detailContent.replace("<br> <br> <br> <br> <br>","<br> <br>");
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
}
