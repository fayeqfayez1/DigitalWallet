package com.app.utils.fileUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static File getNewImageFile(Context context) throws IOException {
        return File.createTempFile(String.valueOf(System.currentTimeMillis()), ".jpg",
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    public static String getPathFromUri(Activity mActivity, Uri uri) {
        if (uri == null) {
            return null;
        } else {
            String scheme = uri.getScheme();
            if (scheme.equals("file")) {
                return uri.getPath();
            } else if (scheme.equals("content")) {
                return getRealImagePathFromURI(mActivity.getContentResolver(), uri);
            } else {
                return uri.toString();
            }
        }
    }

    public static String getRealImagePathFromURI(ContentResolver contentResolver,
                                                 Uri contentURI) {
        Cursor cursor = contentResolver.query(contentURI, null, null, null,
                null);
        if (cursor == null)
            return contentURI.getPath();
        else {
            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            try {
                return cursor.getString(idx);
            } catch (Exception exception) {
                return null;
            }
        }
    }
}
