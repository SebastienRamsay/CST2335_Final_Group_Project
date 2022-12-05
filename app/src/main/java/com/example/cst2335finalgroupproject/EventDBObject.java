package com.example.cst2335finalgroupproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class EventDBObject {


    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @PrimaryKey
    private String url;

    @ColumnInfo(name = "imageURL")
    private String  imageURL;

    @ColumnInfo(name = "dateTime")
    private String dateTime;

    @ColumnInfo(name = "min")
    private String min;

    @ColumnInfo(name = "max")
    private String max;

    public EventDBObject( String name, String url, String imageURL, String dateTime, String min, String max) {
        this.name = name;
        this.url = url;
        this.imageURL = imageURL;
        this.dateTime = dateTime;
        this.min = min;
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDateTime() {
        return dateTime;
    }

}


