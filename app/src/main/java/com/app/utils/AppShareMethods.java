package com.app.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ShareCompat;

import com.app.R;
import com.google.android.material.snackbar.Snackbar;

import static android.view.View.TEXT_ALIGNMENT_GRAVITY;

public class AppShareMethods {

    public static String WHATS_APP_MARKET_URL = "market://details?id=com.whatsapp";
    public static String FACEBOOK_MARKET_URL = "market://details?id=com.facebook.katana";
    public static String SKYPE_MARKET_URL = "market://details?id=com.skype.raider";
    public static String GOOGLE_APP_MARKET_URL = "market://details?id=com.google.android.apps.plus";
    public static String MESSENGER_APP_MARKET_URL = "market://details?id=com.facebook.orca";
    public static String TWITTER_APP_MARKET_URL = "market://details?id=com.twitter.android";

    public static boolean isEmptyEditText(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    public static boolean isEmptyText(TextView text) {
        return text.getText().toString().trim().isEmpty();
    }


    public static void errorInput(EditText editText, String msg) {
        editText.requestFocus();
        editText.setError(msg);
    }

    public static String getTextForMobile(EditText editText) {
        String text = ToolUtils.convertToEngNo(editText.getText().toString()).toString();
        if (editText.getText().toString().trim().startsWith("05")) {
            text = text.replaceFirst("0", "");
        }
        return text;
    }

    public static String getText(EditText editText) {
        return ToolUtils.convertToEngNo(editText.getText().toString().trim()) + "";
    }

    public static String getText(TextView editText) {
        return editText.getText().toString().trim();
    }

    public static boolean isValidEmailAddress(EditText editText) {
        String email = editText.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public static boolean isValidPassword(EditText editText) {
        return editText.getText().toString().trim().length() >= 6;
    }
    public static boolean isCreditCardNumberValid(EditText editText) {
        return editText.getText().toString().trim().length() >= 16;
    }
    public static boolean isCreditCVVNumberValid(EditText editText) {
        return editText.getText().toString().trim().length() >= 4;
    }
    public static boolean isInValidPassword(EditText editText) {
        return editText.getText().toString().trim().length() < 6;
    }

    public static boolean isValidMobile(EditText editText) {
        if ((editText.getText().toString().trim().length() == 10 && editText.getText().toString().trim().startsWith("05")))
            return true;
        else if ((editText.getText().toString().trim().length() == 9) && editText.getText().toString().trim().startsWith("5"))
            return true;
        else return false;
    }

    public static boolean isInValidMobile(EditText editText) {
        return editText.getText().toString().trim().length() < 8 || editText.getText().toString().trim().length() > 9/* || !editText.getText().toString().trim().startsWith("5")*/;
    }

    public static boolean isInValidMobileStartWithZero(EditText editText) {
        return editText.getText().toString().trim().startsWith("0");
    }

    public static boolean isPasswordsMatch(EditText editText1, EditText editText2) {
        return editText1.getText().toString().trim().equals(editText2.getText().toString().trim());
    }

    public static void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void showSnackBar(Activity activity, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        Typeface font = Typeface.createFromAsset(activity.getAssets(), "muli_regular.ttf");
        textView.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        textView.setTypeface(font);
        sbView.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
        textView.setTextDirection(View.TEXT_DIRECTION_LTR);
        textView.setGravity(Gravity.LEFT);
        textView.setTextAlignment(TEXT_ALIGNMENT_GRAVITY);
        snackbar.show();
    }

    public static void setTransStatusBar(Activity mActivity) {
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public static void newWhatsAppIntent(Activity mActivity, PackageManager pm, String whatsapp) {
        if (!isAppClientInstalledAndEnabled(mActivity, "com.whatsapp")) {
            goToMarket(mActivity, "com.whatsapp");
            return;
        }
        if (!TextUtils.isEmpty(whatsapp)) {
            Uri uri = Uri.parse("smsto:" + whatsapp);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            mActivity.startActivity(Intent.createChooser(i, ""));
        }
    }

    /**
     * Determine whether the App for Android Client is installed on this device.
     */
    public static boolean isAppClientInstalledAndEnabled(Activity mActivity, String packageName) {
        PackageManager myPackageMgr = mActivity.getPackageManager();
        try {
            myPackageMgr.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return myPackageMgr.getApplicationInfo(packageName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Install the App Client through the market: URI scheme.
     */
    public static void goToMarket(Activity mActivity, String urlOnMarket) {
        Uri marketUri = Uri.parse(urlOnMarket);
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(myIntent);

        return;
    }

    public static void newFacebookIntent(Activity mActivity, PackageManager pm, String fbUrl) {
        Uri uri;
        if (!TextUtils.isEmpty(fbUrl)) {
            try {
                ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
                if (applicationInfo.enabled) {

                    uri = Uri.parse("fb://facewebmodal/f?href=" + fbUrl);
                    mActivity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            } catch (PackageManager.NameNotFoundException ignored) {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fbUrl)));
            }
        }
    }

    public static void newSkypeIntent(Activity mActivity, PackageManager pm, String appUrl) {
        if (!TextUtils.isEmpty(appUrl)) {
            Intent intent = mActivity.getPackageManager().getLaunchIntentForPackage("com.skype.raider");
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, appUrl);
            intent.setType("text/plain");
            try {
                mActivity.startActivity(intent);
            } catch (ActivityNotFoundException ignored) {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)));
            }
        }
    }

