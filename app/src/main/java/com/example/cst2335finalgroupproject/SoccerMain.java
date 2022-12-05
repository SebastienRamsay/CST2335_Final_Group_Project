package com.example.cst2335finalgroupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cst2335finalgroupproject.databinding.TeamLayoutBinding;
import com.example.cst2335finalgroupproject.databinding.SoccerLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoccerMain extends AppCompatActivity {
    TeamLayoutBinding binding2;
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
        binding.soccerlist.setLayoutManager(layoutManager);

        myAdapter = new SoccerAdaptor(titles, soccerModel);
        binding.soccerlist.setAdapter(myAdapter);

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
        System.out.println("hjhjjhjjh");
        SoccerRetrofitClient
                .getRetrofitClient()
                .getEmbededTitle("https://www.scorebat.com/video-api/v3/competition/" + binding.inputTeam.getText().toString() + "-premier-league/?token=Mzg5NDNfMTY3MDI4MjEyNF8yYzc1NjY2ODcwMGU1ZWE3YjM2OGEyYjM5Nzk4MjY0Yjc4Y2ZmOGZl" )
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
