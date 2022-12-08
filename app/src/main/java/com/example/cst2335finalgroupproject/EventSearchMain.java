package com.example.cst2335finalgroupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.cst2335finalgroupproject.databinding.ActivityEventSearchBinding;
import com.example.cst2335finalgroupproject.databinding.ActivityEventSearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Main class that allows the user to search an api of events.
 */
public class EventSearchMain extends AppCompatActivity {

    ActivityEventSearchBinding binding;
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

    /**
     * sets the toolbar to the toolbar in the xml
     * @param menu the toolbar menu
     * @return the created toolbar menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     *
     * @param item toolbar being passed into the method
     * @return recalls itself
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        Intent switchActivityIntent = new Intent();
//
//        switch(item.getItemId()){
//            case R.id.SoccerMain:
//                switchActivityIntent = new Intent(this, SoccerMain.class);
//                startActivity(switchActivityIntent);
//                break;
//            case R.id.PexelsMainActivity:
//                switchActivityIntent = new Intent(this, PexelsMainActivity.class);
//                startActivity(switchActivityIntent);
//                break;
//            default:
//                switchActivityIntent = new Intent(th66666644is, EventSearchMain.class);
//                startActivity(switchActivityIntent);
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * runs when the activity is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);
        this.savedInstanceState = savedInstanceState;


        binding = ActivityEventSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EventDatabase db = Room.databaseBuilder(getApplicationContext(), EventDatabase.class, "EventDatabase").allowMainThreadQueries().build();
        EventDAO eventDAO = db.eventDAO();

        setSupportActionBar(binding.toolBar);



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
            hideSoftKeyboard(EventSearchMain.this, click);
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
            hideSoftKeyboard(EventSearchMain.this, click);
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

    /**
     * hides the keyboard.
     * @param activity current activity
     * @param view current view
     */
    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /**
     * fetches the information from the api and sends it to a EVENTJSON object
     */
    private void fetchEvents() {
        RetrofitClient
                .getRetrofitClient()
                .getEmbededEvents("https://app.ticketmaster.com/discovery/v2/events.json?apikey=MgaWGVNGvLYPha8sw4Zgz9lIAzOJpT4b&city=" + binding.cityEditText.getText().toString() + "&radius=" + binding.radiusEditText.getText().toString())
                .enqueue(new Callback<EventJSON>() {
                    /**
                     * called when retrofit gets a response from the api
                      * @param call
                     * @param response
                     */
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


                    Context context = getApplicationContext();
                    CharSequence text = getString(R.string.successfulSearch);
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();



                }
            }

                    /**
                     * called when retrofit fails to get a response from the api
                     * @param call
                     * @param t
                     */
            @Override
            public void onFailure(Call<EventJSON> call, Throwable t) {
                Toast.makeText(EventSearchMain.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getCause() +  "\n" + t.getClass());
            }
        });
    }

    /**
     * gets the savd favourite events from the database and adds them the shared prefrences favourite events.
     */
    public void getFavouites(){
        try{
            eventModel.favouriteEvents.setValue(eventDAO.getAllEvents());
        }catch (Exception e){
            System.out.println("no favourites in view model");
        }
    }
}