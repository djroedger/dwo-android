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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Kyle on 8/6/2014.
 */
public class ClassStudentListDialog extends DialogFragment {

    View view;
    AppPreferences _appPrefs;
    Activity activity;
    Student objStudent;
    SchoolClasses objSchoolClasses;
    int position;


    public ClassStudentListDialog() {
    }


    public interface ClassStudentListDialogListener {
        public void onClassStudentListDialogPositiveClick(SchoolClasses oSchoolClass, Student oStudent, int position);

        public void onClassStudentListDialogNuetralClick(SchoolClasses oSchoolClass, Student oStudent);
    }

    ClassStudentListDialogListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ClassStudentListDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ClassStudentListDialogListener");
        }
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.class_student_list_dialog, null);

        _appPrefs = new AppPreferences(getActivity());
        activity = getActivity();

        String strStudent = activity.getIntent().getStringExtra("Student");
        String strSchoolClasses = activity.getIntent().getStringExtra("SchoolClass");

        final int classPosition = getActivity().getIntent().getIntExtra("Position", 0);

        if (strStudent != "None found") {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(strStudent).getAsJsonArray();
            objStudent = gson.fromJson(array.get(0), Student.class);
        }

        if (strSchoolClasses != "None found") {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(strSchoolClasses).getAsJsonArray();
            objSchoolClasses = gson.fromJson(array.get(0), SchoolClasses.class);
        }


        Intent I = getActivity().getIntent();
        ArrayList<String> conflictsArray = I.getStringArrayListExtra("Conflicts");

        TextView tvConflicts = (TextView)
                view.findViewById(R.id.tvConflicts);

        TextView tvStudentName = (TextView)
                view.findViewById(R.id.tvStudentName);

        TextView tvPhone = (TextView)
                view.findViewById(R.id.tvPhone);

        TextView tvBirthDate = (TextView)
                view.findViewById(R.id.tvBirthDate);


        int arrayCount = conflictsArray.size();

        String conflictsMessage = "Do you want to enrol this student in the class?";

        if (arrayCount == 1)
            conflictsMessage = "The student has a conflict with the class below";
        else
            conflictsMessage = "The student hasa a conflict with the classes below";

        for (int i = 0; i < arrayCount; i++) {
            if (conflictsArray.get(i).toString().equals("anyType{}"))
                conflictsMessage += "";
            else
                conflictsMessage += "\n" + conflictsArray.get(i).toString();

        }
        tvConflicts.setText(conflictsMessage);

        tvStudentName.setText(objStudent.FName + " " + objStudent.LName);
        tvPhone.setText(objStudent.Phone);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String strBirthDate;
        if (objStudent.BirthDate.equalsIgnoreCase("")) {
            strBirthDate = "01/01/1970";
        } else {
            strBirthDate = objStudent.BirthDate;
        }
        try {
            strBirthDate = dateFormat.parse(objStudent.BirthDate).toString();
        } catch (ParseException e) {

        }
        tvBirthDate.setText(strBirthDate);


        builder.setView(view)
                // Title of dialog
                .setTitle("Enroll Student in Class?")

                        // Add action buttons
                .setPositiveButton("Enroll", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onClassStudentListDialogPositiveClick(objSchoolClasses, objStudent, classPosition);
                    }

                })
                .setNeutralButton("WaitList", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onClassStudentListDialogNuetralClick(objSchoolClasses, objStudent);

                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ClassStudentListDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
