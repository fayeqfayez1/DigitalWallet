package com.app.features.sinUp.presenter;

import android.app.Activity;
import android.widget.EditText;

import com.app.R;
import com.app.features.sinUp.view.SinUpView;
import com.app.network.RequestListener;
import com.app.network.StringRequestListener;
import com.app.network.firebase.feature.User;
import com.app.network.firebase.model.UserData;
import com.app.utils.AppShareMethods;
import com.app.utils.NavigationUtils;
import com.app.utils.ToolUtils;
import com.app.utils.dialogs.DialogUtils;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.app.utils.ConstantApp.ACTION_NOTHING;
import static com.app.utils.ConstantApp.DEFAULT_TYPE;

public class SinUpPresenter {
    private Activity mActivity;
    private SinUpView mView;

    public SinUpPresenter(Activity mActivity, SinUpView mView) {
        this.mActivity = mActivity;
        this.mView = mView;
    }

    public void validateInput(EditText edEmail,
                              EditText edFullName,
                              EditText edPassword,
                              EditText edConfPassword) {


        if (AppShareMethods.isEmptyEditText(edFullName)) {
            AppShareMethods.errorInput(edFullName, mActivity.getString(R.string.isRequiredF));
            return;
        }
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

        if (AppShareMethods.isEmptyEditText(edConfPassword)) {
            AppShareMethods.errorInput(edConfPassword, mActivity.getString(R.string.isRequiredF));
            return;
        }

        if (!AppShareMethods.isValidPassword(edConfPassword)) {
            AppShareMethods.errorInput(edConfPassword, mActivity.getString(R.string.password_invalid));
            return;
        }

        if (!AppShareMethods.isPasswordsMatch(edPassword, edConfPassword)) {
            AppShareMethods.errorInput(edPassword, mActivity.getString(R.string.password_missmatch));
            AppShareMethods.errorInput(edConfPassword, mActivity.getString(R.string.password_missmatch));
            return;
        }
        ToolUtils.hideKeyboard(mActivity);
        if (ToolUtils.isNetworkConnected()) {
            if (mView != null) {
                mView.showProgress();
            }
            UserData userData = new UserData();
            userData.setUsername(edFullName.getText().toString());
            userData.setEmail(edEmail.getText().toString());


            User.userSignUp(userData, edPassword.getText().toString(), new RequestListener<UserData>() {
                @Override
                public void onSuccess(UserData data) {
                    saveUserToDatabase(data);
                }

                @Override
                public void onFail(String message) {
                    if (mView != null) {
                        mView.showErrorMessage(message);
                        mView.hideProgress();
                    }
                }
            });
        } else {
            if (mView != null) {
                mView.showErrorMessage(mActivity.getString(R.string.noInternetConnection));
            }
        }
    }

    private void saveUserToDatabase(UserData userData) {
        if (ToolUtils.isNetworkConnected()) {
            userData.setLoginType(DEFAULT_TYPE);
            userData.setDeviceToken(FirebaseMessaging.getInstance().getToken().toString());
            User.saveUserDataToDatabase(userData, true, new StringRequestListener<UserData>() {
                @Override
                public void onSuccess(String msg, UserData data) {
                    if (mView != null) {
                        if (mView != null) {
                            mView.hideProgress();
                            DialogUtils.showAlertDialogWithListener(mActivity,
                                    mActivity.getString(R.string.alert),
                                    msg,
                                    mActivity.getString(R.string.ok),
                                    "", new DialogUtils.onClickListener() {
                                        @Override
                                        public void onOkClick() {
                                            NavigationUtils.goToLogin(mActivity);
                                            mActivity.finish();
                                        }

                                        @Override
                                        public void onCancelClick() {
                                        }
                                    });
                        }
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
                mView.showErrorMessage(mActivity.getString(R.string.noInternetConnection));
            }
        }
    }

    public void navToLOgIn() {
        NavigationUtils.goToLogin(mActivity);
        mActivity.finish();
    }
}
