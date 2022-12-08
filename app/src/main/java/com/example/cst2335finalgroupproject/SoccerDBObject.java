package com.example.cst2335finalgroupproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * This class collects the information from the api for the data base.
 */
@Entity
public class SoccerDBObject {
    @ColumnInfo(name = "competition")
    private String competition;

    @NonNull
    @PrimaryKey
    private String url;

    /**
     * constructor that passes the varibles competition and url to the database.
     * @param competition
     * @param url
     */
    public SoccerDBObject (String competition, String url){
        this.competition = competition;
        this.url = url;
    }

    /**
     * gets the match name
     * @return match name
     */
    public String getCompetition(){
        return competition;
    }

    /**
     * sets the match name
     */
    public void setCompetition(){
        this.competition = competition;
    }

    /**
     * gets the url of the api
     * @return url of the api
     */
    public String getUrl(){
        return url;
    }

    /**
     * sets the url of the api
     */
    public void setUrl(){
        this.url = url;
    }
}
