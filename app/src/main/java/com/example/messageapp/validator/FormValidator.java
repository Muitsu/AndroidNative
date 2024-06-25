package com.example.messageapp.validator;

import android.text.TextUtils;
import android.widget.EditText;

public class FormValidator {

    public static boolean isEmailValid(EditText editText) {
        String email = editText.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            editText.setError("Email is required");
            editText.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("Enter a valid email");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isPasswordValid(EditText editText) {
        String password = editText.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            editText.setError("Password is required");
            editText.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            editText.setError("Password must be at least 6 characters");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isTextValid(EditText editText, int minLength) {
        String text = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(text) && text.length() < minLength) {
            editText.setError("Text must be at least " + minLength + " characters");
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isNumberValid(EditText editText) {
        String numberStr = editText.getText().toString().trim();
        if (TextUtils.isEmpty(numberStr)) {
            editText.setError("Number is required");
            editText.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            editText.setError("Enter a valid number");
            editText.requestFocus();
            return false;
        }
        return true;
    }
}
