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

        BarsHelper.addGradientOrNormalStatusBarViewAtDecorView(this, false, R.drawable.gradient_1, -1, 1.0f);
//        BarsHelper.setStatusTransparentAndColor(this,  getResources().getColor(R.color.colorPrimaryDark), 0.0f);
    }


}
