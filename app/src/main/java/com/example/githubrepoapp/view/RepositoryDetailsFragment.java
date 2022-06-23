package com.example.githubrepoapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.githubrepoapp.R;
import com.example.githubrepoapp.base.BaseFragment;
import com.example.githubrepoapp.databinding.FragmentRepositoryDetailsBinding;
import com.example.githubrepoapp.models.ItemPojo;

public class RepositoryDetailsFragment extends BaseFragment {
    FragmentRepositoryDetailsBinding binding;
    ItemPojo itemPojo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false);
        binding.setException("N/a");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            itemPojo = getArguments().getParcelable("itemDetails");
            binding.setBindingPojoDetails(itemPojo);
            binding.setProfilePhoto(itemPojo.getUser().getAvatarUrl());
        }
        binding.progressbarDetails.setVisibility(View.GONE);
    }

    @Override
    public void showErrorLayout(String message, boolean isError) {

    }

    @Override
    public void fetchDataFromViewModel() {

    }
}