package com.example.messageapp.shared_pref;


import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefManager<T> {
    private static Context applicationContext;
    private final String key;
    private final SharedPreferences sharedPreferences;
    private final Class<T> type;

    public SharedPrefManager(String key, Class<T> type) {
        if (applicationContext == null) {
            throw new IllegalStateException("SharedPrefManager is not initialized. Call initialize() with context first.");
        }
        this.key = key;
        this.sharedPreferences = applicationContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        this.type = type;
    }

    public static void initialize(Context context) {
        applicationContext = context.getApplicationContext();
    }

    public boolean clear() {
        return sharedPreferences.edit().clear().commit();
    }

    public boolean isKeyExist() {
        return sharedPreferences.contains(key);
    }

    public boolean saveData(T data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (type.equals(String.class)) {
            editor.putString(key, (String) data);
        } else if (type.equals(Integer.class)) {
            editor.putInt(key, (Integer) data);
        } else if (type.equals(Float.class)) {
            editor.putFloat(key, (Float) data);
        } else if (type.equals(Long.class)) {
            editor.putLong(key, (Long) data);
        } else if (type.equals(Boolean.class)) {
            editor.putBoolean(key, (Boolean) data);
        } else {
            return false;
        }
        return editor.commit();
    }

    public T getData(T defaultVal) {
        if (!sharedPreferences.contains(key)) {
            return null;
        }
        if (type.equals(String.class)) {
            return type.cast(sharedPreferences.getString(key, defaultVal == null ? null : (String) defaultVal));
        } else if (type.equals(Integer.class)) {
            return type.cast(sharedPreferences.getInt(key, defaultVal == null ? 0 : (int) defaultVal));
        } else if (type.equals(Float.class)) {
            return type.cast(sharedPreferences.getFloat(key, defaultVal == null ? 0f : (float) defaultVal));
        } else if (type.equals(Long.class)) {
            return type.cast(sharedPreferences.getLong(key, defaultVal == null ? 0L : (long) defaultVal));
        } else if (type.equals(Boolean.class)) {
            return type.cast(sharedPreferences.getBoolean(key, defaultVal != null && (boolean) defaultVal));
        } else {
            throw new UnsupportedOperationException("Shared Pref Unsupported type: " + type.getSimpleName());
        }
    }

    public T getData() {
        return getData(null);
    }
}
