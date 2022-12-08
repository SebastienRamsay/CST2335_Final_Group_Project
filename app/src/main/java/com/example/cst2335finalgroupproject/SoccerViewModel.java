package com.example.cst2335finalgroupproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * This class uses shared preferences for data to survive rotational changes.
 */
public class SoccerViewModel extends ViewModel {

    MutableLiveData<ArrayList<ListJSON._Embedded.Title>> match = new MutableLiveData<ArrayList<ListJSON._Embedded.Title>>();
    MutableLiveData<ListJSON._Embedded.Title> selectedmatch = new MutableLiveData<>();
    MutableLiveData<String> laatteamsearched = new MutableLiveData<>();
}
