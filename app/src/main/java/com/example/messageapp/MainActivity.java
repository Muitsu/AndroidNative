package com.example.messageapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.annotation.NonNull;

import android.widget.Toast;

import com.example.messageapp.api_service.ApiUtil;
import com.example.messageapp.notification.NotificationService;
import com.example.messageapp.retrofit_service.UserModel;
import com.example.messageapp.retrofit_service.UserRepository;
import com.example.messageapp.api_service.ProfileRepository;
import com.example.messageapp.databinding.ActivityMainBinding;
import com.example.messageapp.navigation.Navigator;

import com.example.messageapp.permission_manager.PermissionManager;
import com.example.messageapp.view_model.MainViewModel;
import com.example.messageapp.widgtes.AppLog;
import com.example.messageapp.widgtes.ToastManager;

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
        binding.btnProfile.setOnClickListener(v -> {
            ProfileRepository.getProfile(
                    new ApiUtil<>(() -> {
                        //TODO PRE EXECUTE
                        ToastManager.showToast(this, "Fetch Profile ...");
                    }, (success) -> {
                        //TODO ON SUCCESS
                        ToastManager.showToast(this, success.toJson());
                    }, (errorMessage) -> {
                        //TODO ON FAILED
                        ToastManager.showToast(this, "Error :" + errorMessage);
                    }));

        });

        binding.btnShowNoti.setOnClickListener(v -> {
            NotificationService.show(1, "Halu", "Yeayyyy");
        });
        binding.btnSchedule.setOnClickListener(v -> {
            // Schedule a notification to be shown 10 seconds from now
            long triggerAtMillis = System.currentTimeMillis() + 10000;
            NotificationService.showScheduled(1, "Scheduled Notification", "This will show after 10 seconds", triggerAtMillis);

        });
        binding.btnOpenList.setOnClickListener(v -> Navigator.push(this, FourthScreen.class));
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
