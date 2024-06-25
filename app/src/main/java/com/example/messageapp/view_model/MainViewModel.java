package com.example.messageapp.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messageapp.AppLog;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainActivity";
    private static MainViewModel instance;
    private final MutableLiveData<String> liveCounter = new MutableLiveData<>();
    private int count = 0;
    private int count1 = 0;
    private String data;


    private MainViewModel() {
    }

    //Singleton Pattern
    public static MainViewModel getInstance() {

        if (instance == null) {
            instance = new MainViewModel();
        }
        return instance;
    }

    public String getData() {
        AppLog.log("View model fetch data");

        if (data == null) {
            fetchDataFromApi();
        }
        return data;
    }

    public LiveData<String> watchCounter() {
        return liveCounter;
    }

    public void increaseCounter() {
        count1 += 1;
        String newData = "Counter :" + count1;
        liveCounter.setValue(newData);
    }

    public void fetchDataFromApi() {
        count += 1;
        data = "Ahmad Mui" + count;
    }
}
