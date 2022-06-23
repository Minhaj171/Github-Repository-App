package com.example.githubrepoapp.models;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class Data {
    public String source;

    @Override
    public Data clone() {
        return new Data();
    }
}
