package com.example.messageapp.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Navigator {


    // Method to navigate to a new activity
    public static void push(Context currActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currActivity, activityClass);
        currActivity.startActivity(intent);
    }

    // Method to navigate to a new activity with data
    public static void push(Context context, Class<? extends Activity> activityClass, Bundle extras) {
        Intent intent = new Intent(context, activityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    // Method to navigate to a new activity and finish the current activity
    public static void pushAndFinish(Activity currActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currActivity, activityClass);
        currActivity.startActivity(intent);
        currActivity.finish();
    }

    // Method to navigate to a new activity and clear the backstack
    public static void pushAndClearBackStack(Activity currActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currActivity, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currActivity.startActivity(intent);
    }

    // Method to pop the current activity from the backstack
    public static void pop(Activity currActivity) {
        currActivity.finish();
    }

    // Method to pop the current activity from the backstack
    public static void popUntil(Activity currActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currActivity, activityClass);
        // Clears all activities on top of the specified one in the stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        currActivity.startActivity(intent);
    }

    // Method to replace the current activity with a new activity
    public static void pushReplacement(Activity currActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currActivity, activityClass);
        currActivity.startActivity(intent);
        currActivity.finish();
    }
}