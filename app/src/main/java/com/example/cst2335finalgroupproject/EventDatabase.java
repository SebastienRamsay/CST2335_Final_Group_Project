package com.example.cst2335finalgroupproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {EventDBObject.class}, version = 4)
public abstract class EventDatabase extends RoomDatabase {

    public abstract EventDAO eventDAO();

}
