package com.app.utils;

import android.annotation.SuppressLint;
import android.provider.Settings;

public interface ConstantApp {
    @SuppressLint("HardwareIds")
    String HEADER_UUID_PHONE_VALUE = Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);// Device UUID
    int HTTP_EXCEPTION_CODE = 300;
    int TIME_OUT_CODE = 302;
    int IO_EXCEPTION_CODE = 303;
    int ANOTHER_EXCEPTION_CODE = 304;
    int SPLASH_TIME_OUT = 3000;
    String FROM_WHERE = "from_where";
    String ACTION_CLOSE = "close";
    String ACTION_NOTHING = "nothing";
    String ACTION_CHECKOUT = "checkout";
    String ANDROID = "android";
    int VISA_CARD= 1;
    int MASTER_CARD= 2;
    String DEFAULT_TYPE = "Default";

}
