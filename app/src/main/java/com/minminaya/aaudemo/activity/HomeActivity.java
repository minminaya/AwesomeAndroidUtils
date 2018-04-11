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
import com.minminaya.aau.utils.SingletonHelper;
import com.minminaya.aaudemo.R;
import com.minminaya.aaudemo.model.Cat;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        SingletonHelper.getSingleton(Cat.class, "cat1").setName("小花1");
        Log.d("HomeActivity", "Name:" + SingletonHelper.getSingleton(Cat.class, "cat1").getName());
        SingletonHelper.removeSingleton("cat1");

        SingletonHelper.getSingleton(Cat.class, "cat2").setName("小花2");
        Log.d("HomeActivity", "Name:" + SingletonHelper.getSingleton(Cat.class, "cat2").getName());
        SingletonHelper.removeSingleton("cat2");

//        BarsHelper.addGradientOrNormalStatusBarViewAtDecorView(this, false, R.drawable.gradient_1, -1, 1.0f);
//        BarsHelper.setStatusTransparentAndColor(this,  getResources().getColor(R.color.colorPrimaryDark), 0.0f);
    }


}
