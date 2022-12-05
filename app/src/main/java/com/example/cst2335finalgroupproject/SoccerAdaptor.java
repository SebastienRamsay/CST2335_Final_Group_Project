package com.example.cst2335finalgroupproject;

import android.content.Context;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst2335finalgroupproject.databinding.TeamLayoutBinding;

import java.util.ArrayList;

public class SoccerAdaptor extends RecyclerView.Adapter<SoccerAdaptor.viewHolder> {

    private ArrayList<ListJSON._Embedded.Title> match;
    private SoccerViewModel soccerModel;

    public SoccerAdaptor(ArrayList<ListJSON._Embedded.Title> matches, SoccerViewModel soccerModel) {
        this.match = matches;
        this.soccerModel = soccerModel;
    }

    @NonNull
    @Override
    public SoccerAdaptor.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoccerAdaptor.viewHolder holder, int position) {
        holder.matchName.setText(match.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return match.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView matchName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click ->{

                int position = getAbsoluteAdapterPosition();
                ListJSON._Embedded.Title selected = match.get(position);
                soccerModel.selectedmatch.postValue(selected);
            });

            matchName = itemView.findViewById(R.id.matchname);
        }
    }
}
