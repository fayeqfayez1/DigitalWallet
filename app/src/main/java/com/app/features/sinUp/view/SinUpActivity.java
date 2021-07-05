package com.app.features.sinUp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.R;
import com.app.databinding.ActivitySinUpBinding;
import com.app.features.base.BaseActivity;
import com.app.features.sinUp.presenter.SinUpPresenter;
import com.app.utils.AppShareMethods;
import com.app.utils.dialogs.DialogUtils;

public class SinUpActivity extends BaseActivity implements SinUpView {

    private ActivitySinUpBinding binding;

    public static Intent newInstance(Context mActivity) {
        return new Intent(mActivity, SinUpActivity.class);
    }

    private SinUpPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySinUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initPresenter();
        initListener();
    }

    private void initListener() {
        binding.btnSignUp.setOnClickListener(v -> {
            mPresenter.validateInput(binding.edEmail, binding.edUserName, binding.edPassword, binding.edConfirmPassword);
        });
        binding.llHaveAccount.setOnClickListener(v -> mPresenter.navToLOgIn());
    }

    private void initPresenter() {
        mPresenter = new SinUpPresenter(this, this);
    }

    @Override
    public void showErrorMessage(String msg) {
        AppShareMethods.showSnackBar(this, binding.getRoot(), msg);
    }

    @Override
    public void showErrorDialog(String message, String okMsg, String cancelMsg, String action) {
        super.showErrorDialog(message, okMsg, cancelMsg, action);
        DialogUtils.showAlertDialog(this, getString(R.string.alert), message, okMsg, cancelMsg, action);
    }
}