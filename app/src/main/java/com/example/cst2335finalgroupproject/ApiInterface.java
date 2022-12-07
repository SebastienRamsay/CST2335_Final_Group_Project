package com.example.cst2335finalgroupproject;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    /**
     * gets events embedded in the api object
     * @param url the api url to search with
     * @return returns the api information in java object format
     */
    @GET
    Call<EventJSON> getEmbededEvents(@Url String url);
}
