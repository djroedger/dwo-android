package com.akadasoftware.danceworksonline.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.R;

import java.text.NumberFormat;
// ...

public class EditAmountDialog extends DialogFragment implements TextView.OnEditorActionListener {

    TextView tvAmount;
    EditText etAmount;

    public EditAmountDialog() {
        // Empty constructor required for DialogFragment
    }

    //Uses the interface so that we can communicate from fragment to parent activity
    public interface EditAmountDialogListener {
        void onFinishEditAmountDialog(String input);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.editamountdialog, container);
        tvAmount = (TextView) view.findViewById(R.id.tvAmount);
        etAmount = (EditText) view.findViewById(R.id.etAmount);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        /**
         * Automatically displays the keyboard when the dialog pops up
         */
        etAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        String strInput = getArguments().getString("Input");
        //etAmount.setText(String.valueOf(format.format(strInput)));
        etAmount.setText(strInput);
        getDialog().setTitle("Enter Amount");
        etAmount.setOnEditorActionListener(this);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditAmountDialogListener activity = (EditAmountDialogListener) getActivity();
            if (etAmount.getText().toString().trim().length() > 0) {
                activity.onFinishEditAmountDialog(etAmount.getText().toString());
                this.dismiss();
            } else {
                activity.onFinishEditAmountDialog("$0.00");
                this.dismiss();
            }
            return true;
        }

        return false;
    }
}