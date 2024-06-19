package com.example.messageapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.messageapp.api_service.UserModel;
import com.example.messageapp.api_service.UserRepository;
import com.example.messageapp.databinding.ActivityMainBinding;
import com.example.messageapp.navigation.Navigator;

import com.example.messageapp.view_model.MainViewModel;

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
                        AppLog.log(UserModel.fromListToJson(responseData), "CALL API");
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
