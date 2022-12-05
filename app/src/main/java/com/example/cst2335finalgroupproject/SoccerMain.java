package com.example.cst2335finalgroupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cst2335finalgroupproject.databinding.WatchmatchlayoutBinding;
import com.example.cst2335finalgroupproject.databinding.SoccerLayoutBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoccerMain extends AppCompatActivity {

    SoccerLayoutBinding binding;
    SoccerViewModel soccerModel;
    SoccerAdaptor myAdapter;
    ListJSON embeddedMatches;
    ArrayList<ListJSON._Embedded.Title> titles = new ArrayList<>();
    LinearLayoutManager layoutManager;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soccer_layout);

        binding = SoccerLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        soccerModel = new ViewModelProvider(this).get(SoccerViewModel.class);

        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        binding.inputTeam.setText(soccerModel.laatteamsearched.getValue());

        String storedteamsearched = prefs.getString("laatteamsearched", "");
        binding.inputTeam.setText(storedteamsearched);

        layoutManager = new LinearLayoutManager(this);
        myAdapter = new SoccerAdaptor(titles, soccerModel);

        binding.search.setOnClickListener(click ->{
            titles.clear();
            fetchEvents();
            soccerModel.laatteamsearched.setValue(binding.inputTeam.getText().toString());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("laatteamsearched", binding.inputTeam.getText().toString());
            editor.apply();

        });

        soccerModel.selectedmatch.observe(this, (newListValue) -> {
            SoccerwatchFragment soccerwatchFragment = new SoccerwatchFragment( newListValue );
            getSupportFragmentManager().beginTransaction().add(R.id.watchmatch, soccerwatchFragment).commit();
        });
    }

    private void fetchEvents() {
        RetrofitClient
                .getRetrofitClient()
                .getEmbededTitle("https://www.scorebat.com/live-stream/"+ binding.inputTeam.getText() )
                .enqueue(new Callback<ListJSON>() {
                    @Override
                    public void onResponse(Call<ListJSON> call, Response<ListJSON> response) {
                        if (response.isSuccessful() && response.body() != null){
                            embeddedMatches = response.body();
                            titles.addAll(embeddedMatches.get_embedded().getTitles());
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<ListJSON> call, Throwable t) {
                        Toast.makeText(SoccerMain.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
