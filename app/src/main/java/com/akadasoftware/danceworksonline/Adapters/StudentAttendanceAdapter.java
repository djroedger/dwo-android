package com.akadasoftware.danceworksonline.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.StudentAttendance;
import com.akadasoftware.danceworksonline.R;

import java.util.List;

/**
 * Created by Kyle on 5/7/2014.
 */
public class StudentAttendanceAdapter extends ArrayAdapter<StudentAttendance> {

    Activity activity;
    int resource;
    //Context newContext;
    List<StudentAttendance> Attendances;

    public StudentAttendanceAdapter(Context context, int resource, List<StudentAttendance> items) {
        super(context, resource, items);
        //newContext = context;
        this.resource = resource;
        Attendances = items;

    }

    public class ViewHolder {
        TextView tvAttendance;
        TextView tvClassDate;

    }

    /**
     * The holder is the container for each list item defined in the ViewHolder class. Below we
     * define them and find out what the equivalent is in our xml file
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("ConvertView", String.valueOf(position));
        ViewHolder holder = null;


        // Inflate the view
        if (convertView == null) {

            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            convertView = vi.inflate(R.layout.item_student_attendance, null);

            holder = new ViewHolder();

            holder.tvAttendance = (TextView) convertView
                    .findViewById(R.id.tvAttendance);

            holder.tvClassDate = (TextView) convertView
                    .findViewById(R.id.tvClassDate);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StudentAttendance oStudentAttendance = Attendances.get(position);


        String strStatus = "";

        char charStatus = oStudentAttendance.Present.charAt(0);

        switch (charStatus) {
            case 'P':
                strStatus = "Present";
                break;
            case 'A':
                strStatus = "Absent";
                break;
            case 'E':
                strStatus = "Excused";
                break;
            case 'M':
                strStatus = "Makeup";
                break;
            case 'L':
                strStatus = "Late";
                break;
            case 'I':
                strStatus = "Illness";
                break;
            case 'O':
                strStatus = "Out of Town";
                break;
            case 'C':
                strStatus = "Cancelled";
                break;
            case 'W':
                strStatus = "Watching";
                break;
            case 'N':
                strStatus = "Non Compliant";
                break;
            case 'H':
                strStatus = "Holiday";
                break;
            case 'K':
                strStatus = "Killed in Action";
                break;
            default:
                strStatus = "Abducted by aliens.";
                break;

        }

        String day = "";
        switch (oStudentAttendance.ClDayNo) {
            case 1:
                day = "Monday";
                break;
            case 2:
                day = "Tuesday";
                break;
            case 3:
                day = "Wednesday";
                break;
            case 4:
                day = "Thursday";
                break;
            case 5:
                day = "Friday";
                break;
            case 6:
                day = "Saturday";
                break;
            case 7:
                day = "Sunday";
                break;

        }

        holder.tvAttendance.setText(strStatus + " for -" + oStudentAttendance.ClLevel + " - " + oStudentAttendance.ClDescription);
        String date = oStudentAttendance.ADate;

        holder.tvClassDate.setText(day + ", " + date + " at " + oStudentAttendance.ClStart);
        return convertView;

    }
}
