package com.lagache.sylvain.library.fragments;

import android.app.Activity;
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

import com.lagache.sylvain.library.R;
import com.lagache.sylvain.library.listeners.RateDialogListener;
import com.lagache.sylvain.library.utils.Constants;

/**
 * Created by user on 28/01/2016.
 */
public class RateDialogFragment extends DialogFragment implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    public static final String TAG = "RateDialogFragment";

    private String playstorePackage;
    private String suggestionEmailAddress;
    private String emailObject;

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

    private CheckBox neverShowAgainCheckBox;

    private RateDialogListener rateDialogListener;

    public RateDialogFragment(){

    }

    public static RateDialogFragment newInstance(String playstorePackage,
                                                 String suggestionEmailAddress,
                                                 String emailObject){
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PLAYSTORE_PACKAGE, playstorePackage);
        args.putString(Constants.ARG_SUGGESTION_EMAIL_ADDRESS, suggestionEmailAddress);
        args.putString(Constants.ARG_EMAIL_OBJECT, emailObject);
        rateDialogFragment.setArguments(args);
        return rateDialogFragment;
    }

    public static RateDialogFragment newInstance(String playstorePackage,
                                                 String suggestionEmailAddress,
                                                 String emailObject,
                                                 String title,
                                                 String message,
                                                 String playstoreMessage,
                                                 String suggestionMessage,
                                                 String cancelMessage,
                                                 String neverShowAgainMessage){
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PLAYSTORE_PACKAGE, playstorePackage);
        args.putString(Constants.ARG_SUGGESTION_EMAIL_ADDRESS, suggestionEmailAddress);
        args.putString(Constants.ARG_EMAIL_OBJECT, emailObject);
        args.putString(Constants.ARG_TITLE, title);
        args.putString(Constants.ARG_MESSAGE, message);
        args.putString(Constants.ARG_PLAYSTORE_TEXT, playstoreMessage);
        args.putString(Constants.ARG_SUGGESTION_TEXT, suggestionMessage);
        args.putString(Constants.ARG_CANCEL_TEXT, cancelMessage);
        args.putString(Constants.ARG_NEVER_SHOW_AGAIN_TEXT, neverShowAgainMessage);
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        rateDialogListener = (RateDialogListener) activity;
    }

    private void initWithArguments(Bundle bundle){
        playstorePackage = bundle.getString(Constants.ARG_PLAYSTORE_PACKAGE);
        suggestionEmailAddress = bundle.getString(Constants.ARG_SUGGESTION_EMAIL_ADDRESS);
        emailObject = bundle.getString(Constants.ARG_EMAIL_OBJECT);

        title = bundle.getString(Constants.ARG_TITLE);
        message = bundle.getString(Constants.ARG_MESSAGE);
        playStoreMessage = bundle.getString(Constants.ARG_PLAYSTORE_TEXT);
        suggestionMessage = bundle.getString(Constants.ARG_SUGGESTION_TEXT);
        cancelMessage = bundle.getString(Constants.ARG_CANCEL_TEXT);
        neverShowAgainMessage = bundle.getString(Constants.ARG_NEVER_SHOW_AGAIN_TEXT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.dialog_fragment_rate_dialog, container);
        initView(contentView);
        setTexts();
        return contentView;
    }

    private void initView(View contentView){
        messageTextView = (TextView) contentView.findViewById(R.id.rate_message_textview);
        rateDescriptionTextView = (TextView) contentView.findViewById(R.id.rate_description_textview);

        ratingBar = (RatingBar) contentView.findViewById(R.id.rate_ratingbar);
        ratingBar.setOnRatingBarChangeListener(this);

        playStoreButton = (Button) contentView.findViewById(R.id.playstore_button);
        suggestionButton = (Button) contentView.findViewById(R.id.suggestion_button);
        cancelButton = (Button) contentView.findViewById(R.id.cancel_button);
        playStoreButton.setOnClickListener(this);
        suggestionButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

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
        switch (ratingInt){
            case 0:
                rateDescriptionTextView.setText("");
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
        suggestionButton.setVisibility(View.VISIBLE);
        playStoreButton.setVisibility(View.GONE);
    }

    private void showPlayStoreButton(){
        suggestionButton.setVisibility(View.GONE);
        playStoreButton.setVisibility(View.VISIBLE);
    }

    private void hideButtons(){
        suggestionButton.setVisibility(View.GONE);
        playStoreButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == playStoreButton){
            rateDialogListener.onPlayStorePressed();
        }else if (v == suggestionButton){
            rateDialogListener.onSuggestionPressed();
        }else if (v == cancelButton){
            rateDialogListener.onCancelPressed(neverShowAgainCheckBox.isChecked());
        }
    }
}
