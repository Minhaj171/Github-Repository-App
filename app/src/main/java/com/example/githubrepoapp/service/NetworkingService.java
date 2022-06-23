package com.example.githubrepoapp.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class NetworkingService {
    private static final String TAG = "NetworkingService";
    private static NetworkingService instance = null;
    public static String API_BASE_URL = "https://api.github.com/";

    protected Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    protected Retrofit retrofit = builder.build();

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
