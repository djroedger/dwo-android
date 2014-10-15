package com.akadasoftware.danceworksonline.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;


/**
 * Created by Kyle on 5/19/2014.
 */
public class EnrollDialog extends DialogFragment {

    AppPreferences _appPrefs;
    Activity activity;
    SchoolClasses objSchoolClasses;
    Student objStudent;
    View view;

    public EnrollDialog() {

    }

    public interface EnrollDialogListener {
        public void onEnrollDialogPositiveClick(SchoolClasses oSchoolClasses, Student oStudent, int position);

        public void onEnrollDialogNuetralClick(SchoolClasses oSchoolClasses, Student oStudent
                , int position);
    }


    EnrollDialogListener dialogListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            dialogListener = (EnrollDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement EnrollDialogListener");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismiss();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.enrolldialog, null);


        _appPrefs = new AppPreferences(getActivity());
        activity = getActivity();


        String strJsonSchoolClasses = activity.getIntent().getStringExtra("SchoolClasses");
        String strStudent = activity.getIntent().getStringExtra("Student");

        if (strJsonSchoolClasses != "None found") {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(strJsonSchoolClasses).getAsJsonArray();
            objSchoolClasses = gson.fromJson(array.get(0), SchoolClasses.class);
        }


        if (strStudent != "None found") {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(strStudent).getAsJsonArray();
            objStudent = gson.fromJson(array.get(0), Student.class);
        }

        Intent I = getActivity().getIntent();
        ArrayList<String> conflictsArray = I.getStringArrayListExtra("Conflicts");

        final int classPosition = getActivity().getIntent().getIntExtra("Position", 0);

        TextView tvConflicts = (TextView) view.findViewById(R.id.tvConflicks);

        TextView tvClassType = (TextView) view.findViewById(R.id.tvClassType);

        TextView tvClassLevel = (TextView)
                view.findViewById(R.id.tvClassLevel);

        TextView tvDescription = (TextView)
                view.findViewById(R.id.tvDescription);

        TextView tvInstructor = (TextView)
                view.findViewById(R.id.tvInstructor);

        TextView tvDay = (TextView)
                view.findViewById(R.id.tvDay);

        TextView tvStart = (TextView)
                view.findViewById(R.id.tvStart);

        TextView tvStop = (TextView)
                view.findViewById(R.id.tvStop);

        TextView tvRoom = (TextView)
                view.findViewById(R.id.tvRoom);


        int arrayCount = conflictsArray.size();

        String conflictMessage = "";

        if (arrayCount == 1)
            conflictMessage = "The following class conflict with the class below";
        else if (arrayCount > 1)
            conflictMessage = "The following Classes conflict with the class below";

        for (int i = 0; i < arrayCount; i++) {
            if (conflictsArray.get(i).toString().equals("anyType{}"))
                conflictMessage += "";
            else
                conflictMessage += "\n" + conflictsArray.get(i).toString();

        }
        tvConflicts.setText(conflictMessage);


        String Day = "";
        tvClassType.setText(objSchoolClasses.ClType);

        tvClassLevel.setText(" - " + objSchoolClasses.ClLevel + " - ");
        tvDescription.setText(objSchoolClasses.ClDescription);
        tvInstructor.setText(objSchoolClasses.ClInstructor);

        if (objSchoolClasses.MultiDay) {
            if (objSchoolClasses.Monday) {
                Day = "Mon";
            }
            if (objSchoolClasses.Tuesday) {
                if (Day.isEmpty()) {
                    Day = "Tue";
                } else {
                    Day = Day + "/Tue";
                }
            }
            if (objSchoolClasses.Wednesday) {
                if (Day.isEmpty()) {
                    Day = "Wed";
                } else {
                    Day = Day + "/Wed";
                }
            }
            if (objSchoolClasses.Thursday) {
                if (Day.isEmpty()) {
                    Day = "Thu";
                } else {
                    Day = Day + "/Thu";
                }
            }
            if (objSchoolClasses.Friday) {
                if (Day.isEmpty()) {
                    Day = "Fri";
                } else {
                    Day = Day + "/Fri";
                }
            }
            if (objSchoolClasses.Saturday) {
                if (Day.isEmpty()) {
                    Day = "Sat";
                } else {
                    Day = Day + "/Sat";
                }
            }
            if (objSchoolClasses.Sunday) {
                if (Day.isEmpty()) {
                    Day = "Sun";
                } else {
                    Day = Day + "/Sun";
                }
            }
        } else {
            Day = objSchoolClasses.ClDay;
        }
        tvDay.setText(Day + " from ");
        tvStart.setText(objSchoolClasses.ClStart + " - ");
        tvStop.setText(objSchoolClasses.ClStop);
        tvRoom.setText(objSchoolClasses.ClRoom);

        switch (objSchoolClasses.EnrollmentStatus) {
            case 0:
                /**
                 * Student not enrolled or waitlisted
                 */
                builder.setView(view)
                        // Title of dialog
                        .setTitle("Enroll Student in Class?")

                                // Add action buttons
                        .setPositiveButton("Enroll", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialogListener.onEnrollDialogPositiveClick(objSchoolClasses, objStudent, classPosition);
                            }

                        })
                        .setNeutralButton("WaitList", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                dialogListener.onEnrollDialogNuetralClick(objSchoolClasses,
                                        objStudent, classPosition);
                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EnrollDialog.this.getDialog().cancel();
                            }
                        });

                break;
            case 1:
                /**
                 * Student already enrolled in class
                 */
                builder.setView(view)
                        // Title of dialog
                        .setTitle("Enroll Student in Class?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EnrollDialog.this.getDialog().cancel();
                            }
                        });
                break;
            case 2:
                /**
                 * Student registered online, enrolled in class but not approved yet
                 */
                builder.setView(view)
                        // Title of dialog
                        .setTitle("Enroll Student in Class?")

                                // Add action buttons
                        .setPositiveButton("Enroll", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialogListener.onEnrollDialogPositiveClick(objSchoolClasses, objStudent, classPosition);
                            }

                        })
                        .setNeutralButton("WaitList", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                dialogListener.onEnrollDialogNuetralClick(objSchoolClasses,
                                        objStudent, classPosition);
                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EnrollDialog.this.getDialog().cancel();
                            }
                        });
                break;
            case 3:
                /**
                 * Student on waitlist
                 */
                builder.setView(view)
                        // Title of dialog
                        .setTitle("Enroll Student in Class?")

                                // Add action buttons
                        .setPositiveButton("Enroll", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialogListener.onEnrollDialogPositiveClick(objSchoolClasses, objStudent, classPosition);
                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EnrollDialog.this.getDialog().cancel();
                            }
                        });
                break;

        }

        return builder.create();
    }

}
