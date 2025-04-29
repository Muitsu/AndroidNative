package com.example.messageapp.widgtes;


import android.util.Log;

public class AppLog {


    public static void secureErrorLog(String ClassName, String functionName, String log) {
        String printText = "**Secure Error Log**\n\nClass Name: " + ClassName + "\n\n" + "Function: " + functionName + "\n\n"
                + log + "\n\n**End of Secure Log**\n\n";

        Log.i("PRINT SECURE Error LOG", printText);
    }

    public static void log(String msg, String tag) {
        Log.i(tag, msg);
    }

    public static void log(String msg) {
        log(msg, "DEVELOPMENT");
    }
}

