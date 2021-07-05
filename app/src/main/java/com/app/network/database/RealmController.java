package com.app.network.database;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.app.network.firebase.model.CreditCard;
import com.app.network.firebase.model.UserData;

import java.util.ArrayList;

import io.realm.Realm;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void addOrUpdateUsers(ArrayList<UserData> userData) {
        realm.beginTransaction();

        realm.commitTransaction();
    }

    public void insertOrUpdateUserData(UserData userData) {
        realm.beginTransaction();

        realm.commitTransaction();
    }


}
