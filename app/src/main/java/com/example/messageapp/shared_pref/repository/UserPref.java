package com.example.messageapp.shared_pref.repository;

import com.example.messageapp.shared_pref.SharedPrefManager;

public class UserPref {
    static String _nameKey = "name";
    private final SharedPrefManager<String> namePref;

    public UserPref() {
        namePref = new SharedPrefManager<>(_nameKey, String.class);
    }

    public void saveName(String data) {
        namePref.saveData(data);
    }

    public String getName(String data) {
        String resp = namePref.getData(data);
        return resp != null ? resp : "";
    }
}
