package com.minminaya.aaudemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.minminaya.aau.utils.BarsHelper;
import com.minminaya.aaudemo.R;

public class NavigationDrawerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);


        Button button1 = findViewById(R.id.btn_show);
        Button button2 = findViewById(R.id.btn_hide);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarsHelper.setNavigationImmersive(NavigationDrawerActivity.this);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarsHelper.setNavigationBarColor(NavigationDrawerActivity.this, Color.TRANSPARENT);
            }
        });
        BarsHelper.setDrawerLayoutTransparent(this, getResources().getColor(R.color.colorPrimaryDark), R.id.layout_content);
//        Log.e("ddd", "jieguo：" + BarsHelper.isNavigationBarVisible(this));
    }

}
