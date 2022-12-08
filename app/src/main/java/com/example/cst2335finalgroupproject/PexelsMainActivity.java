package com.example.cst2335finalgroupproject;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
* this is the main activity which will show the search results
 */
public class PexelsMainActivity extends BaseActivity implements ItemListener {

    // a variable for log tag of main activity
    private static final String TAG = "PexelsMainActivity";
    // a variable which store the pexels search url
    public static final String URL_SEARCH = "https://api.pexels.com/v1/search?query=";
    // a variable which store the api key of the pexels' api
    //authorization key
    public static final String API_KEY = "563492ad6f91700001000001414c4756870d4879b30aee7038a14a12";
    // a variable which store the adapter of the main activity
    // which is used for the listview
    private MyAdapter myAdapter;
    // a variable which store the list of the dataModel
    // which is needed for the adapter
    private ArrayList<DataModel> dataModels = new ArrayList<>();
    // a variable which store the search editText reference
    private EditText searchEt;
    // a variable which store search button reference
    private Button searchBtn;

    // this methods runs whenever opening the main activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getting the layoutInflater service
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate your activity layout here
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        // add main activity to the navigation drawer
        drawerLayout.addView(contentView, 0);

        // initialize the ui elements
        initView();
        // show the last search query
        showLastSearchQuery();
    }

    /**
    *     show the last search query
     */
    private void showLastSearchQuery() {
        prefs = this.getSharedPreferences("BuildConfig.APPLICATION_ID", Context.MODE_PRIVATE);
        String lastQuery = prefs.getString(PREF_LAST_SEARCH_QUERY, "");
        searchEt.setText(lastQuery);
        requestServer(lastQuery);
    }

    /*
    *     using Volley request Pexel server for entered query
     */
    private void requestServer(String query) {

        // get a volley instance
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // complete the search url
        String url = URL_SEARCH + query;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            // remove all dataModels
                            dataModels.clear();

                            // convert Json to dataModels
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("photos");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                DataModel model = new DataModel();
                                JSONObject object = (JSONObject) jsonArray.get(i);

                                model.setId(object.getInt("id"));
                                model.setPhotographer(object.getString("photographer"));
                                model.setWidth(object.getInt("width"));
                                model.setHeight(object.getInt("height"));
                                model.setImageUrl(object.getJSONObject("src").getString("original"));
                                model.setSmallImageUrl(object.getJSONObject("src").getString("landscape"));

                                // add dataModel to DataModel array
                                dataModels.add(model);
                            }
                            // update the listView with new dataModel array
                            myAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // log for error
                Log.d(TAG, "onErrorResponse: " + error);
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // add Authorization to header of the request
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", API_KEY);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /**
    *     initialize the UI elements
     */
    private void initView() {
        // instantiate the Adapter
        myAdapter = new MyAdapter(getApplicationContext(), dataModels, this, false);

        // initialize listview
        RecyclerView recyclerView = findViewById(R.id.rv);
        // set LinearLayoutManager to recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // set adapter to the listview
        recyclerView.setAdapter(myAdapter);


        // initialize search editText
        searchEt = findViewById(R.id.search_et);
        // initialize search button
        searchBtn = findViewById(R.id.search_btn);

        // set click listener for search button
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add search editText value to enteredText variable
                String enteredText = searchEt.getText().toString();
                // add search editText value to shared preferences
                // for next time which main activity will open
                prefs.edit().putString(PREF_LAST_SEARCH_QUERY, enteredText).apply();

                // request server for entered query
                requestServer(enteredText);


                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "You clicked the search button!", Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_close), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
        });

    }


    /**
    *     implement ItemListener interface
    *     to get the listview row click event
     */
    @Override
    public void rowClicked(DataModel dataModel) {
        Toast.makeText(getApplicationContext(), "item selected!", Toast.LENGTH_SHORT).show();

        // open DetailFragment when user click on a listview row
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, DetailFragment.newInstance(dataModel))
                .addToBackStack(null).commit();

    }


    /**
    *     this method is not needed here
    *     just leave it empty
     */
    @Override
    public void buttonClicked(DataModel dataModel) {
    }
}