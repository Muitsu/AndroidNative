package com.example.messageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import model.MainViewModel;

public class SecondActivity extends AppCompatActivity {

    TextView titleTxt;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        viewModel = MainViewModel.getInstance();
        titleTxt = findViewById(R.id.titleText);
        titleTxt.setText(viewModel.getData());

    }
}