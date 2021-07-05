package com.app.features.main.presenter;

import android.app.Activity;
import android.util.Log;

import com.app.R;
import com.app.features.main.view.MainView;
import com.app.network.RequestListener;
import com.app.network.firebase.feature.User;
import com.app.network.firebase.model.CreditCard;
import com.app.utils.AppSharedData;
import com.app.utils.NavigationUtils;
import com.app.utils.ToolUtils;

import java.util.ArrayList;

public class MainPresenter {
    private Activity mActivity;
    private MainView mView;

    public MainPresenter(Activity mActivity, MainView mView) {
        this.mActivity = mActivity;
        this.mView = mView;
        getCreditCardData();
    }

    public void navToAddCreditCard() {
        NavigationUtils.goToAddCard(mActivity);
    }

    private void getCreditCardData() {
        if (ToolUtils.isNetworkConnected()) {
            if (mView != null)
                mView.showProgress();
            User.getAllCreditCardData(new RequestListener<ArrayList<CreditCard>>() {
                @Override
                public void onSuccess(ArrayList<CreditCard> data) {
                    if (mView != null) {
                        if (data.size() > 0) {
                            Log.e("getCreditCardData", data.get(0).toString() + "    ");
                            Log.e("CVV", data.get(0).getCvv() + "    ");
                            Log.e("size", data.size() + "     ");
                        }
                        mView.hideProgress();
                        mView.setDataToView(data);
                    }
                }

                @Override
                public void onFail(String message) {
                    if (mView != null) {
                        mView.hideProgress();
                        mView.showErrorMessage(message);
                        mView.hideDataAndShowEmptyView();
                    }
                }
            });
        } else {
            if (mView != null) {
                mView.showErrorMessage(mActivity.getString(R.string.noInternetConnection));
            }
        }
    }
}
