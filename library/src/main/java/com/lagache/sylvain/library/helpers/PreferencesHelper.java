package com.lagache.sylvain.library.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.lagache.sylvain.library.utils.Constants;

/**
 * Created by user on 28/01/2016.
 */
public class PreferencesHelper {

    private static final String PACKAGE_NAME = "com.lagache.sylvain.library.ratedialog";

    private PreferencesHelper() {
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getPreferencesEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void saveFirstShow(Context context, int firstShow){
        getPreferencesEditor(context).putInt(Constants.PREF_FIRST_SHOW, firstShow).apply();
    }

    public static void saveShowInterval(Context context, int showInterval){
        getPreferencesEditor(context).putInt(Constants.PREF_SHOW_INTERVAL, showInterval).apply();
    }

    public static void saveCalculatedInterval(Context context, int calculatedInterval){
        getPreferencesEditor(context).putInt(Constants.PREF_CALCULATED_INTERVAL, calculatedInterval).apply();
    }

    public static void saveShowMultiplier(Context context, float showMultiplier){
        getPreferencesEditor(context).putFloat(Constants.PREF_SHOW_MULTIPLIER, showMultiplier).apply();
    }

    public static void saveIncrement(Context context, int increment){
        getPreferencesEditor(context).putInt(Constants.PREF_INCREMENT, increment).apply();
    }

    public static void saveNextTimeOpen(Context context, int nextTimeOpen){
        getPreferencesEditor(context).putInt(Constants.PREF_NEXT_TIME_OPEN, nextTimeOpen).apply();
    }

    public static void saveNeverShowAgain(Context context, boolean neverShowAgin){
        getPreferencesEditor(context).putBoolean(Constants.PREF_NEVER_SHOW_AGAIN, neverShowAgin).apply();
    }

    public static int getFirstShow(Context context){
        return getPreferences(context).getInt(Constants.PREF_FIRST_SHOW, 3);
    }

    public static int getShowInterval(Context context){
        return getPreferences(context).getInt(Constants.PREF_SHOW_INTERVAL, 3);
    }

    public static int getCalculatedInterval(Context context){
        return getPreferences(context).getInt(Constants.PREF_CALCULATED_INTERVAL, -1);
    }

    public static float getShowMultiplier(Context context){
        return getPreferences(context).getFloat(Constants.PREF_SHOW_MULTIPLIER, 1);
    }

    public static int getIncrement(Context context){
        return getPreferences(context).getInt(Constants.PREF_INCREMENT, 1);
    }

    public static int getNextTimeOpen(Context context){
        return getPreferences(context).getInt(Constants.PREF_NEXT_TIME_OPEN, -1);
    }

    public static boolean getNeverShowAgain(Context context){
        return getPreferences(context).getBoolean(Constants.PREF_NEVER_SHOW_AGAIN, false);
    }

}
