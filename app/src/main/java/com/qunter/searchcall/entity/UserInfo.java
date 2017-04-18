package com.qunter.searchcall.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/3/23.
 */

public class UserInfo extends BmobUser {
    private String userNickname;
    private String userPhone;

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
