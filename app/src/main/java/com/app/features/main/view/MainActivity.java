package com.app.features.main.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.R;
import com.app.databinding.ActivityMainBinding;
import com.app.features.base.BaseActivity;
import com.app.features.main.adapter.MainAdapter;
import com.app.features.main.presenter.MainPresenter;
import com.app.network.firebase.model.CreditCard;
import com.app.utils.AppShareMethods;
import com.app.utils.dialogs.DialogUtils;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends BaseActivity implements MainView, MainAdapter.OnClickListener {

    private ActivityMainBinding binding;
    private MainPresenter mPresenter;
    private MainAdapter mAdapter;

    public static Intent newInstance(Context mActivity) {
        return new Intent(mActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initPresenter();
        initListener();
        initAdapters();
    }

    private void initPresenter() {
        mPresenter = new MainPresenter(this, this);
    }

    private void initListener() {
        binding.btnNext.setOnClickListener(v -> mPresenter.navToAddCreditCard());
        binding.ibAddCredit.setOnClickListener(v -> mPresenter.navToAddCreditCard());
        binding.ibNavAddBu.setOnClickListener(v -> mPresenter.navToAddCreditCard());
    }

    private void initAdapters() {
        mAdapter = new MainAdapter(this, this::onItemClick);
        binding.rvList.setAdapter(mAdapter);
    }

    @Override
    public void hideDataAndShowEmptyView() {
    }

    @Override
    public void setDataToView(ArrayList<CreditCard> data) {
        if (data != null && data.size() > 0) {
            binding.noCredite.setVisibility(GONE);
            binding.haveCredite.setVisibility(VISIBLE);
        } else {
            binding.noCredite.setVisibility(VISIBLE);
            binding.haveCredite.setVisibility(GONE);
        }
        mAdapter.removeAll();
        mAdapter.addList(data);
    }

    @Override
    public void showErrorMessage(String msg) {
        AppShareMethods.showSnackBar(this, binding.getRoot(), msg);
    }

    @Override
    public void showErrorDialog(String message, String okMsg, String cancelMsg, String action) {
        DialogUtils.showAlertDialog(this, getString(R.string.alert), message, okMsg, cancelMsg, action);
    }

    @Override
    public void onItemClick(CreditCard item, int position) {

    }
}