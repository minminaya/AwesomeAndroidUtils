package com.minminaya.aau.mvp;

/**
 * <p> MVP模式中Activity与Fragment要实现的接口类</p>
 * <p>
 * <p>Created by LGM on 2018/4/22 16:51</p>
 * <p>Email:minminaya@gmail.com</p>
 */
public interface MvpView<T> {
    void setPresenter(T presenter);
}
