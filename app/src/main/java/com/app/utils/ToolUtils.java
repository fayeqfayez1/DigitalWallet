package com.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.app.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ToolUtils {

    public static boolean isNetworkConnected() {
        boolean HaveConnectedWifi = false;
        boolean HaveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equals("WIFI")) ;
            if (ni.isConnected())
                HaveConnectedWifi = true;
            if (ni.getTypeName().equals("MOBILE"))
                if (ni.isConnected())
                    HaveConnectedMobile = true;
        }
        return HaveConnectedWifi || HaveConnectedMobile;
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (imm != null) {
                if (view == null) {
                    view = new View(activity);
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }

    public static void getHash(Activity mActivity) {
        PackageInfo info;
        try {
            info = mActivity.getPackageManager().getPackageInfo(App.getInstance().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.d("hashKey", something);
                Log.e("packageName", App.getInstance().getPackageName());
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    public static void showSnackbarLengthLong(Activity activity, View view, String msg) {
        if (activity != null && view != null) {
            Snackbar mSnackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
            TextView mainTextView = (mSnackBar.getView()).findViewById(R.id.snackbar_text);
//            Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/al_jazeera_arabic_regular.ttf");
            mainTextView.setTextColor(activity.getResources().getColor(R.color.white));
//            mainTextView.setTypeface(font);
            mainTextView.setTextDirection(View.TEXT_DIRECTION_RTL);
            mSnackBar.show();
        }
    }

    static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5F);
    }

    public static String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public static void setRoundedImgWithProgress(final Context context, String url, final ImageView imageView, final AVLoadingIndicatorView prog) {
        prog.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(R.drawable.circle_place_holder).into(imageView);
            prog.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()//.override(50,50)
                    .placeholder(R.drawable.circle_place_holder))
                    .listener(new RequestListener<Drawable>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Glide.with(context).load(R.drawable.circle_place_holder);
                            prog.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            prog.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(imageView);
        }

    }

    public static void setRoundedImgWithProgress(final Context context, String url, final ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(R.drawable.circle_place_holder).into(imageView);
        } else {
            Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()//.override(50,50)
                    .placeholder(R.drawable.circle_place_holder))
                    .listener(new RequestListener<Drawable>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            if (AppSharedData.getUserData() != null && TextUtils.isEmpty(AppSharedData.getUserData().getUserImage())) {
//                                Glide.with(context).load(AppSharedData.getUserData().getUserImage());
//                            } else {
                            Glide.with(context).load(R.drawable.circle_place_holder);
//                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(imageView);
        }

    }


    public static void setImgWithProgress(final Context context, String url, final ImageView imageView, final AVLoadingIndicatorView prog) {
        prog.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(R.drawable.image_placeholder).centerCrop().into(imageView);
            prog.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(url).placeholder(R.drawable.image_placeholder).centerCrop()
                    .listener(new RequestListener<Drawable>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Glide.with(context).load(R.drawable.image_placeholder).centerCrop();
                            prog.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            prog.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(imageView);
        }

    }

    public static void setImg(final Context context, String url, final ImageView imageView, final AVLoadingIndicatorView prog) {
        prog.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(R.drawable.image_placeholder).into(imageView);
            prog.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(url).placeholder(R.drawable.image_placeholder)
                    .listener(new RequestListener<Drawable>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Glide.with(context).load(R.drawable.image_placeholder).centerCrop();
                            prog.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            prog.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(imageView);
        }

    }


    public static void setSmallRoundedImgWithProgress(final Context context, String url, final ImageView imageView, final AVLoadingIndicatorView prog) {
        prog.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).apply(new RequestOptions()
                    .transforms(new CenterCrop(), new RoundedCorners(ToolUtils.dp2px(context, 10)))
                    .placeholder(R.color.gray_60))
                    .into(imageView);
            prog.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(url).apply(new RequestOptions()
                    .transforms(new CenterCrop(), new RoundedCorners(ToolUtils.dp2px(context, 10)))
                    .placeholder(R.color.gray_60))
                    .listener(new RequestListener<Drawable>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Glide.with(context).load(R.color.gray_60).apply(new RequestOptions()
                                    .transforms(new CenterCrop(), new RoundedCorners(ToolUtils.dp2px(context, 10)))
                                    .placeholder(R.color.gray_60));
                            prog.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            prog.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(imageView);
        }

    }
