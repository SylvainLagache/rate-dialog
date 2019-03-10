package com.lagache.sylvain.AppRateDialog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;

import com.lagache.sylvain.AppRateDialog.helpers.IntentHelper;
import com.lagache.sylvain.AppRateDialog.helpers.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * AppRate main class
 */
public class AppRate {

    private static final String TAG = AppRate.class.getSimpleName();

    private static AppRate singleton;

    private final Context context;

    private String appPackage;

    private List<Pair<String, Intent>> goodRateIntents;

    private List<Pair<String, Intent>> badRateIntents;

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

        singleton.badRateIntents = new ArrayList<>();
        singleton.goodRateIntents = new ArrayList<>();
        return singleton;
    }

    public AppRate setFirstShow(int firstShow) {
        PreferencesHelper.saveFirstShow(context, firstShow);
        if (PreferencesHelper.getNextTimeOpen(context) == -1) {
            PreferencesHelper.saveNextTimeOpen(context, firstShow);
        }
        return this;
    }

    public AppRate setShowInterval(int showInterval) {
        int currentInterval = PreferencesHelper.getShowInterval(context);
        int calculatedInterval = PreferencesHelper.getCalculatedInterval(context);
        if ((currentInterval != showInterval) || (calculatedInterval == -1)) {
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
        goodRateIntents.add(
                new Pair<String, Intent>(context.getString(R.string.rate_dialog_playstore_button),
                        IntentHelper.getPlayStoreIntent(appPackage))
        );
        return this;
    }

    public AppRate addEmailButton(String emailAddress, String emailObject) {
        badRateIntents.add(new Pair<String, Intent>(
                context.getString(R.string.rate_dialog_suggestion_button),
                IntentHelper.getEmailIntent(emailAddress, emailObject))
        );
        return this;
    }

    public AppRate addGoodRateButton(String buttonText, Intent buttonIntent) {
        goodRateIntents.add(new Pair<String, Intent>(buttonText, buttonIntent));
        return this;
    }

    public AppRate addBadRateButton(String buttonText, Intent buttonIntent) {
        badRateIntents.add(new Pair<String, Intent>(buttonText, buttonIntent));
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// OTHER ////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean shouldShowDialog(Context context) {
        boolean neverShowAgain = PreferencesHelper.getNeverShowAgain(context);
        if (!neverShowAgain) {
            int currentState = PreferencesHelper.getIncrement(context);
            int nextTimeOpen = PreferencesHelper.getNextTimeOpen(context);
            return (currentState >= nextTimeOpen);
        }
        return false;
    }

    private static void incrementCurrentState(Context context) {
        int currentState = PreferencesHelper.getIncrement(context);
        PreferencesHelper.saveIncrement(context, currentState + 1);
    }

    public static void showDialogIfNeeded(AppCompatActivity activity,
                                          String title,
                                          String message,
                                          String cancelMessage,
                                          String neverShowAgainMessage) {

        if (shouldShowDialog(activity)) {
            showDialog(activity, title, message, cancelMessage, neverShowAgainMessage);
            calculateNextShow(activity);
        }
        incrementCurrentState(activity);
    }

    public static void showDialogIfNeeded(AppCompatActivity activity) {
        logEverything(activity);
        if (shouldShowDialog(activity)) {
            showDialog(activity);
            calculateNextShow(activity);
        }
        incrementCurrentState(activity);
    }

    private static void showDialog(AppCompatActivity activity,
                                   String title,
                                   String message,
                                   String cancelMessage,
                                   String neverShowAgainMessage) {
        RateDialogFragment rateDialogFragment = RateDialogFragment.newInstance(title, message,
                cancelMessage, neverShowAgainMessage);
        if (activity.getSupportFragmentManager().findFragmentByTag(RateDialogFragment.TAG) == null) {
            rateDialogFragment.show(activity.getSupportFragmentManager(), RateDialogFragment.TAG);
        }
    }

    private static void showDialog(AppCompatActivity activity) {
        RateDialogFragment rateDialogFragment = RateDialogFragment.newInstance();
        if (activity.getSupportFragmentManager().findFragmentByTag(RateDialogFragment.TAG) == null) {
            rateDialogFragment.show(activity.getSupportFragmentManager(), RateDialogFragment.TAG);
        }
    }

    private static void calculateNextShow(Context context) {
        float multiplier = PreferencesHelper.getShowMultiplier(context);
        int currentState = PreferencesHelper.getIncrement(context);
        int interval = PreferencesHelper.getCalculatedInterval(context);

        interval = (int) (interval * multiplier);
        int nextTimeOpen = currentState + interval;

        PreferencesHelper.saveCalculatedInterval(context, interval);
        PreferencesHelper.saveNextTimeOpen(context, nextTimeOpen);
    }

    public static void resetValues(Context context) {
        int interval = PreferencesHelper.getShowInterval(context);
        int firstShow = PreferencesHelper.getFirstShow(context);
        PreferencesHelper.saveCalculatedInterval(context, interval);
        PreferencesHelper.saveNextTimeOpen(context, firstShow);
        PreferencesHelper.saveIncrement(context, 1);
        PreferencesHelper.saveNeverShowAgain(context, false);
    }

    private static void logEverything(Context context) {
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

    static void saveNeverShowAgain(boolean neverShowAgain) {
        PreferencesHelper.saveNeverShowAgain(singleton.context, neverShowAgain);
    }

    static List<Pair<String, Intent>> getGoodRateIntents() {
        return singleton.goodRateIntents;
    }

    static List<Pair<String, Intent>> getBadRateIntents() {
        return singleton.badRateIntents;
    }
}
