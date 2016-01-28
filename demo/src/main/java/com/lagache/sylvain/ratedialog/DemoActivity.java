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

    Button showDialogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        showDialogButton = (Button) findViewById(R.id.show_dialog_button);
        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRateDialogFrament();
            }
        });

        AppRate.with(this)
                .setFirstShow(1)
                .setShowInterval(1)
                .setIntervalMultiplier(1.0F);

        AppRate.showDialogIfNeeded(this);
    }

    private void showRateDialogFrament(){
//        rateDialogFragment = RateDialogFragment.newInstance();
//        rateDialogFragment.show(getSupportFragmentManager(), RateDialogFragment.TAG);
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
