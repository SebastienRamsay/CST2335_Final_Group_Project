package com.example.cst2335finalgroupproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

/**
 *     is a base class for all activities
 *     we add here general tools which is used in all activities
 *     not about UI, use it to control toolbar, navigation drawer etc
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // is a base class for all activities
    // we add here general tools which is used in all activities
    //not about UI, use it to control toolbar, navigation drawer etc

    // a variable for DrawerLayout
    protected DrawerLayout drawerLayout;
    // a variable for Toolbar
    protected Toolbar myToolbar;
    // a variable for SharedPreferences
    protected SharedPreferences prefs;
    // a constant for SharedPreferences
    protected final String PREF_LAST_SEARCH_QUERY = "PREF_LAST_SEARCH_QUERY";

    /*
    * create the baseActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // initialize Toolbar
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // change toolbar title depend on activity name
        changeToolbarTitle();
        // Set Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(myToolbar);
        // initialize DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // initialize ActionBarDrawerToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                myToolbar, R.string.open, R.string.close);
        // add actionBarDrawerToggle to drawerLayout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        // Synchronize the state of the drawer indicator/affordance with the linked DrawerLayout
        actionBarDrawerToggle.syncState();

        // initialize NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Set a listener that will be notified when a menu item is selected
        navigationView.setNavigationItemSelectedListener(this);

    }

    /*
    * change toolbar title depend on activity name
     */
    private void changeToolbarTitle() {
        // check if user is currently in SavedItemsActivity or not
        if (this instanceof SavedItemsActivity) {
            // set toolbar title and application version to myToolbar
            myToolbar.setTitle(getString(R.string.title_saved_items_activity) + " v1 " + "Yiting Yao");
        }
        // check if user is currently in PexelsMainActivity or not
        else if (this instanceof PexelsMainActivity) {
            // set toolbar title and application version to myToolbar
            myToolbar.setTitle(getString(R.string.title_main_activity) + " v1 " + "Yiting Yao");
        }
    }

    /*
    * this method calls whenever user click on an item of navigation drawer
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // check which item of navigation drawer is clicked
        switch (item.getItemId()) {
            case R.id.item_main:
                // check if user is currently in PexelsMainActivity or not
                if (!(this instanceof PexelsMainActivity)) {
                    // create intent for PexelsMainActivity
                    Intent intent1 = new Intent(this, PexelsMainActivity.class);
                    // start PexelsMainActivity
                    startActivity(intent1);
                }
                break;
            case R.id.item_saved_items:
                // check if user is currently in SavedItemsActivity or not
                if (!(this instanceof SavedItemsActivity)) {
                    // create intent for SavedItemsActivity
                    Intent intent2 = new Intent(this, SavedItemsActivity.class);
                    // start SavedItemsActivity
                    startActivity(intent2);
                }
                break;

        }
        // close navigation drawer
        drawerLayout.close();
        return true;
    }

    /*
    * this method calls whenever user click on toolbar icon
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // check which item of toolbar icon is clicked
        switch (item.getItemId()) {
            case R.id.menu1:
                // check if user is currently in PexelsMainActivity or not
                if (this instanceof PexelsMainActivity) {
                    // create an alert dialog
                    new AlertDialog.Builder(this)
                            // set title for AlertDialog
                            .setTitle(R.string.info_title)
                            // set message for AlertDialog
                            .setMessage(R.string.info_main_activity)
                            // set button for AlertDialog
                            .setNeutralButton(android.R.string.ok, null)
                            // set icon for AlertDialog
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            // show AlertDialog
                            .show();
                }
                // check if user is currently in SavedItemsActivity or not
                else if (this instanceof SavedItemsActivity) {
                    // create an alert dialog
                    new AlertDialog.Builder(this)
                            // set title for AlertDialog
                            .setTitle(R.string.info_title)
                            // set message for AlertDialog
                            .setMessage(R.string.info_saved_items)
                            // set button for AlertDialog
                            .setNeutralButton(android.R.string.ok, null)
                            // set icon for AlertDialog
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            // show AlertDialog
                            .show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * this method initialize the contents of the Activity's standard options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // get menu inflater
        MenuInflater inflater = getMenuInflater();
        // Inflate a menu hierarchy from the specified XML resource
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }
}