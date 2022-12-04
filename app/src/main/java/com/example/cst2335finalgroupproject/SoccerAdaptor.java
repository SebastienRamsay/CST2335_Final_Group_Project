package com.example.cst2335finalgroupproject;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cst2335finalgroupproject.R;

import java.util.ArrayList;

public class SoccerAdaptor extends BaseAdapter {
    private ArrayList<SoccerObject> soccerList;
    private Context mContext;

    public SoccerAdaptor(Context context, ArrayList<SoccerObject> soccerList) {
        super();
        this.mContext = context;
        this.soccerList = soccerList;
    }

    public int getCount() {
        return this.soccerList.size();
    } //This function tells how many objects to show

    public SoccerObject getItem(int position) {
        return this.soccerList.get(position);
    }  //This returns the string at position p

    public long getItemId(int p) {
        return p;
    } //This returns the database id of the item at position p

    public View getView(int p, View recycled, ViewGroup parent) {
        View thisRow = recycled;
        SoccerObject soccerObject = getItem(p);
        thisRow = ((Activity) this.mContext).getLayoutInflater().inflate(R.layout.team_layout, null);

        TextView team1 = thisRow.findViewById(R.id.team1);
        team1.setText(soccerObject.getSide1());

        TextView team2 = thisRow.findViewById(R.id.team2);
        team2.setText(soccerObject.getSide2());

        return thisRow;
    }
}
