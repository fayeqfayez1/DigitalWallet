package com.app.utils.dialogs;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.app.R;

import static android.app.Activity.RESULT_OK;
import static com.app.utils.ConstantApp.ACTION_CLOSE;

public class DialogUtils {
    public static void showAlertDialog(Activity mActivity, String title, String message, String okMsg, String cancelMsg, String action) {

        CustomFloatDialog dialog = CustomFloatDialog.newInstance(title, message, okMsg, cancelMsg);
        dialog.setListener(new CustomFloatDialog.onClickListener() {
            @Override
            public void onOkClick() {
                dialog.dismiss();
                if (action.equals(ACTION_CLOSE)) {
                    mActivity.setResult(RESULT_OK);
                    mActivity.finish();
                }
            }

            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        });
        ((AppCompatActivity) mActivity).getSupportFragmentManager().beginTransaction().add(dialog, "DialogMessage").commitAllowingStateLoss();
    }


    public static void showAlertDialogWithListener(Activity mActivity, String title, String message, String okMsg, String cancelMsg,
                                                   final onClickListener listener) {

        final CustomFloatDialog dialog = CustomFloatDialog.newInstance(title, message, okMsg, cancelMsg);
        dialog.setListener(new CustomFloatDialog.onClickListener() {
            @Override
            public void onOkClick() {
                if (listener != null) listener.onOkClick();
                dialog.dismiss();
            }

            @Override
            public void onCancelClick() {
                listener.onCancelClick();
                dialog.dismiss();
            }
        });
        ((AppCompatActivity) mActivity).getSupportFragmentManager().beginTransaction().add(dialog, "DialogMessage").commitAllowingStateLoss();
    }


    public static void showMustLoginDialog(Activity mActivity) {
        DialogUtils.showAlertDialogWithListener(mActivity, mActivity.getString(R.string.alert),
                mActivity.getString(R.string.must_login),
                mActivity.getString(R.string.login),
                mActivity.getString(R.string.cancel), new DialogUtils.onClickListener() {
                    @Override
                    public void onOkClick() {
                       //mActivity.startActivity(LoginActivity.newInstance(mActivity, FROM_OTHERS));
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
    }

    public interface onClickListener {
        void onOkClick();

        void onCancelClick();
    }

}
