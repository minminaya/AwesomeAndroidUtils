package com.minminaya.aau.mvp;

/**
 * <p> presenter与Activity/Fragment接入的几个方法</p>
 * <p>
 * <p>Created by LGM on 2018/4/22 16:57</p>
 * <p>Email:minminaya@gmail.com</p>
 */
public interface IPresenter<T> {

    void attachUIModule(T uiModule);

    void detachUIModule();

    void onDestroyed();

    boolean isAttachUIModule(T uiModule);

    /**
     * 当传入Activity的实例时，在presenter中用此方法获取到Activity/Fragment的实例，以控制Activity/Fragment中的方法
     **/
    T getMvpUIModule();

}
