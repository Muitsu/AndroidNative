package com.example.messageapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        recyclerView = findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<MessageModel> messages = new ArrayList<MessageModel>();
        for (int i = 0; i < 20; i++) {
            messages.add(new MessageModel(R.drawable.ic_launcher_foreground, "Ahmad" + i, "ahmad" + i + "@gmail.com"));
        }
        adapter = new ListViewAdapter(messages, getApplicationContext(),
                (index) -> {
                    Toast.makeText(this, "clicked item index is " + index, Toast.LENGTH_SHORT).show();
                }
        );
        recyclerView.setAdapter(adapter);

    }
}