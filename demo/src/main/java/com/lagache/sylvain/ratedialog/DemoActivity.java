package com.lagache.sylvain.ratedialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lagache.sylvain.AppRateDialog.AppRate;
import com.lagache.sylvain.AppRateDialog.listeners.RateDialogListener;

public class DemoActivity extends AppCompatActivity implements RateDialogListener {

    private static final String TAG = "DemoActivity";

    private Button resetDialogButton;

    private static final String ARG_ALREADY_STARTED = "arg_already_started";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        resetDialogButton = (Button) findViewById(R.id.reset_dialog_button);
        resetDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRateDialogValues();
            }
        });

        AppRate.with(this)
                .setFirstShow(1)
                .setShowInterval(1)
                .setIntervalMultiplier(1.0F)
                .setAppPackage("com.nf28.aotc")
                .setEmailAddress("one.thumb.control@gmail.com")
                .setEmailObject("Suggestion");

        Log.d(TAG, "savedInstanceState : " + savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.getBoolean(ARG_ALREADY_STARTED)) {
            AppRate.showDialogIfNeeded(this);
        }
    }

    private void resetRateDialogValues(){
        AppRate.resetValues(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ARG_ALREADY_STARTED , true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPlayStorePressed() {

    }

    @Override
    public void onSuggestionPressed() {

    }

    @Override
    public void onCancelPressed(boolean neverShowAgain) {

    }
}
