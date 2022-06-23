package com.example.githubrepoapp.sources;

import com.example.githubrepoapp.models.*;

import rx.Observable;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class DataSources {
    private final MemoryDataSource memoryDataSource;
    private final DiskDataSource diskDataSource;
    private final NetworkDataSource networkDataSource;

    public DataSources(MemoryDataSource memoryDataSource, DiskDataSource diskDataSource, NetworkDataSource networkDataSource) {
        this.memoryDataSource = memoryDataSource;
        this.diskDataSource = diskDataSource;
        this.networkDataSource = networkDataSource;
    }

    public Observable<Data> getDataFromMemory(){
        return memoryDataSource.getData();
    }

    public Observable<Data> getDataFromDisk(){
        return diskDataSource.getData();
    }

    public Observable<Data> getDataFromNetwork(){
        return diskDataSource.getData().doOnNext(data -> {
            diskDataSource.saveToDisk(data);
            memoryDataSource.cacheMemory(data);
        });
    }
}
