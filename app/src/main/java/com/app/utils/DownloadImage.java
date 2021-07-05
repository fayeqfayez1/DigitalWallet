package com.app.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    private OnTaskCompleted listener;
    private String extension;

    public DownloadImage(OnTaskCompleted listener, String extension) {
        this.listener = listener;
        this.extension = extension;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null)
            listener.onTaskStarted();
    }

    @Override
    protected Bitmap doInBackground(String... f_url) {

//        String imageURL = URL[0];
//
//        Bitmap bitmap = null;
//        try {
//// Download Image from URL
//            InputStream input = new java.net.URL(imageURL).openStream();
//// Decode Bitmap
//            bitmap = BitmapFactory.decodeStream(input);
//            FileUtils.createDirectoryAndSaveFile(bitmap);
//            if (listener != null)
//                listener.onTaskCompleted("success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (listener != null)
//                listener.onTaskCompleted("fail");
//        }
//        return bitmap;

        int count;
        try {
            File folder = new File(Environment.getExternalStorageDirectory()+ "/Em-Text Download");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            if (success) {
                // Do something on success
            } else {
                // Do something else on failure
            }
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // this will be useful so that you can show a tipical 0-100% progress bar
            int lenghtOfFile = conection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream
            //extension must change (mp3,mp4,zip,apk etc.)
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Em-Text Download/" + System.currentTimeMillis() + "." + extension);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

            if (listener != null)
                listener.onTaskCompleted("success");
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            if (listener != null)
                listener.onTaskCompleted(e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

    }

    public interface OnTaskCompleted {
        void onTaskStarted();

        void onTaskCompleted(String msg);
    }
}

