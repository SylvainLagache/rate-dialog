package com.lagache.sylvain.AppRateDialog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lagache.sylvain.AppRateDialog.fragments.RateDialogFragment;
import com.lagache.sylvain.AppRateDialog.helpers.IntentHelper;
import com.lagache.sylvain.AppRateDialog.helpers.PreferencesHelper;

/**
 * AppRate main class
 */
public class AppRate {

    public static final String TAG = "AppRate";

    private static AppRate singleton;

    private final Context context;

    private String appPackage;

    private String emailAddress;

    private String emailObject;

    private Intent faqIntent;

    private AppRate(Context context) {
        this.context = context.getApplicationContext();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// BUILD ////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

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

    public AppRate setFirstShow(int firstShow) {
        PreferencesHelper.saveFirstShow(context, firstShow);
        if (PreferencesHelper.getNextTimeOpen(context) == -1){
            PreferencesHelper.saveNextTimeOpen(context, firstShow);
        }
        return this;
    }

    public AppRate setShowInterval(int showInterval) {
        int currentInterval = PreferencesHelper.getShowInterval(context);
        int calculatedInterval = PreferencesHelper.getCalculatedInterval(context);
        if ((currentInterval != showInterval) || (calculatedInterval == -1)){
            PreferencesHelper.saveShowInterval(context, showInterval);
            PreferencesHelper.saveCalculatedInterval(context, showInterval);
        }
        return this;
    }

    public AppRate setIntervalMultiplier(float intervalMultiplier) {
        PreferencesHelper.saveShowMultiplier(context, intervalMultiplier);
        return this;
    }

    public AppRate setAppPackage(String appPackage) {
        this.appPackage = appPackage;
        return this;
    }

    public AppRate setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public AppRate setEmailObject(String emailObject) {
        this.emailObject = emailObject;
        return this;
    }

    public AppRate setFAQIntent(Intent faqIntent){
        this.faqIntent = faqIntent;
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// OTHER ////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

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
                playstoreMessage, suggestionMessage, cancelMessage, neverShowAgainMessage,
                singleton.faqIntent != null);
        if (activity.getSupportFragmentManager().findFragmentByTag(RateDialogFragment.TAG) == null) {
            rateDialogFragment.show(activity.getSupportFragmentManager(), RateDialogFragment.TAG);
        }
    }

    private static void showDialog(AppCompatActivity activity){
        RateDialogFragment rateDialogFragment = RateDialogFragment.newInstance(singleton.faqIntent != null);
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

    public static void resetValues(Context context){
        int interval = PreferencesHelper.getShowInterval(context);
        int firstShow = PreferencesHelper.getFirstShow(context);
        PreferencesHelper.saveCalculatedInterval(context, interval);
        PreferencesHelper.saveNextTimeOpen(context, firstShow);
        PreferencesHelper.saveIncrement(context, 1);
        PreferencesHelper.saveNeverShowAgain(context, false);
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

    public static void sendSuggestion(){
        IntentHelper.openEmailIntent(singleton.context, singleton.emailAddress, singleton.emailObject);
    }

    public static void goToPlayStore(){
        IntentHelper.openPlayStoreIntent(singleton.context, singleton.appPackage);
    }

    public static void saveNeverShowAgain(boolean neverShowAgain){
        PreferencesHelper.saveNeverShowAgain(singleton.context, neverShowAgain);
    }

    public static void startFAQ(){
        singleton.context.startActivity(singleton.faqIntent);
    }
}
