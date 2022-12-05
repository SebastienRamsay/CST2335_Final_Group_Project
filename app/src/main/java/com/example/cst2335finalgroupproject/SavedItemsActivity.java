package com.example.cst2335finalgroupproject;


import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

/*
* this activity will shows the saved items
 */
public class SavedItemsActivity extends BaseActivity implements ItemListener {

    private MyAdapter myAdapter;
    private ArrayList<DataModel> items = new ArrayList<>();
    private SQLiteDatabase db;

    /*
     * create the SavedItemsActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate your activity layout here
        View contentView = inflater.inflate(R.layout.activity_saved_items, null, false);
        drawerLayout.addView(contentView, 0);

        initView();

        loadDataFromDatabase();
        myAdapter.notifyDataSetChanged();

    }

    /*
    * initialize the view elements
     */
    private void initView() {
        myAdapter = new MyAdapter(getApplicationContext(), items, this, true);
        RecyclerView recyclerView = findViewById(R.id.lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    /*
    * this method loads data from the sqlite database
     */
    private void loadDataFromDatabase() {
        MyDatabase myDatabase = new MyDatabase(this);
        db = myDatabase.getWritableDatabase();

        String[] columns = {MyDatabase.COL_ID, MyDatabase.COL_IMAGE_URL};
        Cursor results = db.query(false, MyDatabase.TABLE_NAME, columns, null, null, null, null, null, null);


        int idColumnIndex = results.getColumnIndex(MyDatabase.COL_ID);
        int imageUrlColumnIndex = results.getColumnIndex(MyDatabase.COL_IMAGE_URL);

        while (results.moveToNext()) {
            long id = results.getLong(idColumnIndex);
            String imageUrl = results.getString(imageUrlColumnIndex);

            DataModel dataModel = new DataModel();
            dataModel.setId(id);
            dataModel.setImageUrl(imageUrl);
            items.add(dataModel);
        }

    }

    /*
     *     this method is not needed here
     *     just leave it empty
     */
    @Override
    public void rowClicked(DataModel dataModel) {
    }

    /*
    this method calls when the user clicks delete button
     */
    @Override
    public void buttonClicked(DataModel dataModel) {

        new AlertDialog.Builder(this)
                // set title for AlertDialog
                .setTitle(R.string.info_title)
                // set message for AlertDialog
                .setMessage("Do you really want to delete the image?")
                // set button for AlertDialog
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myAdapter.removeItem(dataModel);
                        deleteItem(dataModel);
                    }
                })
                .setNeutralButton(android.R.string.no,null)
                // set icon for AlertDialog
                .setIcon(android.R.drawable.ic_dialog_alert)
                // show AlertDialog
                .show();


    }

    /*
    this method deletes the selected dataModel or imagefile from the database and the image from local storage
     */
    private void deleteItem(DataModel dataModel) {
        // delete from local storage
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, dataModel.getId() + ".jpg");
        boolean deleted = file.delete();


        // deletes entity from database
        db.delete(MyDatabase.TABLE_NAME, MyDatabase.COL_ID + "=" + dataModel.getId(), null);


        // show Snackbar
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, R.string.snackbar_image_deleted, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_close), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();

        // show toast
        Toast.makeText(getApplicationContext(), R.string.snackbar_image_deleted, Toast.LENGTH_SHORT).show();

    }
}