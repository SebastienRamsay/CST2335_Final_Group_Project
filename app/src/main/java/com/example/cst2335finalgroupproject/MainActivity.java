package com.example.cst2335finalgroupproject;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cst2335finalgroupproject.databinding.ActivityMainBinding;
import com.example.cst2335finalgroupproject.databinding.EventBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    EventViewModel eventModel;
    Adapter myAdapter;
    EventJSON embeddedEvents = new EventJSON();
    ArrayList<EventJSON._Embedded.Event> events = new ArrayList<>();
    ArrayList<EventDBObject> dbEvents = new ArrayList<>();
    LinearLayoutManager layoutManager;
    SharedPreferences prefs;
    Boolean inFavourites = false;
    List<EventDBObject> eventDBObjectList = new ArrayList<>();
    EventDAO eventDAO;
    Bundle savedInstanceState;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EventDatabase db = Room.databaseBuilder(getApplicationContext(), EventDatabase.class, "EventDatabase").allowMainThreadQueries().build();
        EventDAO eventDAO = db.eventDAO();

        try{
            eventModel.favouriteEvents.setValue(eventDAO.getAllEvents());
        }catch (Exception e){
            System.out.println("no favourites in view model");
        }


        eventModel = new ViewModelProvider(this).get(EventViewModel.class);

        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        binding.cityEditText.setText(eventModel.lastCitySearched.getValue());
        binding.radiusEditText.setText(eventModel.lastRadiusSearched.getValue());

        String storedCitySearched = prefs.getString("lastCitySearched", "");
        String storedRadiusSearched = prefs.getString("lastRadiusSearched", "");
        binding.cityEditText.setText(storedCitySearched);
        binding.radiusEditText.setText(storedRadiusSearched);



        layoutManager = new LinearLayoutManager(this);
        binding.eventRecyclerView.setLayoutManager(layoutManager);

        myAdapter = new Adapter(dbEvents, eventModel);
        binding.eventRecyclerView.setAdapter(myAdapter);

        binding.searchButton.setOnClickListener(click ->{
            inFavourites = false;
            dbEvents.clear();
            fetchEvents();

            eventModel.lastCitySearched.setValue(binding.cityEditText.getText().toString());
            eventModel.lastRadiusSearched.setValue(binding.radiusEditText.getText().toString());

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastRadiusSearched", binding.radiusEditText.getText().toString());
            editor.putString("lastCitySearched", binding.cityEditText.getText().toString());
            editor.apply();
            hideSoftKeyboard(MainActivity.this, click);
        });


        eventModel.selectedEvent.observe(this, (newEventValue) -> {


            EventDetailsFragment eventFragment = new EventDetailsFragment( newEventValue );
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.eventFrameLayout, eventFragment)
                    .addToBackStack("")
                    .commit();

        });

        binding.favouritesButton.setOnClickListener(click ->{

            dbEvents.clear();
            myAdapter.notifyDataSetChanged();

            if (!inFavourites){

                eventDBObjectList = eventDAO.getAllEvents();
                dbEvents.addAll(eventDBObjectList);
                myAdapter.notifyDataSetChanged();
                inFavourites = true;
            }else{
                myAdapter.notifyDataSetChanged();
                inFavourites = false;
            }

        });



    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        dbEvents.clear();
//        myAdapter.notifyDataSetChanged();
//
//        if (!inFavourites){
//
//            eventDBObjectList = eventDAO.getAllEvents();
//            dbEvents.addAll(eventDBObjectList);
//            myAdapter.notifyDataSetChanged();
//        }else{
//
//            super.onBackPressed();
//
//            myAdapter.notifyDataSetChanged();
//        }
//    }


    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private void fetchEvents() {
        RetrofitClient
                .getRetrofitClient()
                .getEmbededEvents("https://app.ticketmaster.com/discovery/v2/events.json?apikey=MgaWGVNGvLYPha8sw4Zgz9lIAzOJpT4b&city=" + binding.cityEditText.getText().toString() + "&radius=" + binding.radiusEditText.getText().toString())
                .enqueue(new Callback<EventJSON>() {
            @Override
            public void onResponse(Call<EventJSON> call, Response<EventJSON> response) {

                if (response.isSuccessful() && response.body() != null){
                    embeddedEvents = response.body();

                    int size = response.body().get_embedded().getEvents().size() - 1;
                        for (int i = 0; i < size; i++) {

                            String name = response.body().get_embedded().getEvents().get(i).getName();
                            String url = response.body().get_embedded().getEvents().get(i).getUrl();
                            String imageURL = response.body().get_embedded().getEvents().get(i).getImages().get(0).getUrl();
                            String dateTime = response.body().get_embedded().getEvents().get(i).getDates().getStart().getDateTime();
                            String min = new String();
                            String max = new String();
                            try{
                                min = response.body().get_embedded().getEvents().get(size).getPriceRanges().get(0).getMin();
                                max = response.body().get_embedded().getEvents().get(size).getPriceRanges().get(0).getMax();
                            }catch (Exception e){

                            }


                            EventDBObject selected = new EventDBObject(
                                    name,
                                    url,
                                    imageURL,
                                    dateTime,
                                    min,
                                    max);
                            dbEvents.add(selected);
                        }


                        eventModel.events.setValue(new ArrayList<>());
                        eventModel.events.setValue(dbEvents);
                        myAdapter.notifyDataSetChanged();






                }
            }

            @Override
            public void onFailure(Call<EventJSON> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getCause() +  "\n" + t.getClass());
            }
        });
    }

    public void getFavouites(){
        try{
            eventModel.favouriteEvents.setValue(eventDAO.getAllEvents());
        }catch (Exception e){
            System.out.println("no favourites in view model");
        }
    }
}