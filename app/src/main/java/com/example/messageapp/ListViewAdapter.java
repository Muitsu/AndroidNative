package com.example.messageapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.model.MessageModel;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListTileHolder> {

    List<MessageModel> messages;
    Context context;
    OnItemClickListener onClick;

    public interface OnItemClickListener {
        void onItemClick(int index);
    }

    public ListViewAdapter(List<MessageModel> messages, Context context, OnItemClickListener onClick) {
        this.messages = messages;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ListTileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ListTileHolder(inflater.inflate(R.layout.listtile_view, parent, false));
        //If use ViewBinding
        //ListtileViewBinding binding = ListtileViewBinding.inflate(inflater);
        //return new ListViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ListTileHolder holder, int index) {
        holder.itemView.setOnClickListener((onCLick) -> {
            onClick.onItemClick(index);
        });
        holder.bindMessage(messages.get(index));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
