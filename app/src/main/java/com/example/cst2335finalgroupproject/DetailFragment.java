package com.example.cst2335finalgroupproject;


import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


/**
this fragment show the datamodel details
 */
public class DetailFragment extends Fragment {
    //when the user clicks on each of the row in recycler view

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "DetailFragment";

    private DataModel mDataModel;

    /**
    this method makes an instance of this fragment
     */
    public static DetailFragment newInstance(DataModel dataModel) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, dataModel);
        fragment.setArguments(args);
        return fragment;
    }

    /**
    this method creates the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDataModel = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    /**
    this method creates the view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView heightTv = view.findViewById(R.id.heightTv);
        heightTv.setText(String.format("Height: %s", mDataModel.getHeight()));

        TextView widthTv = view.findViewById(R.id.widthTv);
        widthTv.setText(String.format("Width: %s", mDataModel.getWidth()));

        TextView urlTv = view.findViewById(R.id.urlTv);
        urlTv.setText(String.format("URL: %s", mDataModel.getImageUrl()));
        urlTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDataModel.getImageUrl()));
                startActivity(browserIntent);
            }
        });

        ImageView imageView = view.findViewById(R.id.iv);
        Glide.with(view).load(mDataModel.getImageUrl()).placeholder(R.drawable.ic_android_black_24dp).into(imageView);


        Button saveButton = view.findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // save image to storage
                new SaveImageLocalStorage(getContext()).execute(mDataModel);
                // save image to Database
                saveToDatabase(mDataModel);

            }
        });

        return view;
    }

    /**
    this method saves the dataModel to the sqlite database and saves the image to the local storage
     */
    private void saveToDatabase(DataModel dataModel) {
        MyDatabase dbHelper = new MyDatabase(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // save to DB
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.COL_ID, dataModel.getId());
        contentValues.put(MyDatabase.COL_IMAGE_URL, dataModel.getImageUrl());
        db.insert(MyDatabase.TABLE_NAME, null, contentValues);

    }



    /**
    this inner class handle saving the image to the local storage in another thread
     */
    class SaveImageLocalStorage extends AsyncTask<DataModel, Void, Void> {

        private final Context mContext;

        public SaveImageLocalStorage(final Context context)
        {
            mContext = context;
        }

        /**
        this method saving the image to the local storage in the background thread
         */
        protected Void doInBackground(DataModel... dataModels) {
            try {

                // download image
                URL url = new URL(dataModels[0].getImageUrl());
                Bitmap bitmapImage= BitmapFactory.decodeStream(url.openConnection().getInputStream());

                saveToInternalStorage(bitmapImage,dataModels[0]);

            } catch (Exception e) {
                return null;
            }
            return null;
        }

        /**
        this method calls after saving the image to the local storage
         */
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(mContext, R.string.toast_image_saved, Toast.LENGTH_SHORT).show();

        }

        /**
        this method saving the image to the local storage
        */
        private void saveToInternalStorage(Bitmap bitmapImage, DataModel model) {
            ContextWrapper cw = new ContextWrapper(getContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath = new File(directory, model.getId() + ".jpg");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}