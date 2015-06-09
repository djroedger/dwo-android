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
import com.akadasoftware.danceworksonline.Classes.StudentClasses;
import com.akadasoftware.danceworksonline.R;

import java.util.List;

/**
 * Created by Kyle on 4/9/2014.
 */
public class StudentClassAdapter extends ArrayAdapter<StudentClasses> {

    Activity activity;
    int resource;
    //Context newContext;
    List<StudentClasses> Classes;
    private AppPreferences _appPrefs;

    public StudentClassAdapter(Context context, int resource, List<StudentClasses> items) {
        super(context, resource, items);
        //newContext = context;
        this.resource = resource;
        Classes = items;

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

        StudentClasses oStudentClasses = Classes.get(position);
        String Day = "";
        holder.tvClassType.setText(oStudentClasses.ClType);
        holder.tvClassType.setTag(position);


        holder.tvClassLevel.setText(" - " + oStudentClasses.ClLevel + " - ");
        holder.tvDescription.setText(oStudentClasses.ClDescription);
        holder.tvInstructor.setText(oStudentClasses.ClInstructor);

        if (oStudentClasses.Multiday) {
            if (oStudentClasses.Monday) {
                Day = "Mon";
            }
            if (oStudentClasses.Tuesday) {
                if (Day.isEmpty()) {
                    Day = "Tue";
                } else {
                    Day = Day + "/Tue";
                }
            }
            if (oStudentClasses.Wednesday) {
                if (Day.isEmpty()) {
                    Day = "Wed";
                } else {
                    Day = Day + "/Wed";
                }
            }
            if (oStudentClasses.Thursday) {
                if (Day.isEmpty()) {
                    Day = "Thu";
                } else {
                    Day = Day + "/Thu";
                }
            }
            if (oStudentClasses.Friday) {
                if (Day.isEmpty()) {
                    Day = "Fri";
                } else {
                    Day = Day + "/Fri";
                }
            }
            if (oStudentClasses.Saturday) {
                if (Day.isEmpty()) {
                    Day = "Sat";
                } else {
                    Day = Day + "/Sat";
                }
            }
            if (oStudentClasses.Sunday) {
                if (Day.isEmpty()) {
                    Day = "Sun";
                } else {
                    Day = Day + "/Sun";
                }
            }
        } else {
            Day = oStudentClasses.ClDay;
        }
        holder.tvDay.setText(Day + " from ");
        holder.tvStart.setText(oStudentClasses.ClStart + " - ");
        holder.tvStop.setText(oStudentClasses.ClStop);
        holder.tvRoom.setText(oStudentClasses.ClRoom);

        return convertView;

    }

    public class ViewHolder {
        TextView tvClassType;
        TextView tvClassLevel;
        TextView tvDescription;
        TextView tvInstructor;
        TextView tvDay;
        TextView tvStart;
        TextView tvStop;
        TextView tvRoom;
    }
}
