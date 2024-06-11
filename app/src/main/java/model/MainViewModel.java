package model;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainActivity";
    private static MainViewModel instance;
    private int count = 0;
    private String data;


    private MainViewModel() {
    }

    public static MainViewModel getInstance() {

        if (instance == null) {
            instance = new MainViewModel();
        }
        return instance;
    }

    public String getData() {
        count = count + 1;
        if (data == null) {
            data = fetchDataFromApi();
        }
        return data + count;
    }

    private String fetchDataFromApi() {
        Log.d(TAG, "Fetching data");
        return "Ahmad Mui";
    }
}
