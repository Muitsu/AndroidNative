package com.example.messageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.messageapp.databinding.ActivitySecondBinding;
import com.example.messageapp.navigation.Navigator;
import com.example.messageapp.view_model.MainViewModel;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = MainViewModel.getInstance();
        binding.titleText.setText(viewModel.getData());
        binding.increaseBtn.setOnClickListener(click -> {
            viewModel.increaseCounter();
        });
        viewModel.watchCounter().observe(this, data -> {
            binding.counterText.setText(data);
        });

        binding.seeList.setOnClickListener((onClick) -> {
            Navigator.push(this, ThirdActivity.class);
        });
    }
}