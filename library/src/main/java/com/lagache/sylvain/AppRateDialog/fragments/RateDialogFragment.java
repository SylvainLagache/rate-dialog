package com.lagache.sylvain.AppRateDialog.fragments;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lagache.sylvain.AppRateDialog.AppRate;
import com.lagache.sylvain.AppRateDialog.R;
import com.lagache.sylvain.AppRateDialog.drawable.RaitingStartDrawables;
import com.lagache.sylvain.AppRateDialog.utils.Constants;

/**
 * Created by user on 28/01/2016.
 */
public class RateDialogFragment extends DialogFragment implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    public static final String TAG = "RateDialogFragment";

    private boolean showFAQ;
    private String title;
    private String message;
    private String playStoreMessage;
    private String suggestionMessage;
    private String cancelMessage;
    private String neverShowAgainMessage;

    private TextView messageTextView;
    private TextView rateDescriptionTextView;

    private RatingBar ratingBar;

    private Button playStoreButton;
    private Button suggestionButton;
    private Button cancelButton;
    private Button faqButton;

    private CheckBox neverShowAgainCheckBox;

    public RateDialogFragment(){
        //Nothing to do
    }

    public static RateDialogFragment newInstance(){
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle args = new Bundle();
        rateDialogFragment.setArguments(args);
        return rateDialogFragment;
    }

    public static RateDialogFragment newInstance(boolean showFAQ){
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.ARG_SHOW_FAQ, showFAQ);
        rateDialogFragment.setArguments(args);
        return rateDialogFragment;
    }

    public static RateDialogFragment newInstance(String title,
                                                 String message,
                                                 String playstoreMessage,
                                                 String suggestionMessage,
                                                 String cancelMessage,
                                                 String neverShowAgainMessage,
                                                 boolean showFAQ){
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_TITLE, title);
        args.putString(Constants.ARG_MESSAGE, message);
        args.putString(Constants.ARG_PLAYSTORE_TEXT, playstoreMessage);
        args.putString(Constants.ARG_SUGGESTION_TEXT, suggestionMessage);
        args.putString(Constants.ARG_CANCEL_TEXT, cancelMessage);
        args.putString(Constants.ARG_NEVER_SHOW_AGAIN_TEXT, neverShowAgainMessage);
        args.putBoolean(Constants.ARG_SHOW_FAQ, showFAQ);
        rateDialogFragment.setArguments(args);
        return rateDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            initWithArguments(getArguments());
        }
    }

    private void initWithArguments(Bundle bundle){
        title = bundle.getString(Constants.ARG_TITLE);
        message = bundle.getString(Constants.ARG_MESSAGE);
        playStoreMessage = bundle.getString(Constants.ARG_PLAYSTORE_TEXT);
        suggestionMessage = bundle.getString(Constants.ARG_SUGGESTION_TEXT);
        cancelMessage = bundle.getString(Constants.ARG_CANCEL_TEXT);
        neverShowAgainMessage = bundle.getString(Constants.ARG_NEVER_SHOW_AGAIN_TEXT);
        showFAQ = bundle.getBoolean(Constants.ARG_SHOW_FAQ);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.rate_dialog_fragment_rate_dialog, container);
        initView(contentView);
        setTexts();
        return contentView;
    }

    private void initView(View contentView){
        messageTextView = (TextView) contentView.findViewById(R.id.rate_message_textview);
        rateDescriptionTextView = (TextView) contentView.findViewById(R.id.rate_description_textview);

        ratingBar = (RatingBar) contentView.findViewById(R.id.rate_ratingbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ratingBar.setProgressDrawableTiled(RaitingStartDrawables.getRatingStar(getContext()));
        }
        ratingBar.setOnRatingBarChangeListener(this);

        playStoreButton = (Button) contentView.findViewById(R.id.playstore_button);
        suggestionButton = (Button) contentView.findViewById(R.id.suggestion_button);
        cancelButton = (Button) contentView.findViewById(R.id.cancel_button);
        faqButton = (Button) contentView.findViewById(R.id.faq_button);

        playStoreButton.setOnClickListener(this);
        suggestionButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        faqButton.setOnClickListener(this);

        neverShowAgainCheckBox = (CheckBox) contentView.findViewById(R.id.never_show_again_checkbox);
    }

    private void setTexts(){
        if (title != null){
            getDialog().setTitle(title);
        }else{
            getDialog().setTitle(getString(R.string.rate_dialog_title));
        }

        if (message != null){
            messageTextView.setText(message);
        }
        if (playStoreMessage != null){
            playStoreButton.setText(playStoreMessage);
        }
        if (suggestionMessage != null){
            suggestionButton.setText(suggestionMessage);
        }
        if (cancelMessage != null){
            cancelButton.setText(cancelMessage);
        }
        if (neverShowAgainMessage != null){
            neverShowAgainCheckBox.setText(neverShowAgainMessage);
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        int ratingInt = (int) rating;
        rateDescriptionTextView.setVisibility(View.VISIBLE);
        switch (ratingInt){
            case 0:
                rateDescriptionTextView.setVisibility(View.GONE);
                hideButtons();
                break;
            case 1:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_1_star_description));
                showSuggestionButton();
                break;
            case 2:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_2_star_description));
                showSuggestionButton();
                break;
            case 3:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_3_star_description));
                showSuggestionButton();
                break;
            case 4:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_4_star_description));
                showPlayStoreButton();
                break;
            case 5:
                rateDescriptionTextView.setText(getString(R.string.rate_dialog_5_star_description));
                showPlayStoreButton();
                break;
        }
    }

    private void showSuggestionButton(){
        playStoreButton.setVisibility(View.GONE);
        suggestionButton.setVisibility(View.VISIBLE);
        if (showFAQ) {
            faqButton.setVisibility(View.VISIBLE);
        }
    }

    private void showPlayStoreButton(){
        suggestionButton.setVisibility(View.GONE);
        faqButton.setVisibility(View.GONE);
        playStoreButton.setVisibility(View.VISIBLE);
    }

    private void hideButtons(){
        suggestionButton.setVisibility(View.GONE);
        playStoreButton.setVisibility(View.GONE);
        faqButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == playStoreButton){
            AppRate.goToPlayStore();
            AppRate.saveNeverShowAgain(true);
            dismiss();
        }else if (v == suggestionButton){
            AppRate.sendSuggestion();
            AppRate.saveNeverShowAgain(true);
            dismiss();
        }else if (v == cancelButton){
            AppRate.saveNeverShowAgain(neverShowAgainCheckBox.isChecked());
            dismiss();
        }else if (v == faqButton){
            AppRate.startFAQ();
            dismiss();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        AppRate.saveNeverShowAgain(neverShowAgainCheckBox.isChecked());
        super.onCancel(dialog);
    }
}
