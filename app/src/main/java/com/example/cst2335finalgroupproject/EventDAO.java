package com.example.cst2335finalgroupproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EventDAO {

    @Insert
    void insertEvent(EventDBObject event);

    @Query("SELECT * FROM EventDBObject")
    List<EventDBObject> getAllEvents();

    @Query("SELECT * FROM EventDBObject WHERE url = :url")
    EventDBObject getEvent(String url);

    @Delete
    void deleteEvent(EventDBObject event);

}
