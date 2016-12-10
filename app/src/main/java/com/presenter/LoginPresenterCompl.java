package com.presenter;

import android.os.Handler;
import android.os.Looper;

import com.model.IUser;
import com.model.UserModel;
import com.view.ILoginView;

/**
 * 保留了ILoginView的引用
 * 别的Activity里也需要用到相同的业务逻辑，就可以直接复用LoginPresenterCompl类了
 */

public class LoginPresenterCompl implements ILoginPresenter {
    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public LoginPresenterCompl(ILoginView iLoginView){
        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    public void initUser(){
        user = new UserModel("mvp","mvp");
    }

    @Override
    public void clear() {

    }

    @Override
    public void doLogin(String name, String passwd) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },3000);
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {

    }
}
