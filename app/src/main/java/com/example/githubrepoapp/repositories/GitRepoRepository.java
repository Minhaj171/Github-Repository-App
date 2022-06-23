package com.example.githubrepoapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.githubrepoapp.R;
import com.example.githubrepoapp.app.AppController;
import com.example.githubrepoapp.endpoints.IRepoApi;
import com.example.githubrepoapp.models.RepoPojo;
import com.example.githubrepoapp.service.NetworkingService;
import com.google.gson.JsonObject;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class GitRepoRepository {
    private MutableLiveData<RepoPojo> repoPojoMutableLiveData;
    public Call<RepoPojo> repositoryCall;

    NetworkingService networkingService = new NetworkingService();

    public IRepoApi getRepoApi() {
        return networkingService.getRetrofit().create(IRepoApi.class);
    }

    public MutableLiveData<RepoPojo> getRepoFromRepository(String query) {
        repoPojoMutableLiveData = new MutableLiveData<>();
        fetchRepoData(query);
        return repoPojoMutableLiveData;
    }

    private void fetchRepoData(String query) {
        repositoryCall = getRepoApi().getAllRepositories(query);
        repositoryCall.enqueue(new Callback<RepoPojo>() {
            RepoPojo repoPojo = new RepoPojo();
            @Override
            public void onResponse(@NonNull Call<RepoPojo> call, @NonNull Response<RepoPojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    repoPojoMutableLiveData.setValue(response.body());
                } else if (response.code() == 401) {
                    repoPojo.setMessage(AppController.getInstance().getResources().getString(R.string.authentication_error));
                } else if (response.code() == 500) {
                    repoPojo.setMessage(AppController.getInstance().getResources().getString(R.string.server_error));
                } else {
                    repoPojo.setMessage(AppController.getInstance().getResources().getString(R.string.unknown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RepoPojo> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    repoPojo.setMessage(AppController.getInstance().getResources().getString(R.string.request_canceled));
                } else {
                    if (t instanceof SocketTimeoutException) {
                        repoPojo.setMessage(AppController.getInstance().getResources().getString(R.string.timed_out));
                    } else {
                        repoPojo.setMessage(AppController.getInstance().getResources().getString(R.string.no_internet));
                    }
                }
            }
        });
    }


}
