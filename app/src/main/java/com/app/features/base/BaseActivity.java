package com.app.features.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.utils.dialogs.LoadingDialog;

public class BaseActivity extends AppCompatActivity implements BaseView {

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();

    }

    private void initDialog() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setDialogCancelable();
    }

    @Override
    public void showProgress() {
        loadingDialog.showDialog();
    }

    @Override
    public void hideProgress() {
        loadingDialog.hideDialog();
    }

    @Override
    public void showErrorMessage(String massage) {
    }

    @Override
    public void showErrorDialog(String massage, String okMsg, String cancelMsg, String action) {
    }

}
