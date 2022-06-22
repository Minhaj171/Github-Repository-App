package com.example.githubrepoapp.di.moduls;

import com.example.githubrepoapp.repositories.GitRepoRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */

@Module
public class DataContextModule {
    @Provides
    GitRepoRepository getRepository() {
        return new GitRepoRepository();
    }
}
