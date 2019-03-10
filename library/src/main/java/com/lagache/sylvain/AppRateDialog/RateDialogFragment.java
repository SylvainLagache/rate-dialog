package com.lagache.sylvain.AppRateDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lagache.sylvain.AppRateDialog.drawable.RaitingStartDrawables;
import com.lagache.sylvain.AppRateDialog.utils.Constants;

/**
 * Created by user on 28/01/2016.
 */
public class RateDialogFragment extends DialogFragment implements RatingBar.OnRatingBarChangeListener {

    public static final String TAG = RateDialogFragment.class.getSimpleName();

    private String title;
    private String message;
    private String cancelMessage;
    private String neverShowAgainMessage;

    private TextView messageTextView;
    private TextView rateDescriptionTextView;

    private LinearLayout buttonsLayout;

    private Button cancelButton;

    private CheckBox neverShowAgainCheckBox;

    public RateDialogFragment() {
        //Nothing to do
    }

    public static RateDialogFragment newInstance() {
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle args = new Bundle();
        rateDialogFragment.setArguments(args);
        return rateDialogFragment;
    }

    public static RateDialogFragment newInstance(String title,
                                                 String message,
                                                 String cancelMessage,
                                                 String neverShowAgainMessage) {
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_TITLE, title);
        args.putString(Constants.ARG_MESSAGE, message);
        args.putString(Constants.ARG_CANCEL_TEXT, cancelMessage);
        args.putString(Constants.ARG_NEVER_SHOW_AGAIN_TEXT, neverShowAgainMessage);
        rateDialogFragment.setArguments(args);
        return rateDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            initWithArguments(getArguments());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.rate_dialog_fragment_rate_dialog, container);
        initView(contentView);
        setTexts();
        return contentView;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        AppRate.saveNeverShowAgain(neverShowAgainCheckBox.isChecked());
        super.onCancel(dialog);
    }

    private void initWithArguments(Bundle bundle) {
        title = bundle.getString(Constants.ARG_TITLE);
        message = bundle.getString(Constants.ARG_MESSAGE);
        cancelMessage = bundle.getString(Constants.ARG_CANCEL_TEXT);
        neverShowAgainMessage = bundle.getString(Constants.ARG_NEVER_SHOW_AGAIN_TEXT);
    }

    private void initView(View contentView) {
        messageTextView = (TextView) contentView.findViewById(R.id.rate_message_textview);
        rateDescriptionTextView = (TextView) contentView.findViewById(R.id.rate_description_textview);

        RatingBar ratingBar = (RatingBar) contentView.findViewById(R.id.rate_ratingbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ratingBar.setProgressDrawableTiled(RaitingStartDrawables.getRatingStar(getContext()));
        }
        ratingBar.setOnRatingBarChangeListener(this);

        buttonsLayout = (LinearLayout) contentView.findViewById(R.id.buttons_layout);
        cancelButton = (Button) contentView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppRate.saveNeverShowAgain(neverShowAgainCheckBox.isChecked());
                dismiss();
            }
        });

        neverShowAgainCheckBox = (CheckBox) contentView.findViewById(R.id.never_show_again_checkbox);
    }

    private void setTexts() {
        if (title != null) {
            getDialog().setTitle(title);
        } else {
            getDialog().setTitle(getString(R.string.rate_dialog_title));
        }

        if (message != null) {
            messageTextView.setText(message);
        }
        if (cancelMessage != null) {
            cancelButton.setText(cancelMessage);
        }
        if (neverShowAgainMessage != null) {
            neverShowAgainCheckBox.setText(neverShowAgainMessage);
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        int ratingInt = (int) rating;
        rateDescriptionTextView.setVisibility(View.VISIBLE);
        switch (ratingInt) {
            case 0:
                rateDescriptionTextView.setVisibility(View.GONE);
                hideButtons();
                break;
            case 1:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_1_star_description));
                showBadRateButtons();
                break;
            case 2:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_2_star_description));
                showBadRateButtons();
                break;
            case 3:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_3_star_description));
                showBadRateButtons();
                break;
            case 4:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_4_star_description));
                showGoogRateButtons();
                break;
            case 5:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_5_star_description));
                showGoogRateButtons();
                break;
        }
    }

    private void showBadRateButtons() {
        buttonsLayout.removeAllViews();
        for (final Pair<String, Intent> pair : AppRate.getBadRateIntents()) {
            addButton(pair.first, pair.second);
        }
    }

    private void showGoogRateButtons() {
        buttonsLayout.removeAllViews();
        for (final Pair<String, Intent> pair : AppRate.getGoodRateIntents()) {
            addButton(pair.first, pair.second);
        }
    }

    private void hideButtons() {
        buttonsLayout.removeAllViews();
    }

    private void addButton(String buttonText, final Intent buttonIntent) {
        Button button = new Button(new ContextThemeWrapper(getContext(), R.style.Rate_Dialog_RateButton), null, 0);
        button.setText(buttonText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppRate.saveNeverShowAgain(true);
                startActivity(buttonIntent);
                dismiss();
            }
        });
        buttonsLayout.addView(button);
    }
}
