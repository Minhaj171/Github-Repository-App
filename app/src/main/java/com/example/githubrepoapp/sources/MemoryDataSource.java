package com.example.githubrepoapp.sources;

import com.example.githubrepoapp.models.Data;

import java.util.Date;

import rx.Observable;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class MemoryDataSource {
    private Data data;

    public Observable<Data> getData() {
        return Observable.create(emitter -> {
            if (data != null) {
                emitter.onNext(data);
            }
            emitter.onCompleted();
        });
    }

    public void cacheMemory(Data data){
        this.data = data.clone();
        this.data.source = "memory";
    }
}
