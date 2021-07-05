package com.app.features.splash.presenter;

import android.app.Activity;
import android.os.Handler;

import com.app.features.introApp.view.FeatureActivity;
import com.app.features.splash.view.SplashView;
import com.app.utils.NavigationUtils;

public class SplashPresenter {

    private Activity mActivity;
    private SplashView mView;
    private Handler handler;

    public SplashPresenter(Activity mActivity, SplashView mView) {
        this.mActivity = mActivity;
        this.mView = mView;
    }

    public void navToIntroScreen() {
        mActivity.startActivity(FeatureActivity.newInstance(mActivity));
        mActivity.finish();
    }
    public void navToMainPage() {
        NavigationUtils.goToMain(mActivity);
    }
    public void navToLogInPage() {
        NavigationUtils.goToLogin(mActivity);
    }
}
