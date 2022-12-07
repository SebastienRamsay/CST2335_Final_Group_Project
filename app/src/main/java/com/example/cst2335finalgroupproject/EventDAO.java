package com.example.cst2335finalgroupproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EventDAO {

    /**
     * insert an event into the database
     * @param event the event to be inserted into the database
     */
    @Insert
    void insertEvent(EventDBObject event);

    /**
     * get all events from the database
     * @return the full list of events stored in the database
     */
    @Query("SELECT * FROM EventDBObject")
    List<EventDBObject> getAllEvents();

    /**
     * get an event from the database with this url
     * @param url the url to check for when getting the event
     * @return the event that has the specified url
     */
    @Query("SELECT * FROM EventDBObject WHERE url = :url")
    EventDBObject getEvent(String url);

    /**
     * deletes an event form the database
     * @param event the event to be deleted from the database
     */
    @Delete
    void deleteEvent(EventDBObject event);

}