//    public static void navigateToLogin(Activity mActivity) {
//        Intent intent = new Intent(SignInActivity.newInstance(mActivity));
////        AppSharedData.setUserData(null);
////        AppSharedData.setUserLogin(false);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        mActivity.startActivity(intent);
//        mActivity.finish();
//    }

    public static boolean checkIfEqualDate(long milliseconds) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = new Date(milliseconds * 1000);
        Date currentDate = Calendar.getInstance().getTime();
        try {
            format.setTimeZone(TimeZone.getDefault());
            String today = format.format(currentDate);
            currentDate = format.parse(today);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String dateToCheck = format.format(inputDate);
            format.setTimeZone(TimeZone.getDefault());
            inputDate = format.parse(dateToCheck);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (inputDate != null && currentDate != null) {
            return inputDate.equals(currentDate);
        } else {
            return false;
        }
    }

    public static String convertDate(Long dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, dateInMilliseconds * 1000).toString();
    }

    public static long convertDateToMillisecond(String dateS) {
        long timeInMilliseconds = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.US);
        try {
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = formatter.parse(dateS);
            formatter.setTimeZone(TimeZone.getDefault());

            String ourDate = formatter.format(date);
            date = formatter.parse(ourDate);
            timeInMilliseconds = date.getTime();

            System.out.println("Date in milli :: " + timeInMilliseconds);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static String convertMillisecondToDate(long milliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        return formatter.format(new Date(milliseconds * 1000));
    }

    private static Date getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a", Locale.US);
        Date currentDate = Calendar.getInstance().getTime();
        String date = formatter.format(currentDate);
        try {
            currentDate = formatter.parse(date);
        } catch (ParseException e) {

        }
        return currentDate;
    }

    private static Date parseCurrentDateTime(Date currentDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = formatter.format(currentDate);
        try {
            currentDate = formatter.parse(date);
        } catch (ParseException e) {

        }
        return currentDate;
    }

    public static String getLocalTimeString(long milliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date dateServer = new Date(milliseconds);
        return formatter.format(dateServer);
    }

    public static String getRandomKey() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static Integer getRandomInteger() {
        Random random = new Random();
        return random.nextInt(20000);
    }

    public static String convertLongTime(long mileSegundos) {
        return getLocalTimeString(mileSegundos * 1000);
//        return new SimpleDateFormat("hh:mm a", new Locale("en")).format(new Date(mileSegundos * 1000));
    }

    public static void setImg(final Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url)
                .into(imageView);
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static long getGMTTimeInMillisecond() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        return cal.getTimeInMillis() / 1000;
    }


    public static CharSequence convertTimeStamp(long mileSegundos) {
        return DateUtils.getRelativeTimeSpanString(mileSegundos * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

    public static String getPath(Uri uri, Activity mActivity) {
        if (uri == null)
            return null;
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = mActivity.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    public static void printHours() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
        try {

            Date date = sdf.parse("10:00 am");
            Date toData = sdf.parse("10:00 pm");


            for (int i = 1; i < 5; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, i);
                System.out.println("Time here " + calendar.getTime());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public static void refreshFCMToken() {
    }


    public static boolean checkFileIsDownloaded(String path) {
        File file = new File(path);
        return file.exists();
    }


    public static String getCreateTime(long dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String strLocalDate = "";
//        try {
        Date createdDate = new Date(dateString);
        Date currentDate = getCurrentDateTime();

        formatter.setTimeZone(TimeZone.getDefault());
        long diff = currentDate.getTime() - createdDate.getTime();
        if (diff < 0) {
            diff = 0;
        }
        long seconds = diff / 1000;
        long hours = seconds / 3600;
        long days = hours / 24;
        long remainder = seconds % 3600;
        long minutes = remainder / 60;


        if (days == 0 && hours == 0 && minutes == 0) {

            return String.format(Locale.ENGLISH, "%d sec", seconds);

        } else if (days == 0 && hours == 0 && minutes != 0) {

            return String.format(Locale.ENGLISH, "%d min", minutes);


        } else if (days == 0 && hours != 0 && minutes == 0) {

            return String.format(Locale.ENGLISH, "%d h", hours);


        } else if (days == 0 && hours != 0 && minutes != 0) {

            return String.format(Locale.ENGLISH, "%d h %d min", hours, minutes);
        } else if (days != 0) {
            return String.format(Locale.ENGLISH, "%d day", days);

        }

//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return strLocalDate;
    }


    private static String readErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            String message = jsonObject.optString("message");
            return message;
        } catch (Exception e) {
            Log.e("error", e.getMessage() + "");
            return App.getInstance().getString(R.string.error);
        }
    }
    public static CharSequence convertToEngNo(String format) {
        String result = format;
        result = format.replace("١", "1").replace("٢", "2").replace("٣", "3")
                .replace("٤", "4").replace("٥", "5").replace("٦", "6")
                .replace("٧", "7").replace("٨", "8").replace("٩", "9")
                .replace("٠", "0")
                .replace("٫", ".");
        return result;
    }

    public static String getCurrentLanguage() {
        String lang = "en";
        return lang;
    }


    public static String getDate(String createdDate, String format) {
        if (TextUtils.isEmpty(createdDate))
            return "";
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
        SimpleDateFormat LocalDayAndMonthFormatter = new SimpleDateFormat("yyyy.MM.dd hh:mm a", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        String strLocalDate = "";
        try {
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = formatter.parse(createdDate);
            LocalDayAndMonthFormatter.setTimeZone(TimeZone.getDefault());
            strLocalDate = LocalDayAndMonthFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return strLocalDate;
        }
        return strLocalDate;
    }

    public static String getOrderDate(String createdDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
        SimpleDateFormat LocalDayAndMonthFormatter = new SimpleDateFormat("yyyy.MM.dd hh:mm a", Locale.US);

        String strLocalDate = null;
        try {
            Date date = formatter.parse(createdDate);
            strLocalDate = LocalDayAndMonthFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strLocalDate;
    }

    public static String getCompleteAddressString(Activity activity, double LATITUDE, double LONGITUDE) {
        String strAdd = LATITUDE + "," + LONGITUDE;
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                android.location.Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
                return strAdd;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return strAdd;
        }
        return strAdd;
    }

    public static String getMyCountry(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(App.getInstance(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses == null || addresses.isEmpty()) {
                return "Unknown Address";
            }
            if (addresses.get(0) == null) {
                return "Unknown Address";
            }
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            if (address != null) {
                return address;
            }
            if (city != null) {
                return city;
            }
            if (state != null) {
                return state;
            }
            if (country != null) {
                return country;
            }
            return "Unknown Address";
        } catch (IOException e) {
            return "Unknown Address";
        }
    }


    public static RequestBody getRequestBody(String data) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), data);
    }

    public static Date getDateByString(String dateStr, String format) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertStringDateToUtc(String dateStr, String format) {
        Date date = null;
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            date = simpleDateFormat.parse(dateStr);

            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            result = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public static boolean compareDateAfter(Date selectedDate, Date currentDate) {
        Log.e("date", "compareDateAfter: selectedDate" + selectedDate);
        Log.e("date", "compareDateAfter: currentDate" + currentDate);

        return selectedDate != null && (selectedDate.after(currentDate));
    }

    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        } catch (MalformedURLException e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        // find end index for ?
        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        // find end index for #
        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }
        // calculate the end index
        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }

    public static boolean checkCouponProductIdEqual(ArrayList<Integer> productIds, Integer productsId) {
        for (Integer item : productIds) {
            if (item.equals(productsId))
                return true;
        }
        return false;
    }

    public static String convertToFormatTwoDigit(double num) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat formatter = new DecimalFormat("#0.00", symbols);
        formatter.setRoundingMode(RoundingMode.FLOOR);
        return formatter.format(num);
    }

    public static Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }
}
