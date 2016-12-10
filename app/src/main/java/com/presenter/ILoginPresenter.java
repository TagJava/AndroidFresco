package com.presenter;

/**
 * Created by elephant on 2016/11/30.
 */

public interface ILoginPresenter {
    void clear();
    void doLogin(String name,String passwd);
    void setProgressBarVisiblity(int visiblity);
}
