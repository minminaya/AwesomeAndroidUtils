package com.minminaya.aaudemo;

import android.app.Application;

import com.minminaya.aau.AAUHelper;

/**
 * Created by Niwa on 2018-03-19.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AAUHelper.initial(this);
    }
}
