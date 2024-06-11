package com.example.messageapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.example.messageapp.navigation.Navigator;

import model.MainViewModel;

public class MainActivity extends AppCompatActivity {

    // Variables
    String data = "";
    Button nextButton, skipButton;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        viewModel = MainViewModel.getInstance();
        // Initialize UI components
        initializeViews();

        // Fetch data from API

    }

    // Initialize UI components
    private void initializeViews() {
        nextButton = findViewById(R.id.nextBtn);
        skipButton = findViewById(R.id.skipBtn);
        nextButton.setOnClickListener(view -> navigateToSecondActivity());
        skipButton.setOnClickListener(view -> navigateToSecondActivity());
    }


    // Navigate to SecondActivity
    private void navigateToSecondActivity() {
        String text = viewModel.getData();
        Navigator.push(this, SecondActivity.class);
    }
}
