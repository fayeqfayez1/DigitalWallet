package com.app.utils;

import android.app.Activity;
import android.content.Intent;

import com.app.features.addCreditCard.view.AddCreditCardActivity;
import com.app.features.logIn.view.LogInActivity;
import com.app.features.main.view.MainActivity;
import com.app.features.sinUp.view.SinUpActivity;

public class NavigationUtils {
    public static void goToMain(Activity mActivity) {
        Intent intent = MainActivity.newInstance(mActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    public static void goToSignUp(Activity mActivity) {
        mActivity.startActivity(SinUpActivity.newInstance(mActivity));
    }
    public static void goToLogin(Activity mActivity) {
        mActivity.startActivity(LogInActivity.newInstance(mActivity));
    }
    public static void goToAddCard(Activity mActivity) {
        mActivity.startActivity(AddCreditCardActivity.newInstance(mActivity));
    }
}
