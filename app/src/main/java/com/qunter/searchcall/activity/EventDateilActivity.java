package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;
import com.qunter.searchcall.entity.EventInfo;

/**
 * Created by Administrator on 2017/4/19.
 */

public class EventDateilActivity extends BaseActivity {
    private EventInfo eventInfo;
    private TextView eventDateilTitleTv,eventDateilContentTv,eventDateilTimeStartTv,eventDateilTimeEndTv;
    @Override
    protected void initVariablesAndService() {
        eventInfo = (EventInfo) getIntent().getExtras().get("eventInfo");
    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_event_dateil);
        eventDateilTitleTv = (TextView) findViewById(R.id.event_detail_titleTv);
        eventDateilContentTv = (TextView) findViewById(R.id.event_detail_contentTv);
        eventDateilTimeStartTv = (TextView) findViewById(R.id.event_detail_timeStartTv);
        eventDateilTimeEndTv = (TextView) findViewById(R.id.event_detail_timeEndTv);

        eventDateilTitleTv.setText(eventInfo.getEventTitle());
        eventDateilContentTv.setText(eventInfo.getEventContent());
        eventDateilTimeStartTv.setText(eventInfo.getEventStartDate().getDate());
        eventDateilTimeEndTv.setText(eventInfo.getEventEndDate().getDate());
    }
}
