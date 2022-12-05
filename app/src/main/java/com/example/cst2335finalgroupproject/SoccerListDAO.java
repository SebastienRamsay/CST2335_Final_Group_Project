package com.example.cst2335finalgroupproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SoccerListDAO {

    @Insert
    void insertlist(SoccerDBObject soccer);

    @Query("SELECT * FROM SoccerDBObject")
    List<SoccerDBObject> getAllsoccer();

    @Query("SELECT * FROM SoccerDBObject WHERE url = :url")
    SoccerDBObject geturl(String url);

    @Delete
    void deletelist(SoccerDBObject soccer);

}
