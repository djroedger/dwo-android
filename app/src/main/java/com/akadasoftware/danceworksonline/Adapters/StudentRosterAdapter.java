package com.akadasoftware.danceworksonline.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.StudentRoster;
import com.akadasoftware.danceworksonline.R;

import java.util.List;

/**
 * Created by Kyle on 7/10/2014.
 */
public class StudentRosterAdapter extends ArrayAdapter<StudentRoster> {

    private AppPreferences _appPrefs;
    Activity activity;
    int resource;
    List<StudentRoster> rosterStudents;

    public StudentRosterAdapter(Context context, int resource, List<StudentRoster> items) {
        super(context, resource, items);
        this.resource = resource;
        rosterStudents = items;
    }


    public class ViewHolder {
        TextView tvFNameLName;
        TextView tvStatus;
        TextView tvPhone;
        TextView tvBirthDate;

    }

    /**
     * The holder is the container for each list item defined in the ViewHolder class. Below we
     * define them and find out what the equivalent is in our xml file
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("ConertView", String.valueOf(position));
        ViewHolder holder = null;


        // Inflate the view
        if (convertView == null) {

            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            convertView = vi.inflate(R.layout.item_student_roster, null);

            holder = new ViewHolder();
            holder.tvFNameLName = (TextView) convertView
                    .findViewById(R.id.tvFNameLName);

            holder.tvStatus = (TextView) convertView
                    .findViewById(R.id.tvStatus);
            holder.tvPhone = (TextView) convertView
                    .findViewById(R.id.tvPhone);

            holder.tvBirthDate = (TextView) convertView
                    .findViewById(R.id.tvBirthDate);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        StudentRoster oStudentRoster = rosterStudents.get(position);

        String strName = oStudentRoster.FName + " " + oStudentRoster.LName;

        String strStatus = "";

        switch (oStudentRoster.Status) {
            case 0:
                strStatus = "Active";
                break;
            case 1:
                strStatus = "Inactive";
                break;
            case 2:
                strStatus = "Prospect";
                break;
            case 3:
                strStatus = "Deleted";
                break;
        }

        holder.tvFNameLName.setText(strName);
        holder.tvFNameLName.setTag(position);

        holder.tvStatus.setText("(" + strStatus + ")");
        holder.tvBirthDate.setText("Age: " + oStudentRoster.BirthDate);
        holder.tvBirthDate.setText("" + oStudentRoster.Phone);


        return convertView;

    }
}

