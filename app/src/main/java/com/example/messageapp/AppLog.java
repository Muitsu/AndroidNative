package com.example.messageapp;


import android.util.Log;

public class AppLog {


    public static void printSecureErrorLog(String ClassName, String functionName, String log) {
        String printText = "**DBKLPay Secure Error Log**\n\nClass Name: " + ClassName + "\n\n" + "Function: " + functionName + "\n\n"
                + log + "\n\n**End of Secure Log**\n\n";

        Log.i("PRINT SECURE Error LOG", printText);
    }

    public static void log(String msg, String tag) {
        Log.i(tag, msg);
    }
}

