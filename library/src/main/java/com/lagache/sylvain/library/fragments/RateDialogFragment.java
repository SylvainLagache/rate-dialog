package com.lagache.sylvain.library.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lagache.sylvain.library.R;

/**
 * Created by user on 28/01/2016.
 */
public class RateDialogFragment extends DialogFragment {

    public static final String TAG = "RateDialogFragment";

    public RateDialogFragment(){

    }

    public static RateDialogFragment newInstance(){
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle args = new Bundle();
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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.dialog_fragment_rate_dialog, container);
        initView(contentView);
        getDialog().setTitle(getString(R.string.rate_dialog_title));
        return contentView;
    }

    private void initView(View contentView){

    }
}