    public static void newGoogleIntent(Activity mActivity, PackageManager pm, String appUrl) {
        if (!TextUtils.isEmpty(appUrl)) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(mActivity)
                    .setText(appUrl).setType("text/plain")
                    .setStream(Uri.parse("")).getIntent()
                    .setPackage("com.google.android.apps.plus");
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                mActivity.startActivity(shareIntent);
            } catch (ActivityNotFoundException ignored) {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)));
            }
        }
    }

    public static void newFacebookMessengerIntent(Activity mActivity, PackageManager pm, String appUrl) {
        if (!TextUtils.isEmpty(appUrl)) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, appUrl);
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.facebook.orca");
            try {
                mActivity.startActivity(sendIntent);
            } catch (ActivityNotFoundException ignored) {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)));
            }
        }
    }

    public static void newInstagramIntent(Activity mActivity, PackageManager pm, String instagramUrl) {
        Uri uri = Uri.parse(instagramUrl);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            mActivity.startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(instagramUrl)));
        }
    }

    public static void newTwitterIntent(Activity mActivity, PackageManager pm, String twitterUrl) {

        if (!TextUtils.isEmpty(twitterUrl)) {
            Uri uri = Uri.parse(twitterUrl);
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.twitter.android");

            try {
                mActivity.startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl)));
            }

        }
    }

    // To animate view slide out from left to right
    public static void slideToRight(View view, int visibility, int duration) {
        TranslateAnimation animate = new TranslateAnimation(0, view.getWidth(), 0, 0);
        animate.setDuration(duration);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(visibility);
    }

    // To animate view slide out from right to left
    public static void slideToLeft(View view, int visibility, int duration) {
        TranslateAnimation animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        animate.setDuration(duration);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(visibility);
    }

    // To animate view slide out from top to bottom
    public static void slideToBottom(View view, int visibility, int duration) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(duration);
//        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(visibility);
    }

    // To animate view slide out from bottom to top
    public static void slideToTop(View view, int visibility, int duration) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(duration);
//        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(visibility);
    }

    public static String replaceString(String oldText) {
        if (oldText == null) return "";
        if (oldText.isEmpty()) return "";
        String resultText = oldText;
        if (oldText.contains("\\n")) {
            resultText = resultText.replace("\\n", "\n");
        }
        if (resultText.contains("\r")) {
            resultText = resultText.replace("\r", "");
        }
        if (resultText.contains("\'")) {
            resultText = resultText.replace("\\'", "\'");
        }
        if (resultText.contains("&amp;")) {
            resultText = resultText.replace("&amp;", "&");
        }
        return resultText;
    }


}
