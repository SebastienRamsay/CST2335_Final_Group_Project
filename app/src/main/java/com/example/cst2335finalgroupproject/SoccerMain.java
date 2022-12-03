package com.example.cst2335finalgroupproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cst2335finalgroupproject.databinding.SoccerLayoutBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

public class SoccerMain extends AppCompatActivity {
    protected RequestQueue queue = null;
    protected String match;
    SoccerLayoutBinding binding;
    SoccerFragment soccerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        binding = SoccerLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.search.setOnClickListener(click -> {
            match = binding.inputTeam.getText().toString();
            String URL = "https:\\/\\/www.scorebat.com\\/live-stream\\/" + URLEncoder.encode(match) + "\\/";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
              (response) -> {
                JSONArray SoccerArray = response.getJSONArray("name");
                JSONObject position0 = SoccerArray.getJSONObject(0);
                String livestream = position0.getString("url");
              });
              soccerFragment.getActivity();
            queue.add(request);
        });

    }
}