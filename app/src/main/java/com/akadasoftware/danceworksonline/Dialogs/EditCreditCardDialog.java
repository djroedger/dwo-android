package com.akadasoftware.danceworksonline.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.akadasoftware.danceworksonline.Classes.Account;
import com.akadasoftware.danceworksonline.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kyle on 3/24/2014.
 */
public class EditCreditCardDialog extends DialogFragment {

    EditText etCCNo, etCVV;
    String strCCNo, strDate, strCVV;
    Boolean boolSaveCard;
    Spinner spinnerMonth, spinnerYear;
    CheckBox chkSaveCreditCard;
    Activity activity;
    Account account;

    ArrayList<String> years = new ArrayList<String>();
    String[] months;

    public EditCreditCardDialog() {
    }

    public interface EditCreditCardDialogListener {
        public void onDialogPositiveClick(String CCNo, String CVV, String Date, Boolean saveCard);

        public void onDialogNegativeClick();
    }

    EditCreditCardDialogListener ccListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            ccListener = (EditCreditCardDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement EditCreditCardDialogListener");
        }
    }


    /**
     * @param savedInstanceState
     * @return Dialog with new CVV, CC#, and CC Exp values
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.editcreditcarddialog, null);

        etCCNo = (EditText) view.findViewById(R.id.etCCNo);
        etCVV = (EditText) view.findViewById(R.id.etCVV);
        spinnerMonth = (Spinner) view.findViewById(R.id.spinnerMonth);
        spinnerYear = (Spinner) view.findViewById(R.id.spinnerYear);
        chkSaveCreditCard = (CheckBox) view.findViewById(R.id.chkSaveCreditCard);
        chkSaveCreditCard.setChecked(false);
        activity = getActivity();


        boolSaveCard = false;


        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.months, android.R.layout.simple_spinner_item);

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int currMonth = Calendar.getInstance().get(Calendar.MONTH);
        for (int i = 1900; i <= thisYear + 10; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, years);

        spinnerMonth.setAdapter(monthAdapter);
        spinnerYear.setAdapter(yearAdapter);

        spinnerMonth.setSelection(currMonth);
        spinnerYear.setSelection(years.size() - 11);
        builder.setView(view)
                // Title of dialog
                .setTitle("Enter Credit Card Information")

                        // Add action buttons
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        strCCNo = etCCNo.getText().toString().trim();
                        strCVV = etCVV.getText().toString().trim();
                        strDate = spinnerMonth.getSelectedItem().toString() + spinnerYear.getSelectedItem().toString().substring(2);

                        //No blank fields
                        if (strCCNo.trim().length() == 0) {
                            Toast toast = Toast.makeText(activity, "No Credit Card entered", Toast.LENGTH_LONG);
                            toast.show();

                        } else {
                            //Amex, needs to be length 15
                            if (strCCNo.charAt(0) == '3') {
                                if (strCCNo.length() != 15) {
                                    Toast toast = Toast.makeText(activity, "Invalid Credit Card #", Toast.LENGTH_LONG);
                                    toast.show();
                                } else if (strCVV.length() != 3 || strCVV.length() != 0) {
                                    Toast toast = Toast.makeText(activity, "Invalid CVV #", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                //All other card lengths are 16
                            } else {
                                if (strCCNo.length() != 16) {
                                    Toast toast = Toast.makeText(activity, "Invalid Credit Card #", Toast.LENGTH_LONG);
                                    toast.show();
                                } else if (strCVV.length() != 4 || strCVV.length() != 0) {
                                    Toast toast = Toast.makeText(activity, "Invalid CVV #", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }

                            if (chkSaveCreditCard.isChecked()) {
                                boolSaveCard = true;
                            }
                            ccListener.onDialogPositiveClick(strCCNo, strCVV, strDate, boolSaveCard);
                        }
                    }
                })
                .setNegativeButton("Use Card on File", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditCreditCardDialog.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }

}
