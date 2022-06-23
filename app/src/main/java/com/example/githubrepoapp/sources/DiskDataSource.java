package com.example.githubrepoapp.sources;

import com.example.githubrepoapp.models.Data;

import rx.Observable;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class DiskDataSource {
    private Data data;

    public Observable<Data> getData() {
        return Observable.create(emitter -> {
            if (data != null) {
                emitter.onNext(data);
            }
            emitter.onCompleted();
        });
    }

    public void saveToDisk(Data data){
        this.data = data.clone();
        this.data.source = "disk";
    }
}
