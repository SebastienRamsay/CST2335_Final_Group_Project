package com.example.cst2335finalgroupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    EventViewModel eventModel;
    Adapter myAdapter;
    EventJSON embeddedEvents;
    ArrayList<EventJSON._Embedded.Event> events = new ArrayList<>();
    LinearLayoutManager layoutManager;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        myAdapter = new Adapter(events, eventModel);
        binding.eventRecyclerView.setAdapter(myAdapter);

        binding.searchButton.setOnClickListener(click ->{
            events.clear();
            fetchEvents();

            eventModel.lastCitySearched.setValue(binding.cityEditText.getText().toString());
            eventModel.lastRadiusSearched.setValue(binding.radiusEditText.getText().toString());

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastRadiusSearched", binding.radiusEditText.getText().toString());
            editor.putString("lastCitySearched", binding.cityEditText.getText().toString());
            editor.apply();
        });


        eventModel.selectedEvent.observe(this, (newEventValue) -> {


            EventDetailsFragment eventFragment = new EventDetailsFragment( newEventValue );
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.eventFrameLayout, eventFragment)
                    .commit();

        });


    }

    private void fetchEvents() {
        RetrofitClient
                .getRetrofitClient()
                .getEmbededEvents("https://app.ticketmaster.com/discovery/v2/events.json?apikey=MgaWGVNGvLYPha8sw4Zgz9lIAzOJpT4b&city=" + binding.cityEditText.getText() + "&radius=" + binding.radiusEditText.getText())
                .enqueue(new Callback<EventJSON>() {
            @Override
            public void onResponse(Call<EventJSON> call, Response<EventJSON> response) {
                if (response.isSuccessful() && response.body() != null){
                    embeddedEvents = response.body();

//                    for (int i = 0; i < embeddedEvents.get_embedded().getNumberOfEvents(); i++){
//                        events.add(embeddedEvents.get_embedded().getEvent(i));
//                        myAdapter.notifyItemInserted(i);
//                    }
                    events.addAll(embeddedEvents.get_embedded().getEvents());

                    myAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<EventJSON> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}