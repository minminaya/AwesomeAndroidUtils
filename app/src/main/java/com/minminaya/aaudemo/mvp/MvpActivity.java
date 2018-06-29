package com.minminaya.aaudemo.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.minminaya.aau.mvp.MvpView;
import com.minminaya.aaudemo.activity.HomeActivity;

/**
 * <p> </p>
 * <p>
 * <p>Created by LGM on 2018/4/22 17:50</p>
 * <p>Email:minminaya@gmail.com</p>
 */
public class MvpActivity extends AppCompatActivity implements MvpView<MvpPresenter> {


    private MvpPresenter mvpPresenter = new MvpPresenter();

    @Override
    public void setPresenter(MvpPresenter presenter) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.attachUIModule(this);
        mvpPresenter.test();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter.detachUIModule();
    }
}
