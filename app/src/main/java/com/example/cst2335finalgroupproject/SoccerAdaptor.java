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

/**
 * Creates the view for the current matches that are currently being played for
 * the FIFA 2022 games.
 */
public class SoccerAdaptor extends RecyclerView.Adapter<SoccerAdaptor.viewHolder> {

    private ArrayList<ListJSON._Embedded.Title> match;
    private SoccerViewModel soccerModel;

    /**
     * Constructor that passes an arraylist and view model parameter.
     * @param matches name of match currently being played
     * @param soccerModel ensures that the data is intact when the phone/tablet is rotated
     */
    public SoccerAdaptor(ArrayList<ListJSON._Embedded.Title> matches, SoccerViewModel soccerModel) {
        this.match = matches;
        this.soccerModel = soccerModel;
    }

    /**
     * This method is called when ViewHolder is created.
     * @param parent The
     * @param viewType Type of View
     * @return match name where user can select from.
     */
    @NonNull
    @Override
    public SoccerAdaptor.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_layout, parent, false);

        return new viewHolder(view);
    }

    /**
     *
     * @param holder The row that is being added
     * @param position The id of the match to be selected or displayed
     */
    @Override
    public void onBindViewHolder(@NonNull SoccerAdaptor.viewHolder holder, int position) {
        holder.matchName.setText(match.get(position).getTitle());
    }

    /**
     * Gets the number of teams that are currently playing in the FiFA 2022 games
     * @return number of teams that are playing right now.
     */
    @Override
    public int getItemCount() {
        return match.size();
    }

    /**
     * Opens the fragment where the person can view the highlights of selected team.
     */
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
