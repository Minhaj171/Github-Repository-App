package com.example.githubrepoapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.githubrepoapp.adapter.IRepoClick;
import com.example.githubrepoapp.adapter.RepositoryAdapter;
import com.example.githubrepoapp.base.BaseFragment;
import com.example.githubrepoapp.databinding.FragmentRepositoryBinding;
import com.example.githubrepoapp.models.ItemPojo;
import com.example.githubrepoapp.models.RepoPojo;
import com.example.githubrepoapp.viewmodel.RepoViewModel;

import java.util.List;


public class RepositoryFragment extends BaseFragment implements IRepoClick {
    private static final String TAG = "RepositoryFragment";
    FragmentRepositoryBinding binding;
    RepoViewModel repoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRepositoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repoViewModel = new ViewModelProvider(this).get(RepoViewModel.class);
        fetchDataFromViewModel();
    }

    @Override
    public void fetchDataFromViewModel() {
        //showDialog();
        binding.progressbar.setVisibility(View.VISIBLE);
        repoViewModel.getRepo("Android").observe(getViewLifecycleOwner(), new Observer<RepoPojo>() {
            @Override
            public void onChanged(RepoPojo repoPojo) {
                if (repoPojo != null){
                    setRepoLayout(repoPojo.getItems());
                    Log.d(TAG, "fromApi: " + repoPojo.getItems().size());
                }else {
                    assert false;
                    Log.d(TAG, "fromApi: " + repoPojo.getMessage());
                }
                binding.progressbar.setVisibility(View.GONE);
            }
        });
    }

    private void setRepoLayout(List<ItemPojo> items) {
        RepositoryAdapter repositoryAdapter = new RepositoryAdapter();
        binding.repositoryRecycler.setAdapter(repositoryAdapter);
        binding.repositoryRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        repositoryAdapter.updateItem(items, this);
    }

    @Override
    public void showErrorLayout(String message, boolean isError) {

    }


    @Override
    public void onItemClick(ItemPojo itemPojo) {

    }
}