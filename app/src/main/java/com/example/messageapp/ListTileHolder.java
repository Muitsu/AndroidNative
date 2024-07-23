package com.example.messageapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.model.MessageModel;

public class ListTileHolder extends RecyclerView.ViewHolder {
    //If using viewbinding
//    ListtileViewBinding binding;
    //If using traditional method
    ImageView avatar;
    TextView title, subtitle;


    public ListTileHolder(@NonNull View itemView) {
        super(itemView);
        //ViewBinding method
//        binding = ListtileViewBinding.bind(itemView);
        //Traditional method
        avatar = itemView.findViewById(R.id.listTile_avatar);
        title = itemView.findViewById(R.id.listTile_title);
        subtitle = itemView.findViewById(R.id.listTile_subtitle);
    }

    public void bindMessage(MessageModel data) {
        //ViewBinding method
//        binding.listTileAvatar.setImageResource(data.getImage());
//        binding.listTileTitle.setText(data.getName());
//        binding.listTileSubtitle.setText(data.getEmail());
        avatar.setImageResource(data.getImage());
        title.setText(data.getName());
        subtitle.setText(data.getEmail());
    }
}
