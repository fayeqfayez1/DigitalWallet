package com.app.utils;

import com.app.network.firebase.model.CreditCard;
import com.app.network.firebase.model.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AppSharedData {

    public static final String SHARED_APP_DATA = "app_data";
    public static final String SHARED_USER_DATA = "user";
    public static final String SHARED_CREDIT_CARD = "creditCard ";

    private static final String SHARED_IS_APP_OPENED_BEFORE = "is_app_opened_before";
    private static final String SHARED_IS_USER_LOGIN_BY_SOCIAL = "login_social";
    private static final String SHARED_IS_USER_LOGIN = "is_user_login";
    private static Gson gson = new Gson();
    private static final String SHARED_LAT = "lat";
    private static final String SHARED_LNG = "lng";
    private static final String DEVICE_ID = "device_id";
    private static final String PASSWORD = "password";
    private static final String SHARED_BADGE_COUNT = "BadgeCount";
    private static final String SHARED_USER_ID = "userId";

    public static void setUserData(UserData user) {
        App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE)
                .edit().putString(SHARED_USER_DATA, gson.toJson(user)).apply();
    }

    public static UserData getUserData() {
        UserData mUser = gson.fromJson(App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE)
                .getString(SHARED_USER_DATA, null), UserData.class);
        return mUser;
    }

    public static void setUserId(String userId) {
        App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE)
                .edit().putString(SHARED_USER_ID, userId).apply();
    }

    public static String getUserId() {
        return App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE).getString(SHARED_USER_ID, "");
    }

    public static boolean isUserLogin() {
        return App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE)
                .getBoolean(SHARED_IS_USER_LOGIN, false);
    }

    public static void setUserLogin(boolean login) {
        App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE).edit()
                .putBoolean(SHARED_IS_USER_LOGIN, login).apply();
    }

    public static boolean isLoginBySocial() {
        return App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE)
                .getBoolean(SHARED_IS_USER_LOGIN_BY_SOCIAL, false);
    }

    public static void setLoginBySocial(boolean login) {
        App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE).edit()
                .putBoolean(SHARED_IS_USER_LOGIN_BY_SOCIAL, login).apply();
    }


    public static void saveDeviceId(String deviceID) {
        App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE).edit()
                .putString(DEVICE_ID, deviceID).apply();
    }

    public static boolean isOpenBeforeThat() {
        return App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE)
                .getBoolean(SHARED_IS_APP_OPENED_BEFORE, false);
    }

    public static void setOpenBeforeThat(boolean open) {
        App.getInstance().getSharedPreferences(SHARED_APP_DATA, MODE_PRIVATE).edit()
                .putBoolean(SHARED_IS_APP_OPENED_BEFORE, open).apply();
    }
}
