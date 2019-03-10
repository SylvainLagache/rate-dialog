package com.lagache.sylvain.ratedialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lagache.sylvain.AppRateDialog.AppRate;

public class DemoActivity extends AppCompatActivity {

    private static final String ARG_ALREADY_STARTED = "arg_already_started";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Button resetDialogButton = (Button) findViewById(R.id.reset_dialog_button);
        resetDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRateDialogValues();
            }
        });

        AppRate.with(this)
                .setFirstShow(1) //First time showed
                .setShowInterval(1) //Show interval
                .setIntervalMultiplier(1.0F) //Show interval multiplier
                .setAppPackage("com.your.package") //App package
                .addEmailButton("your.email@gmail.com", "This is the object")
                .addBadRateButton("FAQ",
                        new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.lagache.sylvain.xhomebar"))
                ); //Your FAQ intent

        if (savedInstanceState == null || !savedInstanceState.getBoolean(ARG_ALREADY_STARTED)) {
            AppRate.showDialogIfNeeded(this);
            //or
            AppRate.showDialogIfNeeded(this,
                    "Rate this app", //Dialog title
                    "How much did you like this app ?", //Dialog message
                    "Cancel", //Cancel button text
                    "Never show again"); //Never show again checkbox text
        }
    }

    private void resetRateDialogValues() {
        AppRate.resetValues(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ARG_ALREADY_STARTED, true);
        super.onSaveInstanceState(outState);
    }
}
