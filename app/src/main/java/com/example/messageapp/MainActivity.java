package com.example.messageapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.example.messageapp.api_service.UserModel;
import com.example.messageapp.api_service.UserRepository;
import com.example.messageapp.databinding.ActivityMainBinding;
import com.example.messageapp.navigation.Navigator;

import com.example.messageapp.permission_manager.PermissionManager;
import com.example.messageapp.view_model.MainViewModel;

public class MainActivity extends AppCompatActivity {

    // Variables
    String data = "";
    ActivityMainBinding binding;
    private PermissionManager permissionManager;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        viewModel = MainViewModel.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        permissionManager = new PermissionManager(this);
        if (!permissionManager.checkPermissions()) {
            permissionManager.showPermissionsDeniedDialog(this);
        }
        // Initialize UI components
        initializeViews();
    }

    // Initialize UI components
    private void initializeViews() {
        binding.nextBtn.setOnClickListener(view -> navigateToSecondActivity());
        binding.skipBtn.setOnClickListener(view -> {
            UserRepository.getUsers(
                    responseData -> {
                        AppLog.log(UserModel.fromListToJson(responseData));
                    },
                    errorMessage -> {
                        AppLog.log(errorMessage);
                    }
            );
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionManager.handlePermissionsResult(requestCode, grantResults)) {
            // All permissions granted, you can proceed with accessing location and camera
            Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show();
        } else {
            // One or more permissions denied
            Toast.makeText(this, "One or more permissions denied", Toast.LENGTH_SHORT).show();
        }
    }

    // Navigate to SecondActivity
    private void navigateToSecondActivity() {
        viewModel.fetchDataFromApi();
        Navigator.push(this, SecondActivity.class);
    }
}
