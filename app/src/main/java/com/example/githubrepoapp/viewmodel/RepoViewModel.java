package com.example.githubrepoapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubrepoapp.app.AppController;
import com.example.githubrepoapp.models.RepoPojo;
import com.example.githubrepoapp.repositories.GitRepoRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class RepoViewModel extends ViewModel {
    @Inject
    GitRepoRepository gitRepoRepository;

    public RepoViewModel() {
        AppController.getInstance().getViewModelComponent().inject(this);
    }

    public LiveData<RepoPojo> getRepo(String query) {
        return gitRepoRepository.getRepoFromRepository(query);
    }


}
