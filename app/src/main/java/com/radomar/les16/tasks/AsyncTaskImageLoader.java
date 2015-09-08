package com.radomar.les16.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Radomar on 01.09.2015.
 */
public class AsyncTaskImageLoader extends AsyncTask<URL, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(URL... params) {
        Bitmap bitmap = downloadImage(params[0]);
        return bitmap;
    }

    private Bitmap downloadImage(URL url) {
        Bitmap bmImg = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            int respCode = conn.getResponseCode();
            if (respCode == 200) {
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
                is.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        bmImg = Bitmap.createScaledBitmap(bmImg, 100, 100, true);
        return bmImg;
    }

}
