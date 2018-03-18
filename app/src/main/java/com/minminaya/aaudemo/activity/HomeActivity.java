package com.minminaya.aaudemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
    }
}
