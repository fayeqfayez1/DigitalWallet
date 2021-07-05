package com.app.features.introApp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.viewpager.widget.ViewPager;

import com.app.R;
import com.app.databinding.ActivityFeatureBinding;
import com.app.features.base.BaseActivity;
import com.app.features.introApp.adapter.IntroAdapter;
import com.app.features.introApp.presenter.FeaturePresenter;
import com.app.network.firebase.model.SliderData;
import com.app.utils.AppShareMethods;
import com.app.utils.AppSharedData;
import com.app.utils.dialogs.DialogUtils;

import java.util.ArrayList;

public class FeatureActivity extends BaseActivity implements FeatureView, ViewPager.OnPageChangeListener {

    private ActivityFeatureBinding binding;
    private FeaturePresenter mPresenter;
    private IntroAdapter adapter;
    private int currentPage = 0;

    public static Intent newInstance(Context mActivity) {
        return new Intent(mActivity, FeatureActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = ActivityFeatureBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initPresenter();
        initView();
    }

    private void initView() {
        binding.llStart.setOnClickListener(v -> {
            mPresenter.navToLogInPage();
            AppSharedData.setOpenBeforeThat(true);
        });
    }

    private void initPresenter() {
        mPresenter = new FeaturePresenter(this, this);
    }

    @Override
    public void hidePagerAndShowEmptyView() {

    }

    @Override
    public void setDataToView(ArrayList<SliderData> featureBeans) {
        adapter = new IntroAdapter(getSupportFragmentManager(), featureBeans);
        binding.viewPagerIntro.setAdapter(adapter);
        binding.viewPagerIntro.setOnPageChangeListener(this);
        if (featureBeans != null) {
            int size = featureBeans.size();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                new Handler().postDelayed(() -> setPage(size), 500);
            else {
                setPage(size);
            }
        }
    }

    @Override
    public void showErrorMessage(String msg) {
        AppShareMethods.showSnackBar(this, binding.getRoot(), msg);
    }

    @Override
    public void showErrorDialog(String message, String okMsg, String cancelMsg, String action) {
        DialogUtils.showAlertDialog(this, getString(R.string.alert), message, okMsg, cancelMsg, action);
    }

    private void setPage(int size) {
        binding.viewPagerIntro.setCurrentItem(0, true);
        currentPage = 0;
        binding.indicator.setCount(size);
        binding.indicator.setSelected(currentPage);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        binding.indicator.setSelected(currentPage);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }


}