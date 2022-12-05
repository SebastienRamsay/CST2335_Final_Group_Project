package com.example.cst2335finalgroupproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLToImage extends AsyncTask<String, Void, Bitmap> {



    Bitmap bitmap;

    public URLToImage(ImageView image) {
        this.image = image;
    }

    ImageView image;


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

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        image.setImageBitmap(bitmap);

    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
