package com.app.features.introApp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.features.introApp.fragmen.IntroFragment;
import com.app.network.firebase.model.SliderData;

import java.util.ArrayList;

public class IntroAdapter extends FragmentPagerAdapter {
    private ArrayList<SliderData> listOfFeature;

    public IntroAdapter(FragmentManager fm, ArrayList<SliderData> listOfFeature) {
        super(fm);
        this.listOfFeature = listOfFeature;
    }

    @Override
    public Fragment getItem(int position) {

        return IntroFragment.newInstance(listOfFeature.get(position), position, listOfFeature.size());
    }

    @Override
    public int getCount() {
        return listOfFeature.size();
    }

}