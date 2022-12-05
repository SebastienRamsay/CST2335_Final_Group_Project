package com.example.cst2335finalgroupproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
@Entity
public class SoccerDBObject {
    @ColumnInfo(name = "competition")
    private String competition;

    @NonNull
    @PrimaryKey
    private String url;

    public SoccerDBObject (String competition, String url){
        this.competition = competition;
        this.url = url;
    }

    public String getCompetition(){
        return competition;
    }

    public void setCompetition(){
        this.competition = competition;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(){
        this.url = url;
    }
}
