package com.example.githubrepoapp.view;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.githubrepoapp.R;
import com.example.githubrepoapp.adapter.IRepoClick;
import com.example.githubrepoapp.adapter.RepositoryAdapter;
import com.example.githubrepoapp.base.BaseFragment;
import com.example.githubrepoapp.base.IUserSelectedDate;
import com.example.githubrepoapp.databinding.FragmentRepositoryBinding;
import com.example.githubrepoapp.models.ItemPojo;
import com.example.githubrepoapp.models.RepoPojo;
import com.example.githubrepoapp.receiver.NetworkChangeReceiver;
import com.example.githubrepoapp.viewmodel.RepoViewModel;

import java.text.ParseException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class RepositoryFragment extends BaseFragment implements IRepoClick, IUserSelectedDate {
    private static final String TAG = "RepositoryFragment";
    FragmentRepositoryBinding binding;
    RepoViewModel repoViewModel;
    private Timer timer = new Timer();
    private final long DELAY = 1000;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRepositoryBinding.inflate(inflater, container, false);
        binding.setCallback(this);
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
        if (NetworkChangeReceiver.isConnected()) {
            binding.dataLayout.setVisibility(View.VISIBLE);
            fetchsortedList("Android", "", "");
        } else {
            binding.dataLayout.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchSortedData() {
        binding.searchSection.setVisibility(View.VISIBLE);
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    binding.repositoryRecycler.setVisibility(View.GONE);
                    binding.progressbar.setVisibility(View.VISIBLE);
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fetchsortedList("Android", String.valueOf(s), "searchMethod");
                                }
                            });
                        }
                    }, DELAY);
                } else {
                    Toast.makeText(getContext(), "Please Enter Text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchsortedList(String query, String search, String tag) {
        repoViewModel.getRepo(query, search).observe(getViewLifecycleOwner(), new Observer<RepoPojo>() {
            @Override
            public void onChanged(RepoPojo repoPojo) {
                if (repoPojo != null) {
                    fetchSortedData();
                    setRepoLayout(repoPojo.getItems());
                    if (tag.equals("searchMethod") || tag.equals("calendarMethod") ){
                        binding.found.setVisibility(View.VISIBLE);
                        String text = "Repository found ( "+ repoPojo.getItems().size()+" )";
                        binding.found.setText(text);
                    }else {
                        binding.found.setVisibility(View.GONE);
                    }

                    Log.d(TAG, "fromApi: " + repoPojo.getItems().size());
                } else {
                    assert false;
                    Log.d(TAG, "fromApi: " + repoPojo.getMessage());
                }
                binding.repositoryRecycler.setVisibility(View.VISIBLE);
                binding.progressbar.setVisibility(View.GONE);
                hideKeyboard();
            }
        });

    }

    public void onCalendarClick() {
        showCalendar(this, "date");
    }

    @Override
    public void getUpdatedDate(String date, String tag) throws ParseException {
        if (date != null && tag.equals("date")){
            binding.search.setText(date);
            fetchsortedList("android", date, "calendarMethod");
        }else{
            Toast.makeText(getContext(), "Please select date", Toast.LENGTH_SHORT).show();
        }
    }


//    private void fetchRepoData() {
//        repoViewModel.getRepo("Android", "").observe(getViewLifecycleOwner(), new Observer<RepoPojo>() {
//            @Override
//            public void onChanged(RepoPojo repoPojo) {
//                if (repoPojo != null){
//                    setRepoLayout(repoPojo.getItems());
//                    Log.d(TAG, "fromApi: " + repoPojo.getItems().size());
//                }else {
//                    assert false;
//                    Log.d(TAG, "fromApi: " + repoPojo.getMessage());
//                }
//                binding.progressbar.setVisibility(View.GONE);
//            }
//        });
//    }

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
        Bundle bundle = new Bundle();
        bundle.putParcelable("itemDetails", itemPojo);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_repositoryFragment_to_repositoryDetailsFragment, bundle);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
    }

}