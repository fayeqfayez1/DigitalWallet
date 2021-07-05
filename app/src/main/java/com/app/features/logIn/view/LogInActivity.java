package com.app.features.logIn.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.R;
import com.app.databinding.ActivityLogInBinding;
import com.app.features.base.BaseActivity;
import com.app.features.logIn.presenter.LogInPresenter;
import com.app.utils.AppShareMethods;
import com.app.utils.dialogs.DialogUtils;

public class LogInActivity extends BaseActivity implements LogInView {

    private ActivityLogInBinding binding;
    private LogInPresenter mPresenter;

    public static Intent newInstance(Context mActivity) {
        return new Intent(mActivity, LogInActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initPresenter();
        initListener();
    }

    private void initPresenter() {
        mPresenter = new LogInPresenter(this, this);
    }

    private void initListener() {
        binding.llSignUp.setOnClickListener(v -> {
            mPresenter.navToSinUpPage();
        });
        binding.btnSignIn.setOnClickListener(v -> {
            mPresenter.validateSignIn(binding.edEmail, binding.edPassword);
        });
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