package com.lagache.sylvain.AppRateDialog.listeners;

/**
 * Created by user on 28/01/2016.
 */
public interface RateDialogListener {

    void onPlayStorePressed();
    void onSuggestionPressed();
    void onCancelPressed(boolean neverShowAgain);

}
