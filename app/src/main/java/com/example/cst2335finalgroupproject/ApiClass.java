package com.example.cst2335finalgroupproject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiClass {

    @GET
    Call<ListJSON> getEmbededTitle(@Url String url);
}
