package com.example.cst2335finalgroupproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.cst2335finalgroupproject.databinding.WatchmatchBinding;

import java.io.InputStream;

public class SoccerFragment extends Fragment {

    String video;
    SoccerMain soccermain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        WatchmatchBinding binding = WatchmatchBinding.inflate(inflater);

        video = soccermain.match;
        binding.videoView.setVideoPath(video);

        return binding.getRoot();
    }
}
