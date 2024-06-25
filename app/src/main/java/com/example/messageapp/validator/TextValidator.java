package com.example.messageapp.validator;

import android.text.TextUtils;

public class TextValidator {

    public static boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password, int pwdLength) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return password.length() >= pwdLength;
    }

    public static boolean isTextEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    public static boolean isNumberValid(String numberStr) {
        if (TextUtils.isEmpty(numberStr)) {
            return false;
        }
        try {
            Integer.parseInt(numberStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
