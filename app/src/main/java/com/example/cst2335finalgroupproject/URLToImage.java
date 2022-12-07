package com.example.cst2335finalgroupproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * converts a url into a bitmap and then assigns it to an image object
 */
public class URLToImage extends AsyncTask<String, Void, Bitmap> {



    Bitmap bitmap;

    public URLToImage(ImageView image) {
        this.image = image;
    }

    ImageView image;


    /**
     *
     * @param url the url to turn into a bitmap
     * @return Bitmap
     */
    @Override
    protected Bitmap doInBackground(String... url) {

        InputStream stream = null;
        try {
            stream = new URL(url[0]).openStream();
            bitmap = BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * sets the bitmap to an image object
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        image.setImageBitmap(bitmap);

    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
