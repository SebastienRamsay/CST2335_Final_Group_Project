package com.example.cst2335finalgroupproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class EventViewModel extends ViewModel {

    MutableLiveData<ArrayList<EventJSON._Embedded.Event>> events = new MutableLiveData<ArrayList<EventJSON._Embedded.Event>>();
    MutableLiveData<EventJSON._Embedded.Event> selectedEvent = new MutableLiveData<>();
    MutableLiveData<String> lastCitySearched = new MutableLiveData<>();
    MutableLiveData<String> lastRadiusSearched = new MutableLiveData<>();

}
