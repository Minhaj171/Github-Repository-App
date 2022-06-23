package com.example.githubrepoapp.repositories;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.githubrepoapp.R;
import com.example.githubrepoapp.app.AppController;
import com.example.githubrepoapp.endpoints.IRepoApi;
import com.example.githubrepoapp.models.RepoPojo;
import com.example.githubrepoapp.service.NetworkingService;
import com.google.gson.JsonObject;

import java.net.SocketTimeoutException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

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

    public MutableLiveData<RepoPojo> getRepoFromRepository(String query, String stars) {
        repoPojoMutableLiveData = new MutableLiveData<>();
        fetchRepoData(query, stars);
        return repoPojoMutableLiveData;
    }

//    private void fetchRepoDataFromCache(String query) {
//        getRepoApi().getAllRepositories(query)
//                .toObservable()
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<RepoPojo>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull RepoPojo repoPojo) {
//                        Log.d(TAG, "onNext: " + repoPojo.getItems().size());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d(TAG, "onError: " , e);
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

    private void fetchRepoData(String query, String stars) {
        repositoryCall = getRepoApi().getAllRepositories(query, stars);
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
