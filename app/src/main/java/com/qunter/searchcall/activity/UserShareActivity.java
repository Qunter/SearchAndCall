package com.qunter.searchcall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.qunter.searchcall.R;
import com.qunter.searchcall.base.BaseActivity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/10/24.
 */
public class UserShareActivity extends BaseActivity {
    private EditText editText;
    private Button share;
    private ImageView userShareBackBtn;
    @Override
    protected void initVariablesAndService() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_share);
        editText = (EditText)findViewById(R.id.share_edittext);
        share = (Button)findViewById(R.id.share);
        userShareBackBtn = (ImageView) findViewById(R.id.user_share_backBtn);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                showShare(new String("给你们推荐个应用"),str.equals("")?new String("我正在使用寻呼,你们也来试试吧!"):str);
            }
        });
        userShareBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void showShare(String str,String data) {
        ShareSDK.initSDK(getApplicationContext());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(str);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(data);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImageUrl("http://bmob-cdn-1675.b0.upaiyun.com/2016/05/22/14ab9b904024cc70809ed04d84208280.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(getApplicationContext());
    }
}