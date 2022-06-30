package com.yang.cae.util.xbasebrowerUtil;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.yang.cae.R;
import com.yang.xbasebrowser.activity.ActivityRouter;
import com.yang.xbasebrowser.browser.XBaseBrowserActivity;

public class XBaseBrowserUtil extends AppCompatActivity {

    public static void startActivity(Context context, String url){
        ActivityRouter.getInstance()
                .add(XBaseBrowserActivity.WEB_URL,url)
                .add(XBaseBrowserActivity.SHOW_TITLE_BAR,true)
                .add(XBaseBrowserActivity.STATUS_COLOR, R.color.main_color)//设置状态栏颜色，默认是蓝色
                .startActivity(context, XBaseBrowserActivity.class);
    }
}
