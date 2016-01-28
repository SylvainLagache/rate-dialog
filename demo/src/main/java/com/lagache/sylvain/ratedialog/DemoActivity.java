package com.lagache.sylvain.ratedialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lagache.sylvain.library.AppRate;
import com.lagache.sylvain.library.fragments.RateDialogFragment;
import com.lagache.sylvain.library.listeners.RateDialogListener;

public class DemoActivity extends AppCompatActivity implements RateDialogListener {

    RateDialogFragment rateDialogFragment;

    Button resetDialogButton;

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

        AppRate.showDialogIfNeeded(this);
    }

    private void resetRateDialogValues(){
        AppRate.resetValues(this);
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
