package com.app.features.introApp.presenter;

import android.app.Activity;

import com.app.R;
import com.app.features.introApp.view.FeatureView;
import com.app.network.firebase.model.SliderData;
import com.app.utils.NavigationUtils;
import com.app.utils.ToolUtils;

import java.util.ArrayList;

public class FeaturePresenter {

    private FeatureView mView;
    private Activity mActivity;

    public FeaturePresenter(FeatureView mView, Activity mActivity) {
        this.mView = mView;
        this.mActivity = mActivity;
        getListOfFeatures();
    }


    private void getListOfFeatures() {
        if (ToolUtils.isNetworkConnected()) {
            if (mView != null)
                mView.showProgress();
            getSliderImg();
        } else {
            if (mView != null)
                mView.showErrorMessage(mActivity.getString(R.string.noInternetConnection));
        }
    }
    public void navToLogInPage() {
        NavigationUtils.goToLogin(mActivity);
    }
    private void getSliderImg() {
        ArrayList<SliderData> data = new ArrayList<>();
        data.add(new SliderData(1, "Easily Accessible", "Access your account anytime anywhere, no hassele" +
                "for any kind of transaction. Transfer, pay or buy anything" +
                "anywhere, your account is in your fingertips.", "https://firebasestorage.googleapis.com/v0/b/digitalwallet-45922.appspot.com/o/introImg%2FintroApp1.png?alt=media&token=715b520e-8d56-4f12-af43-970cd42d8e47"));
        data.add(new SliderData(2, "Manage anytime", "Manage your account anytime anywhere, no hassele" +
                "for any kind of management. Transfer, pay or buy anything" +
                "anywhere, your account is in your fingertips.", "https://firebasestorage.googleapis.com/v0/b/digitalwallet-45922.appspot.com/o/introImg%2FintroApp2.png?alt=media&token=3e12181d-18d9-4df7-91b5-a9443dd54733"));
        data.add(new SliderData(3, "Safe transaction", "Manage your account anytime anywhere, no hassele" +
                "for any kind of management. Transfer, pay or buy anything" +
                "anywhere, your account is in your fingertips.", "https://firebasestorage.googleapis.com/v0/b/digitalwallet-45922.appspot.com/o/introImg%2FintroApp3.png?alt=media&token=0c0b694a-5855-4116-bf85-ef76f2fe291f"));
        if (mView != null) {
            mView.setDataToView(data);
            mView.hideProgress();
        }
    }

    public void onDestroy() {
        mView = null;
    }

}
