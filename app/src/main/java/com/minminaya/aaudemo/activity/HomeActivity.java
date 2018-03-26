package com.minminaya.aaudemo.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.minminaya.aau.utils.BarsHelper;
import com.minminaya.aaudemo.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int statusBarHeight = BarsHelper.getStatusBarOrNavigationHeight(BarsHelper.STATUS_BAR_HEIGHT);
        int navigationHeight = BarsHelper.getStatusBarOrNavigationHeight(BarsHelper.NAVIGATION_BAR_HEIGHT);
        Log.e("HomeActivity：", "当前statusBarHeight值：" + statusBarHeight);
        Log.e("HomeActivity：", "当前navigationHeight值：" + navigationHeight);

//        BarsHelper.addStatusBarViewAtDecorView(this, getResources().getColor(R.color.colorPrimaryDark), 1.0f);

        BarsHelper.setStatusTransparent(this, Color.parseColor("#303F9F"));
    }

}
