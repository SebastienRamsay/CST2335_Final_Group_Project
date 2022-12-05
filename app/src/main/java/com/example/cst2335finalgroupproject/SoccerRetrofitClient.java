package com.example.cst2335finalgroupproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerRetrofitClient {

    private static final String URL = "https://www.scorebat.com/";
    private static Retrofit retrofit = null;

    public static ApiClass getRetrofitClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiClass.class);
    }
}
