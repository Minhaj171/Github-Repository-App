package com.example.githubrepoapp.di.components;

import com.example.githubrepoapp.di.moduls.DataContextModule;
import com.example.githubrepoapp.viewmodel.RepoViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
@Singleton
@Component(modules = { DataContextModule.class})
public interface IViewModelComponet {
    void inject(RepoViewModel viewModel);

}
