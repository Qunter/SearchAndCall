package com.qunter.searchcall.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Administrator on 2017/4/17.
 * 活动信息实体
 */

public class EventInfo extends BmobObject{
    private String eventTitle;
    private String eventPlace;
    private String eventContent;
    private BmobDate eventStartDate;
    private BmobDate eventEndDate;

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public BmobDate getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(BmobDate eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public BmobDate getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(BmobDate eventEndDate) {
        this.eventEndDate = eventEndDate;
    }
}
