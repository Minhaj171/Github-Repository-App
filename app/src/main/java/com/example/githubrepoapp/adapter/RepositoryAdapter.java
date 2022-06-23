package com.example.githubrepoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubrepoapp.databinding.ListItemBinding;
import com.example.githubrepoapp.models.ItemPojo;

import java.util.List;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {
    private List<ItemPojo> itemPojoList;
    private IRepoClick iRepoClick;

    public void updateItem(List<ItemPojo> itemPojoList, IRepoClick iRepoClick){
        this.itemPojoList = itemPojoList;
        this.iRepoClick = iRepoClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding listItemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemPojo itemPojo = itemPojoList.get(position);
        holder.bindView(itemPojo);
    }

    @Override
    public int getItemCount() {
        return itemPojoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemBinding itemBinding;

        public ViewHolder(@NonNull ListItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            itemBinding = listItemBinding;
        }

        public void bindView(ItemPojo itemPojo) {
            itemBinding.setBindingPojo(itemPojo);
            itemBinding.setException("N/a");
            itemBinding.itemContainer.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION){
                    iRepoClick.onItemClick(itemPojo);
                }
            });
        }
    }
}
