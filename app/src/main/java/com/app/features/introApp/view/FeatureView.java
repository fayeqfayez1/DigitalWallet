package com.app.features.introApp.view;

import com.app.features.base.BaseView;
import com.app.network.firebase.model.SliderData;

import java.util.ArrayList;

public interface FeatureView extends BaseView {
    void hidePagerAndShowEmptyView();

    void setDataToView(ArrayList<SliderData> data);
}
