package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;
import com.qunter.searchcall.engine.timePicket.TimePickerShow;
import com.qunter.searchcall.entity.EventInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/4/18.
 */

public class EventCreateActivity extends BaseActivity implements View.OnClickListener{
    private EditText eventTitleEt,eventContentEt;
    private TimePickerShow timePickerShow;
    private LinearLayout timeChooseView;
    private Button eventGetEventStartTimeBtn,eventGetEventEndTimeBtn,eventSubmitBtn;
    private Date eventStartDate = null,eventEndDate = null;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_event_create);
        timeChooseView = (LinearLayout) findViewById(R.id.timeChooseView);
        timePickerShow = new TimePickerShow(getApplicationContext());
        timeChooseView.addView(timePickerShow.timePickerView());
        eventTitleEt = (EditText) findViewById(R.id.event_titleEt);
        eventContentEt = (EditText) findViewById(R.id.event_contentEt);
        eventGetEventStartTimeBtn = (Button) findViewById(R.id.event_getEventStartTimeBtn);
        eventGetEventStartTimeBtn.setOnClickListener(this);
        eventGetEventEndTimeBtn = (Button) findViewById(R.id.event_getEventEndTimeBtn);
        eventGetEventEndTimeBtn.setOnClickListener(this);
        eventSubmitBtn = (Button) findViewById(R.id.event_submitBtn);
        eventSubmitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_submitBtn: {
                String eventSubmitTitle = eventTitleEt.getText().toString();
                String eventSubmitContent = eventContentEt.getText().toString();
                if (eventSubmitTitle.equals("") || eventSubmitContent.equals("")||eventGetEventStartTimeBtn.getText().toString().equals("获取活动开始时间")||eventGetEventEndTimeBtn.getText().toString().equals("获取活动结束时间")) {
                    Toast.makeText(getApplicationContext(), "标题、内容为空或活动时间未获取  请检查后再次提交", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    BmobDate SubmitStartDate = new BmobDate(eventStartDate);
                    BmobDate SubmitEndDate = new BmobDate(eventEndDate);
                    EventInfo eventInfo = new EventInfo();
                    eventInfo.setEventTitle(eventSubmitTitle);
                    eventInfo.setEventContent(eventSubmitContent);
                    eventInfo.setEventStartDate(SubmitStartDate);
                    eventInfo.setEventEndDate(SubmitEndDate);
                    eventInfo.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                Toast.makeText(getApplicationContext(), "活动发布成功", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "活动发布失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                break;
            }
            case R.id.event_getEventStartTimeBtn:{
                try {
                    SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String DateString = timePickerShow.getTxtTime("-", "-", " ", ":", ":", "");
                    eventStartDate = mSDF.parse(DateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                eventGetEventStartTimeBtn.setText(timePickerShow.getTxtTime("-", "-", " ", ":", ":", ""));
                break;
            }
            case R.id.event_getEventEndTimeBtn:{
                try {
                    SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String DateString = timePickerShow.getTxtTime("-", "-", " ", ":", ":", "");
                    eventEndDate = mSDF.parse(DateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                eventGetEventEndTimeBtn.setText(timePickerShow.getTxtTime("-", "-", " ", ":", ":", ""));
                break;
            }
        }
    }
}
