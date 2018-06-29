package com.minminaya.aaudemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.minminaya.aau.utils.ThreadPoolManagerUtils;
import com.minminaya.aaudemo.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ThreadPoolManagerUtils.getsInstance()
                .setNewBuilder(new ThreadPoolManagerUtils.Builder())
                .executeRunnableBySpeciallyPool("THREADPOOL-1", new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 5; i++) {
                            Log.d(TAG, "当前的i为：" + i);
                        }
                    }
                });
//        BarsHelper.addGradientOrNormalStatusBarViewAtDecorView(this, false, R.drawable.gradient_1, -1, 1.0f);
//        BarsHelper.setStatusTransparentAndColor(this,  getResources().getColor(R.color.colorPrimaryDark), 0.0f);
    }


}
