package com.example.cst2335finalgroupproject;


import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst2335finalgroupproject.databinding.EventBinding;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.cache.InternalCache;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    private ArrayList<EventDBObject> events;
    private EventViewModel eventModel;

    public Adapter(ArrayList<EventDBObject> events, EventViewModel eventModel) {
        this.events = events;
        this.eventModel = eventModel;
    }


    @NonNull
    @Override
    public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position) {
        holder.eventName.setText(events.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView eventName;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click ->{

                int position = getAbsoluteAdapterPosition();

                eventModel.selectedEvent.postValue(events.get(position));

            });

            eventName = itemView.findViewById(R.id.eventName);
        }
    }
}
