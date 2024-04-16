package com.example.messageapp;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.messageapp.navigation.Navigator;

public class MainActivity extends AppCompatActivity {

    // Variables
     String data = "";
     Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        initializeViews();

        // Fetch data from API
        fetchDataFromApi();
    }

    // Initialize UI components
    private void initializeViews() {
        nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(view -> navigateToSecondActivity());
    }

    // Fetch data from API
    private void fetchDataFromApi() {
        // Simulate API call
        data = "Some data";
    }

    // Navigate to SecondActivity
    private void navigateToSecondActivity() {
        Navigator.push(this, SecondActivity.class);
    }
}
