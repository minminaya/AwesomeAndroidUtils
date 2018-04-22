package com.minminaya.aau.mvp;

/**
 * <p> MVP模式Presenter的base类</p>
 * <p>
 * <p>Created by LGM on 2018/4/22 16:56</p>
 * <p>Email:minminaya@gmail.com</p>
 */
public class BasePresenter<T extends MvpView> implements IPresenter<T> {
    /**
     * 指传入的Activity或者Fragment实例
     */
    private T mMvpUIModule;

    /**
     * @param uiModule 要绑定的Activity/Fragment，统称为UIModule(UI组件)
     *                 将Presenter和Activity/Fragment绑定
     */
    @Override
    public void attachUIModule(T uiModule) {
        mMvpUIModule = uiModule;
    }

    /**
     * @param uiModule 要绑定的Activity/Fragment，统称为UIModule(UI组件)
     *                 将Presenter和Activity/Fragment绑定
     *                 取消Presenter和Activity/Fragment的绑定
     */
    @Override
    public void detachUIModule() {
        if(mMvpUIModule != null){
            mMvpUIModule = null;
        }
    }

    @Override
    public void onDestroyed() {

    }

    /**
     * Presenter是否连接Activity/Fragment
     *
     * @return true表示已连接
     */
    @Override
    public boolean isAttachUIModule(T uiModule) {
        return mMvpUIModule != null;
    }


    /**
     * 得到当前的绑定的Activity/Fragment的实例
     *
     * @param T 当前的绑定的Activity/Fragment的实例
     */
    @Override
    public T getMvpUIModule() {
        return mMvpUIModule;
    }
}
