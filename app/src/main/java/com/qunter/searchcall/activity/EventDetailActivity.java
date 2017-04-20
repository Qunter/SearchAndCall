package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;
import com.qunter.searchcall.entity.EventInfo;

/**
 * Created by Administrator on 2017/4/19.
 */

public class EventDetailActivity extends BaseActivity {
    private EventInfo eventInfo;
    private TextView eventDetailTitleTv, eventDetailContentTv, eventDetailTimeStartTv, eventDetailTimeEndTv;
    private ImageView eventDetailBackBtn;
    @Override
    protected void initVariablesAndService() {
        eventInfo = (EventInfo) getIntent().getExtras().get("eventInfo");
    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_event_detail);
        eventDetailTitleTv = (TextView) findViewById(R.id.event_detail_titleTv);
        eventDetailContentTv = (TextView) findViewById(R.id.event_detail_contentTv);
        eventDetailTimeStartTv = (TextView) findViewById(R.id.event_detail_timeStartTv);
        eventDetailTimeEndTv = (TextView) findViewById(R.id.event_detail_timeEndTv);
        eventDetailBackBtn = (ImageView) findViewById(R.id.event_detail_backBtn);

        eventDetailTitleTv.setText(eventInfo.getEventTitle());
        eventDetailContentTv.setText(eventInfo.getEventContent());
        eventDetailTimeStartTv.setText(eventInfo.getEventStartDate().getDate());
        eventDetailTimeEndTv.setText(eventInfo.getEventEndDate().getDate());
        eventDetailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
