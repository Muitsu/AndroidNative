package com.example.messageapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.example.messageapp.api_service.UserRepository;
import com.example.messageapp.databinding.ActivityMainBinding;
import com.example.messageapp.navigation.Navigator;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import model.MainViewModel;

public class MainActivity extends AppCompatActivity {

    // Variables
    String data = "";

    ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        viewModel = MainViewModel.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize UI components
        initializeViews();
        // Fetch data from API
    }

    // Initialize UI components
    private void initializeViews() {
        binding.nextBtn.setOnClickListener(view -> navigateToSecondActivity());
        binding.skipBtn.setOnClickListener(view -> {
            UserRepository.getUsers(
                    responseData -> {
                        try {
                            AppLog.log(responseData.string(), "CALL API");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    },
                    errorMessage -> {
                        AppLog.log(errorMessage, "CALL API");
                    }
            );
        });
    }


    // Navigate to SecondActivity
    private void navigateToSecondActivity() {
        String text = viewModel.getData();
        Navigator.push(this, SecondActivity.class);
    }
}
