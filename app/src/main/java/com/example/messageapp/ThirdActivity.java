package com.example.messageapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.databinding.ActivityThirdBinding;
import com.example.messageapp.model.MessageModel;
import com.example.messageapp.widgtes.RecyclerViewHelper;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewHelper<MessageModel> recycleHelper;
    ActivityThirdBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityThirdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = binding.listview;
        recycleHelper = new RecyclerViewHelper<>(this, recyclerView);
        recycleHelper.setItemLayout(item -> R.layout.listtile_view);
        List<MessageModel> messages = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            messages.add(new MessageModel(R.drawable.ic_launcher_foreground, "Ahmad" + i, "ahmad" + i + "@gmail.com"));
        }
        recycleHelper.setData(messages);
        recycleHelper.setOnBindItem((viewItem, item, index) -> {
            ImageView leading = viewItem.findViewById(R.id.listTile_avatar);
            leading.setImageDrawable(getDrawable(item.getImage()));
            TextView title = viewItem.findViewById(R.id.listTile_title);
            title.setText(item.getName());
            TextView subtitle = viewItem.findViewById(R.id.listTile_subtitle);
            subtitle.setText(item.getEmail());
            viewItem.setOnClickListener(v -> {
                Toast.makeText(this, "clicked item index is " + index, Toast.LENGTH_SHORT).show();
            });
        });

        recycleHelper.build();
    }
}