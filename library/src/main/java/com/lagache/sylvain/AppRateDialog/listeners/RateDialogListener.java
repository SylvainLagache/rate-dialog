package com.lagache.sylvain.AppRateDialog.listeners;

/**
 * Listener used to get user interaction of the dialog.
 */
public interface RateDialogListener {

    /**
     * Play store rate requested.
     */
    void onPlayStorePressed();

    /**
     * Suggestion mail requested.
     */
    void onSuggestionPressed();

    /**
     * Cancel requested.
     */
    void onCancelPressed(boolean neverShowAgain);
}
