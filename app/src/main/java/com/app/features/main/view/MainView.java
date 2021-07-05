package com.app.features.main.view;

import com.app.features.base.BaseView;
import com.app.network.firebase.model.CreditCard;

import java.util.ArrayList;

public interface MainView extends BaseView {
    void hideDataAndShowEmptyView();

    void setDataToView(ArrayList<CreditCard> data);

}
