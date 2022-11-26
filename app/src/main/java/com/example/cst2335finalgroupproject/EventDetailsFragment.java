package com.example.cst2335finalgroupproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335finalgroupproject.databinding.EventBinding;
import com.example.cst2335finalgroupproject.databinding.EventDetailsLayoutBinding;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EventDetailsFragment extends Fragment {


    EventDetailsLayoutBinding binding;
    EventJSON._Embedded.Event event;


    public EventDetailsFragment(EventJSON._Embedded.Event event) {
        this.event = event;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = EventDetailsLayoutBinding.inflate(inflater);

        binding.eventNameTextView.setText(event.getName());
        binding.eventStartingDateTextView.setText(event.getDates().getStart().getDateTime());
//        binding.ticketPriceRangeTextView.setText();
        binding.urlTextView.setText(event.getUrl());
        binding.promotionalImageView.setImageBitmap(urlToBitmap(event.getImages().get(0).getUrl()));

        return binding.getRoot();
    }

    public Bitmap urlToBitmap(String src){

        try {

            //uncomment below line in image name have spaces.
            //src = src.replaceAll(" ", "%20");

            URL url = new URL(src);



            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            return null;
        }
    }
}
