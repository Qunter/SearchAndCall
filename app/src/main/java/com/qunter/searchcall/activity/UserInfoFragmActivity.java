package com.qunter.searchcall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qunter.searchcall.R;
import com.qunter.searchcall.entity.UserInfo;

import cn.bmob.v3.BmobUser;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by Administrator on 2017/3/24.
 */

public class UserInfoFragmActivity extends Fragment implements View.OnClickListener{
    public static UserInfoFragmActivity newInstance() {
        return new UserInfoFragmActivity();
    }
    private TextView usermanegerBtn,settingsBtn,shareBtn,sentBtn,aboutBtn,userNickname;
    private ImageView userAvatar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_info_fragm, container, false);
        usermanegerBtn = (TextView) view.findViewById(R.id.user_info_usermanegerBtn);
        settingsBtn = (TextView) view.findViewById(R.id.user_info_settingsBtn);
        shareBtn = (TextView) view.findViewById(R.id.user_info_shareBtn);
        sentBtn = (TextView) view.findViewById(R.id.user_info_sentBtn);
        aboutBtn = (TextView) view.findViewById(R.id.user_info_aboutBtn);
        userNickname = (TextView) view.findViewById(R.id.user_info_nickname);
        userAvatar = (ImageView) view.findViewById(R.id.user_info_avatar);

        usermanegerBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        sentBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
        userNickname.setText(BmobUser.getCurrentUser(UserInfo.class).getUserNickname());
        Glide.with(getContext()).load(BmobUser.getCurrentUser(UserInfo.class).getUserAvatar()).bitmapTransform(new CropCircleTransformation(getContext())).into(userAvatar);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_info_usermanegerBtn:
                startActivity(new Intent(getContext(),UserManegerActivity.class));
                break;
            case R.id.user_info_settingsBtn:
                startActivity(new Intent(getContext(),UserSettingActivity.class));
                break;
            case R.id.user_info_shareBtn:
                startActivity(new Intent(getContext(),UserShareActivity.class));
                break;
            case R.id.user_info_sentBtn:
                startActivity(new Intent(getContext(),UserSentActivity.class));
                break;
            case R.id.user_info_aboutBtn:
                startActivity(new Intent(getContext(),UserAboutActivity.class));
                break;
        }
    }
}
