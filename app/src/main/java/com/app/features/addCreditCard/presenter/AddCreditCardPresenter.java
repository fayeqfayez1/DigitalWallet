package com.app.features.addCreditCard.presenter;

import android.app.Activity;
import android.widget.EditText;

import com.app.R;
import com.app.features.addCreditCard.view.AddCreditCardView;
import com.app.network.StringRequestListener;
import com.app.network.firebase.feature.User;
import com.app.network.firebase.model.CreditCard;
import com.app.utils.AppShareMethods;
import com.app.utils.AppSharedData;
import com.app.utils.NavigationUtils;
import com.app.utils.ToolUtils;
import com.app.utils.dialogs.DialogUtils;

import static com.app.utils.ConstantApp.ACTION_NOTHING;

public class AddCreditCardPresenter {

    private AddCreditCardView mView;
    private Activity mActivity;

    public AddCreditCardPresenter(AddCreditCardView mView, Activity mActivity) {
        this.mView = mView;
        this.mActivity = mActivity;
    }

    public void validateInput(int cardType,
                              EditText edCardNumber,
                              EditText edCardHolderName,
                              EditText edCVV,
                              EditText edExpiredDate) {

        if (cardType == 0) {
            if (mView != null)
                mView.showErrorMessage(mActivity.getString(R.string.card_type_required));
            return;
        }
        if (AppShareMethods.isEmptyEditText(edCardNumber)) {
            AppShareMethods.errorInput(edCardNumber, mActivity.getString(R.string.isRequiredF));
            return;
        }
        if (!AppShareMethods.isCreditCardNumberValid(edCardNumber)) {
            AppShareMethods.errorInput(edCardNumber, mActivity.getString(R.string.wrong_credit_card_number));
            return;
        }
        if (AppShareMethods.isEmptyEditText(edCardHolderName)) {
            AppShareMethods.errorInput(edCardHolderName, mActivity.getString(R.string.isRequiredF));
            return;
        }
        if (AppShareMethods.isEmptyEditText(edCVV)) {
            AppShareMethods.errorInput(edCVV, mActivity.getString(R.string.isRequiredF));
            return;
        }
        if (!AppShareMethods.isCreditCVVNumberValid(edCVV)) {
            AppShareMethods.errorInput(edCVV, mActivity.getString(R.string.wrong_cvv_number));
            return;
        }
        if (AppShareMethods.isEmptyEditText(edExpiredDate)) {
            AppShareMethods.errorInput(edExpiredDate, mActivity.getString(R.string.isRequiredF));
            return;
        }
        ToolUtils.hideKeyboard(mActivity);
        if (ToolUtils.isNetworkConnected()) {
            if (mView != null)
                mView.showProgress();
            CreditCard creditCard = new CreditCard();
            creditCard.setCardNumber(AppShareMethods.getText(edCardNumber));
            creditCard.setCardHolderName(AppShareMethods.getText(edCardHolderName));
            creditCard.setCvv(AppShareMethods.getText(edCVV));
            creditCard.setExpiredDate(AppShareMethods.getText(edExpiredDate));
            creditCard.setCardType(cardType);
            saveUserToDatabase(creditCard);
        } else {
            if (mView != null) {
                mView.showErrorMessage(mActivity.getString(R.string.noInternetConnection));
            }
        }
    }

    private void saveUserToDatabase(CreditCard creditCard) {
        if (ToolUtils.isNetworkConnected()) {
            User.saveUserCreditCardDataToDatabase(AppSharedData.getUserId(), creditCard, new StringRequestListener<CreditCard>() {
                @Override
                public void onSuccess(String message, CreditCard data) {
                    if (mView != null) {
                        mView.hideProgress();
                        DialogUtils.showAlertDialogWithListener(mActivity,
                                mActivity.getString(R.string.alert),
                                message,
                                mActivity.getString(R.string.ok),
                                "", new DialogUtils.onClickListener() {
                                    @Override
                                    public void onOkClick() {
                                        NavigationUtils.goToMain(mActivity);
                                        mActivity.finish();
                                    }

                                    @Override
                                    public void onCancelClick() {
                                    }
                                });
                    }
                }

                @Override
                public void onFail(String message) {
                    if (mView != null) {
                        mView.hideProgress();
                        DialogUtils.showAlertDialog(mActivity, mActivity.getString(R.string.alert), message, mActivity.getString(R.string.ok), "", ACTION_NOTHING);
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
