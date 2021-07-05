package com.app.utils;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    private static Boolean isMainActivityOpen = false;

    public static boolean isMainActivityOpen() {
        return isMainActivityOpen;
    }

    public static void setMainActivityOpen(Boolean isOpen) {
        isMainActivityOpen = isOpen;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
