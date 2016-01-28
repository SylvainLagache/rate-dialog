package com.lagache.sylvain.library;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lagache.sylvain.library.fragments.RateDialogFragment;
import com.lagache.sylvain.library.helpers.PreferencesHelper;

/**
 * Created by user on 28/01/2016.
 */
public class AppRate {

    public static final String TAG = "AppRate";

    private static AppRate singleton;

    private final Context context;

    private int firstShow = 3;

    private int showInterval = 3;

    private float intervalMultiplier = 1;

    private AppRate(Context context) {
        this.context = context.getApplicationContext();
    }

    public static AppRate with(Context context) {
        if (singleton == null) {
            synchronized (AppRate.class) {
                if (singleton == null) {
                    singleton = new AppRate(context);
                }
            }
        }
        return singleton;
    }

    public int getFirstShow() {
        return firstShow;
    }

    public AppRate setFirstShow(int firstShow) {
        this.firstShow = firstShow;
        PreferencesHelper.saveFirstShow(context, firstShow);
        if (PreferencesHelper.getNextTimeOpen(context) == -1){
            PreferencesHelper.saveNextTimeOpen(context, firstShow);
        }
        return this;
    }

    public int getShowInterval() {
        return showInterval;
    }

    public AppRate setShowInterval(int showInterval) {
        this.showInterval = showInterval;
        int currentInterval = PreferencesHelper.getShowInterval(context);
        int calculatedInterval = PreferencesHelper.getCalculatedInterval(context);
        if ((currentInterval != showInterval) || (calculatedInterval == -1)){
            PreferencesHelper.saveShowInterval(context, showInterval);
            PreferencesHelper.saveCalculatedInterval(context, showInterval);
        }
        return this;
    }

    public float getIntervalMultiplier() {
        return intervalMultiplier;
    }

    public AppRate setIntervalMultiplier(float intervalMultiplier) {
        this.intervalMultiplier = intervalMultiplier;
        PreferencesHelper.saveShowMultiplier(context, intervalMultiplier);
        return this;
    }

    public static boolean shouldShowDialog(Context context){
        boolean neverShowAgain = PreferencesHelper.getNeverShowAgain(context);
        if (!neverShowAgain) {
            int currentState = PreferencesHelper.getIncrement(context);
            int nextTimeOpen = PreferencesHelper.getNextTimeOpen(context);
            return (currentState >= nextTimeOpen);
        }
        return false;
    }

    private static void incrementCurrentState(Context context){
        int currentState = PreferencesHelper.getIncrement(context);
        PreferencesHelper.saveIncrement(context, currentState+1);
    }

    public static void showDialogIfNeeded(AppCompatActivity activity,
                                          String title,
                                          String message,
                                          String playstoreMessage,
                                          String suggestionMessage,
                                          String cancelMessage,
                                          String neverShowAgainMessage){

        if (shouldShowDialog(activity)){
            showDialog(activity, title, message, playstoreMessage, suggestionMessage,
                    cancelMessage, neverShowAgainMessage);
            calculateNextShow(activity);
        }
        incrementCurrentState(activity);
    }

    public static void showDialogIfNeeded(AppCompatActivity activity){
        logEverything(activity);
        if (shouldShowDialog(activity)){
            showDialog(activity);
            calculateNextShow(activity);
        }
        incrementCurrentState(activity);
    }

    private static void showDialog(AppCompatActivity activity,
                                   String title,
                                   String message,
                                   String playstoreMessage,
                                   String suggestionMessage,
                                   String cancelMessage,
                                   String neverShowAgainMessage){
        RateDialogFragment rateDialogFragment = RateDialogFragment.newInstance(title, message,
                playstoreMessage, suggestionMessage, cancelMessage, neverShowAgainMessage);
        if (activity.getSupportFragmentManager().findFragmentByTag(RateDialogFragment.TAG) == null) {
            rateDialogFragment.show(activity.getSupportFragmentManager(), RateDialogFragment.TAG);
        }
    }

    private static void showDialog(AppCompatActivity activity){
        RateDialogFragment rateDialogFragment = RateDialogFragment.newInstance();
        if (activity.getSupportFragmentManager().findFragmentByTag(RateDialogFragment.TAG) == null) {
            rateDialogFragment.show(activity.getSupportFragmentManager(), RateDialogFragment.TAG);
        }
    }

    private static void calculateNextShow(Context context){
        float multiplier = PreferencesHelper.getShowMultiplier(context);
        int currentState = PreferencesHelper.getIncrement(context);
        int interval = PreferencesHelper.getCalculatedInterval(context);

        interval = (int) (interval * multiplier);
        int nextTimeOpen = currentState + interval;

        PreferencesHelper.saveCalculatedInterval(context, interval);
        PreferencesHelper.saveNextTimeOpen(context, nextTimeOpen);
    }

    private static void logEverything(Context context){
        float multiplier = PreferencesHelper.getShowMultiplier(context);
        int currentState = PreferencesHelper.getIncrement(context);
        int interval = PreferencesHelper.getShowInterval(context);
        int calculatedInterval = PreferencesHelper.getCalculatedInterval(context);
        boolean neverShowAgain = PreferencesHelper.getNeverShowAgain(context);
        int firstShow = PreferencesHelper.getFirstShow(context);
        int nextTimeOpen = PreferencesHelper.getNextTimeOpen(context);

        Log.d(TAG, "firstShow = " + firstShow);
        Log.d(TAG, "currentState = " + currentState);
        Log.d(TAG, "interval = " + interval);
        Log.d(TAG, "calculatedInterval = " + calculatedInterval);
        Log.d(TAG, "nextTimeOpen = " + nextTimeOpen);
        Log.d(TAG, "multiplier = " + multiplier);
        Log.d(TAG, "neverShowAgain = " + neverShowAgain);
    }
}