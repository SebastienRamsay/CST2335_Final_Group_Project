package com.example.cst2335finalgroupproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventViewModel extends ViewModel {

    MutableLiveData<List<EventDBObject>> events = new MutableLiveData<>();
    MutableLiveData<List<EventDBObject>> favouriteEvents = new MutableLiveData<>();
    MutableLiveData<EventDBObject> selectedEvent = new MutableLiveData<>();
    MutableLiveData<String> lastCitySearched = new MutableLiveData<>();
    MutableLiveData<String> lastRadiusSearched = new MutableLiveData<>();

}
