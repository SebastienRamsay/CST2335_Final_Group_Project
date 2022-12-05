package com.example.cst2335finalgroupproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.cst2335finalgroupproject.databinding.EventDetailsLayoutBinding;

import java.util.ArrayList;

public class EventDetailsFragment extends Fragment {


    EventDetailsLayoutBinding binding;
    EventDBObject event;


    public EventDetailsFragment(EventDBObject event) {
        this.event = event;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        EventDatabase db = Room.databaseBuilder(inflater.getContext(), EventDatabase.class, "EventDatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        EventDAO eventDAO = db.eventDAO();

        binding = EventDetailsLayoutBinding.inflate(inflater);

        binding.eventNameTextView.setText(event.getName());

        binding.eventStartingDateTextView.setText(event.getDateTime());

        String priceRange = event.getMin() + "-" + event.getMax();
        binding.ticketPriceRangeTextView.setText(priceRange);

        binding.urlButton.setOnClickListener(click -> {
            Uri uri = Uri.parse(event.getUrl()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        new URLToImage(binding.promotionalImageView).execute(event.getImageURL());


        if (eventDAO.getEvent(event.getUrl()) != null){
            binding.favouriteCheckBox.setChecked(true);
        }else{
            binding.favouriteCheckBox.setChecked(false);
        }



        binding.favouriteCheckBox.setOnClickListener(click ->{


            if (binding.favouriteCheckBox.isChecked()){

                eventDAO.insertEvent(event);

            }else{

                eventDAO.deleteEvent(event);

            }
        });


        return binding.getRoot();
    }

}
