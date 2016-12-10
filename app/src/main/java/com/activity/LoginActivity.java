package com.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;

import com.presenter.ILoginPresenter;
import com.presenter.LoginPresenterCompl;
import com.view.ILoginView;

/**
 * Created by elephant on 2016/11/30.
 */

public class LoginActivity extends ActionBarActivity implements ILoginView,View.OnClickListener{

    ILoginPresenter loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        loginPresenter = new LoginPresenterCompl(this);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onClick(View view) {

    }
}
