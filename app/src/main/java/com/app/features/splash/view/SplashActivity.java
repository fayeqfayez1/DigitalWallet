package com.app.features.splash.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.app.databinding.ActivitySplashBinding;
import com.app.features.base.BaseActivity;
import com.app.features.splash.presenter.SplashPresenter;
import com.app.utils.AppSharedData;
import com.app.utils.ConstantApp;
import com.app.utils.ToolUtils;

import static com.app.utils.ConstantApp.SPLASH_TIME_OUT;

public class SplashActivity extends BaseActivity implements SplashView {

    private SplashPresenter presenter;
    private ActivitySplashBinding binding;
    private Handler handler;

    private boolean canGoToNextScreen = true;

    public static Intent newInstance(Context mActivity) {
        return new Intent(mActivity, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ToolUtils.getHash(this);
        AppSharedData.saveDeviceId(ConstantApp.HEADER_UUID_PHONE_VALUE);
        initPresenter();
        initPresenter();
    }

    private void initPresenter() {
        presenter = new SplashPresenter(this, this);
        initTimer();
    }

    private void initTimer() {
        this.handler = new Handler();
        initTimer(handler);
    }

    public void initTimer(Handler handler) {
        this.handler = handler;
        this.handler.postDelayed(this::navToNextPage, SPLASH_TIME_OUT);

    }

    public void navToNextPage() {
        if (canGoToNextScreen) {
            if (!AppSharedData.isOpenBeforeThat())
                presenter.navToIntroScreen();
            else if (AppSharedData.isUserLogin() && AppSharedData.getUserId() != null)
                presenter.navToMainPage();
            else presenter.navToLogInPage();
        }

    }

}