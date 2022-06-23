package com.example.githubrepoapp.endpoints;

import com.example.githubrepoapp.models.RepoPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public interface IRepoApi {
    @GET("search/repositories")
    Call<RepoPojo> getAllRepositories(@Query("q") String query);

}
