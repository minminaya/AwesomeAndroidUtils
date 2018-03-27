package com.minminaya.aaudemo.activity;

import android.annotation.SuppressLint;
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

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int statusBarHeight = BarsHelper.getStatusBarOrNavigationHeight(BarsHelper.STATUS_BAR);
        int navigationHeight = BarsHelper.getStatusBarOrNavigationHeight(BarsHelper.NAVIGATION_BAR);
        Log.e("HomeActivity：", "当前statusBarHeight值：" + statusBarHeight);
        Log.e("HomeActivity：", "当前navigationHeight值：" + navigationHeight);

//        BarsHelper.addStatusBarViewAtDecorView(this, getResources().getColor(R.color.colorPrimaryDark), 1.0f);

//        BarsHelper.setStatusBarColor(this, Color.parseColor("#303F9F"),1f,true,BarsHelper.NO_STATUS_AND_NO_NAVIGATION);
//        BarsHelper.addGradientOrNormalStatusBarViewAtDecorView(this, true, -1, Color.parseColor("#303F9F"), 1f);
//        BarsHelper.addGradientOrNormalStatusBarViewAtDecorView(this, false, R.drawable.gradient_1, -1, 1f);

    }

}
