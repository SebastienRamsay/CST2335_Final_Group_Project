package com.example.cst2335finalgroupproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SoccerViewModel extends ViewModel {

    MutableLiveData<ArrayList<ListJSON._Embedded.Title>> match = new MutableLiveData<ArrayList<ListJSON._Embedded.Title>>();
    MutableLiveData<ListJSON._Embedded.Title> selectedmatch = new MutableLiveData<>();
    MutableLiveData<String> laatteamsearched = new MutableLiveData<>();
}
