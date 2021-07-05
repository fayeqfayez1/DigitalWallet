package com.app.features.addCreditCard.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.R;
import com.app.databinding.ActivityAddCreditCardBinding;
import com.app.features.addCreditCard.presenter.AddCreditCardPresenter;
import com.app.features.base.BaseActivity;

import static com.app.utils.ConstantApp.MASTER_CARD;
import static com.app.utils.ConstantApp.VISA_CARD;

public class AddCreditCardActivity extends BaseActivity implements AddCreditCardView {

    private ActivityAddCreditCardBinding binding;
    private Integer mCardType = MASTER_CARD;
    private AddCreditCardPresenter mPresenter;

    public static Intent newInstance(Context mActivity) {
        return new Intent(mActivity, AddCreditCardActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCreditCardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
        initPresenter();
        initListener();
    }

    private void initView() {
        setCardDefault(MASTER_CARD);
    }

    private void initPresenter() {
        mPresenter = new AddCreditCardPresenter(this, this);
    }

    private void initListener() {
        binding.tvVisa.setOnClickListener(v -> setCardDefault(VISA_CARD));
        binding.tvMaster.setOnClickListener(v -> setCardDefault(MASTER_CARD));
        binding.btnAddCard.setOnClickListener(v -> {
            mPresenter.validateInput(mCardType, binding.edCardNumber, binding.edCardHolderName, binding.edCcv, binding.edExpiredDate);
        });
        binding.ibBack.setOnClickListener(v -> onBackPressed());
    }

    private void setCardDefault(int cardType) {
        switch (cardType) {
            case VISA_CARD:
                binding.tvVisa.setBackgroundResource(R.drawable.custom_card_type_selected);
                binding.tvVisa.setTextColor(getResources().getColor(R.color.white));
                binding.tvMaster.setBackgroundResource(R.drawable.custom_card_type_white);
                binding.tvMaster.setTextColor(getResources().getColor(R.color.black));
                mCardType = VISA_CARD;
                break;
            case MASTER_CARD:
                binding.tvMaster.setBackgroundResource(R.drawable.custom_card_type_selected);
                binding.tvMaster.setTextColor(getResources().getColor(R.color.white));
                binding.tvVisa.setBackgroundResource(R.drawable.custom_card_type_white);
                binding.tvVisa.setTextColor(getResources().getColor(R.color.black));
                mCardType = MASTER_CARD;

                break;
        }
    }

}
