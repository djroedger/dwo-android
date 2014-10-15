package com.akadasoftware.danceworksonline.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 5/21/2014.
 */
public class SchoolClassAdapter extends ArrayAdapter<SchoolClasses> {

    private AppPreferences _appPrefs;
    Activity activity;
    int resource;
    //Context newContext;
    List<SchoolClasses> Classes;

    public SchoolClassAdapter(Context context, int resource, ArrayList<SchoolClasses> items) {
        super(context, resource, items);
        //newContext = context;
        this.resource = resource;
        Classes = items;

    }

    public class ViewHolder {
        TextView tvClassType;
        TextView tvEnrollmentStatus;
        TextView tvClassLevel;
        TextView tvDescription;
        TextView tvInstructor;
        TextView tvDay;
        TextView tvStart;
        TextView tvStop;
        TextView tvRoom;
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
            convertView = vi.inflate(R.layout.item_studentclass, null);

            holder = new ViewHolder();

            holder.tvClassType = (TextView) convertView
                    .findViewById(R.id.tvClassType);

            holder.tvEnrollmentStatus = (TextView) convertView
                    .findViewById(R.id.tvEnrollmentStatus);

            holder.tvClassLevel = (TextView) convertView
                    .findViewById(R.id.tvClassLevel);

            holder.tvDescription = (TextView) convertView
                    .findViewById(R.id.tvDescription);

            holder.tvInstructor = (TextView) convertView
                    .findViewById(R.id.tvInstructor);

            holder.tvDay = (TextView) convertView
                    .findViewById(R.id.tvDay);

            holder.tvStart = (TextView) convertView
                    .findViewById(R.id.tvStart);

            holder.tvStop = (TextView) convertView
                    .findViewById(R.id.tvStop);

            holder.tvRoom = (TextView) convertView
                    .findViewById(R.id.tvRoom);


            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SchoolClasses oSchoolClasses = Classes.get(position);
        String Day = "";
        holder.tvClassType.setText(oSchoolClasses.ClType);
        holder.tvClassType.setTag(position);

        holder.tvClassLevel.setText(" - " + oSchoolClasses.ClLevel + " - ");
        holder.tvDescription.setText(oSchoolClasses.ClDescription);
        holder.tvInstructor.setText(oSchoolClasses.ClInstructor);

        if (oSchoolClasses.MultiDay) {
            if (oSchoolClasses.Monday) {
                Day = "Mon";
            }
            if (oSchoolClasses.Tuesday) {
                if (Day.isEmpty()) {
                    Day = "Tue";
                } else {
                    Day = Day + "/Tue";
                }
            }
            if (oSchoolClasses.Wednesday) {
                if (Day.isEmpty()) {
                    Day = "Wed";
                } else {
                    Day = Day + "/Wed";
                }
            }
            if (oSchoolClasses.Thursday) {
                if (Day.isEmpty()) {
                    Day = "Thu";
                } else {
                    Day = Day + "/Thu";
                }
            }
            if (oSchoolClasses.Friday) {
                if (Day.isEmpty()) {
                    Day = "Fri";
                } else {
                    Day = Day + "/Fri";
                }
            }
            if (oSchoolClasses.Saturday) {
                if (Day.isEmpty()) {
                    Day = "Sat";
                } else {
                    Day = Day + "/Sat";
                }
            }
            if (oSchoolClasses.Sunday) {
                if (Day.isEmpty()) {
                    Day = "Sun";
                } else {
                    Day = Day + "/Sun";
                }
            }
        } else {
            Day = oSchoolClasses.ClDay;
        }
        holder.tvDay.setText(Day + " from ");
        holder.tvStart.setText(oSchoolClasses.ClStart + " - ");
        holder.tvStop.setText(oSchoolClasses.ClStop);
        holder.tvRoom.setText(oSchoolClasses.ClRoom);

        switch (oSchoolClasses.EnrollmentStatus) {
            case 0:
                /**
                 * Student not enrolled or waitlisted
                 */
                convertView.setBackgroundColor(Color.WHITE);
                holder.tvEnrollmentStatus.setText("");
                break;
            case 1:
                /**
                 * Student already enrolled in class
                 */
                convertView.setBackgroundColor(Color.GREEN);
                holder.tvEnrollmentStatus.setText("Enrolled");
                break;
            case 2:
                /**
                 * Student registered online, enrolled in class but not approved yet
                 */
                convertView.setBackgroundColor(Color.RED);
                holder.tvEnrollmentStatus.setText("Pending approval");
                break;
            case 3:
                /**
                 * Student on waitlist
                 */
                convertView.setBackgroundColor(Color.YELLOW);
                holder.tvEnrollmentStatus.setText("On wait list");
                break;

        }


        return convertView;

    }

    public void replaceSchoolClass(SchoolClasses objSchoolClasses, int intClassPosition) {
        Classes.set(intClassPosition, objSchoolClasses);
        notifyDataSetChanged();
    }
}
