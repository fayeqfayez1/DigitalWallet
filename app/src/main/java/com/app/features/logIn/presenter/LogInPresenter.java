package com.app.features.logIn.presenter;

import android.app.Activity;
import android.widget.EditText;

import com.app.R;
import com.app.features.logIn.view.LogInView;
import com.app.network.StringRequestListener;
import com.app.network.firebase.feature.User;
import com.app.network.firebase.model.UserData;
import com.app.utils.AppShareMethods;
import com.app.utils.AppSharedData;
import com.app.utils.NavigationUtils;
import com.app.utils.ToolUtils;
import com.app.utils.dialogs.DialogUtils;

import static com.app.utils.ConstantApp.ACTION_NOTHING;

public class LogInPresenter {

    private LogInView mView;
    private Activity mActivity;

    public LogInPresenter(LogInView mView, Activity mActivity) {
        this.mView = mView;
        this.mActivity = mActivity;
    }

    public void validateSignIn(EditText edEmail, EditText edPassword) {
        edEmail.setError(null);
        edPassword.setError(null);
        if (AppShareMethods.isEmptyEditText(edEmail)) {
            AppShareMethods.errorInput(edEmail, mActivity.getString(R.string.isRequiredF));
            return;
        }

        if (!AppShareMethods.isValidEmailAddress(edEmail)) {
            AppShareMethods.errorInput(edEmail, mActivity.getString(R.string.invalidEmail));
            return;
        }
        if (AppShareMethods.isEmptyEditText(edPassword)) {
            AppShareMethods.errorInput(edPassword, mActivity.getString(R.string.isRequiredF));
            return;
        }

        if (!AppShareMethods.isValidPassword(edPassword)) {
            AppShareMethods.errorInput(edPassword, mActivity.getString(R.string.password_invalid));
            return;
        }
        ToolUtils.hideKeyboard(mActivity);
        normalLogin(edEmail.getText().toString().trim(), edPassword.getText().toString().trim());
    }

    private void normalLogin(String email, String password) {
        ToolUtils.hideKeyboard(mActivity);
        if (ToolUtils.isNetworkConnected()) {
            if (mView != null) {
                mView.showProgress();
            }
            User.loginWithEmailAndPassword(email, password, new StringRequestListener<String>() {
                @Override
                public void onSuccess(String msg, String data) {
                    if (mView != null) {
                        mView.hideProgress();
                        AppSharedData.setUserId(data);
                        AppSharedData.setUserLogin(true);
                        AppSharedData.setLoginBySocial(false);
                        NavigationUtils.goToMain(mActivity);
                    }
                }

                @Override
                public void onFail(String message) {
                    if (mView != null) {
                        mView.hideProgress();
                        DialogUtils.showAlertDialog(mActivity, mActivity.getString(R.string.alert), message, mActivity.getString(R.string.ok), "", ACTION_NOTHING);
                    }
                }
            });
        } else {
            if (mView != null) {
                mView.hideProgress();
                mView.showErrorMessage(mActivity.getString(R.string.noInternetConnection));
            }
        }
    }

    public void navToSinUpPage() {
        NavigationUtils.goToSignUp(mActivity);
    }
}
