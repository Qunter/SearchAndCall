package com.qunter.searchcall.entity;

/**
 * Created by Administrator on 2017/3/25.
 * 学校资讯实体
 */

public class SchoolInfo {
    //标题，item图标，页面url
    private String Title;
    private String ImgUrl;
    private String PageUrl;
    public SchoolInfo(String Title,String PageUrl,String ImgUrl){
        this.Title = Title;
        this.PageUrl = PageUrl;
        this.ImgUrl = ImgUrl;
    }

    public String getTitle() {
        return Title;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public String getPageUrl() {
        return PageUrl;
    }
}
