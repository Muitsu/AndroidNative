package com.example.messageapp.navigation;

import android.app.Activity;
import android.content.Intent;

public class Navigator {

    // Method to navigate to a new activity
    public static void push(Activity currentActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivity(intent);
        PageTransition.transitionTo(currentActivity, PageTransition.Transition.RIGHT_TO_LEFT);
    }

    // Method to navigate to a new activity and finish the current activity
    public static void pushAndFinish(Activity currentActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivity(intent);
        PageTransition.transitionTo(currentActivity, PageTransition.Transition.RIGHT_TO_LEFT);
        currentActivity.finish();
    }

    // Method to navigate to a new activity and clear the backstack
    public static void pushAndClearBackStack(Activity currentActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
        PageTransition.transitionTo(currentActivity, PageTransition.Transition.RIGHT_TO_LEFT);
    }

    // Method to pop the current activity from the backstack
    public static void pop(Activity currentActivity) {
        currentActivity.finish();
        PageTransition.transitionBack(currentActivity, PageTransition.Transition.RIGHT_TO_LEFT);
    }

    // Method to replace the current activity with a new activity
    public static void pushReplacement(Activity currentActivity, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivity(intent);
        PageTransition.transitionTo(currentActivity, PageTransition.Transition.RIGHT_TO_LEFT);
        currentActivity.finish();
    }
}
