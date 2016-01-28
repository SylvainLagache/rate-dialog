package com.lagache.sylvain.ratedialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.lagache.sylvain.library.fragments.RateDialogFragment;

public class DemoActivity extends AppCompatActivity {

    RateDialogFragment rateDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        showRateDialogFrament();
    }

    private void showRateDialogFrament(){
        rateDialogFragment = RateDialogFragment.newInstance();
        rateDialogFragment.show(getSupportFragmentManager(), RateDialogFragment.TAG);
    }
}
