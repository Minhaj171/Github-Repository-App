package com.example.githubrepoapp.sources;

import com.example.githubrepoapp.models.Data;

import java.util.Date;

import rx.Observable;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class NetworkDataSource {

    public Observable<Data> getData() {
        return Observable.create(emitter -> {
            Data data = new Data();
            data.source = "Network";
            emitter.onNext(data);
            emitter.onCompleted();
        });
    }

}
