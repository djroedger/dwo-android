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
import android.widget.Spinner;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.StudentAttendance;
import com.akadasoftware.danceworksonline.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * Created by Kyle on 7/23/2014.
 */
public class AttendanceDialog extends DialogFragment {

    public AttendanceDialog() {
    }

    Spinner spinnerAttendance;
    AppPreferences _appPrefs;
    StudentAttendance oStudentAttendance;

    public interface AttendanceDialogListener {
        public void onAttendanceDialogPositiveClick(String mTitle);

    }

    AttendanceDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AttendanceDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement AttendanceDialogListener");
        }
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.attendance_dialog, null);


        spinnerAttendance = (Spinner) view.findViewById(R.id.spinnerAttendance);
        _appPrefs = new AppPreferences(getActivity());
        ArrayAdapter<CharSequence> attendanceAdapter = null;

        attendanceAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.AttendanceStatus, android.R.layout.simple_spinner_item);

        spinnerAttendance.setAdapter(attendanceAdapter);

        String strJsonAttendance = getActivity().getIntent().getStringExtra("StudentAttendance");

        if (strJsonAttendance != "None found") {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(strJsonAttendance).getAsJsonArray();
            oStudentAttendance = gson.fromJson(array.get(0), StudentAttendance.class);
        }

        TextView tvClassLevel = (TextView)
                view.findViewById(R.id.tvClassLevel);

        TextView tvDescription = (TextView)
                view.findViewById(R.id.tvDescription);

        TextView tvClassType = (TextView)
                view.findViewById(R.id.tvClassType);

        TextView tvDate = (TextView)
                view.findViewById(R.id.tvDate);

        tvClassType.setText(oStudentAttendance.ClType);
        tvClassLevel.setText(oStudentAttendance.ClLevel);

        tvDescription.setText(oStudentAttendance.ClDescription);
        tvDate.setText(oStudentAttendance.ADate);


        builder.setView(view)
                // Title of dialog
                .setTitle("Change Filter Settings")

                        // Add action buttons
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }


                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AttendanceDialog.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }

}
