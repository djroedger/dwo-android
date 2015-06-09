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
import com.akadasoftware.danceworksonline.Classes.StudentWaitList;
import com.akadasoftware.danceworksonline.R;

import java.util.List;

/**
 * Created by Kyle on 4/14/2014.
 */
public class StudentWaitListAdapter extends ArrayAdapter<StudentWaitList> {

    Activity activity;
    int resource;
    //Context newContext;
    List<StudentWaitList> StudentWaitLists;
    private AppPreferences _appPrefs;

    public StudentWaitListAdapter(Context context, int resource, List<StudentWaitList> items) {
        super(context, resource, items);
        this.resource = resource;
        StudentWaitLists = items;
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
            convertView = vi.inflate(R.layout.item_studentwaitlist, null);

            holder = new ViewHolder();

            holder.tvClassType = (TextView) convertView
                    .findViewById(R.id.tvClassType);

            holder.tvClassLevel = (TextView) convertView
                    .findViewById(R.id.tvClassLevel);

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

            holder.tvNotes = (TextView) convertView
                    .findViewById(R.id.tvNotes);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        StudentWaitList oStudentWaitList = StudentWaitLists.get(position);


        holder.tvClassType.setText(oStudentWaitList.ClType);
        holder.tvClassType.setTag(position);


        holder.tvClassLevel.setText(" - " + oStudentWaitList.ClLevel + " - ");

        holder.tvInstructor.setText(oStudentWaitList.ClInstructor);

        String Day = "";

        if (oStudentWaitList.Multiday) {
            if (oStudentWaitList.Monday) {
                Day = "Mon";
            }
            if (oStudentWaitList.Tuesday) {
                if (Day.isEmpty()) {
                    Day = "Tue";
                } else {
                    Day = Day + "/Tue";
                }
            }
            if (oStudentWaitList.Wednesday) {
                if (Day.isEmpty()) {
                    Day = "Wed";
                } else {
                    Day = Day + "/Wed";
                }
            }
            if (oStudentWaitList.Thursday) {
                if (Day.isEmpty()) {
                    Day = "Thu";
                } else {
                    Day = Day + "/Thu";
                }
            }
            if (oStudentWaitList.Friday) {
                if (Day.isEmpty()) {
                    Day = "Fri";
                } else {
                    Day = Day + "/Fri";
                }
            }
            if (oStudentWaitList.Saturday) {
                if (Day.isEmpty()) {
                    Day = "Sat";
                } else {
                    Day = Day + "/Sat";
                }
            }
            if (oStudentWaitList.Sunday) {
                if (Day.isEmpty()) {
                    Day = "Sun";
                } else {
                    Day = Day + "/Sun";
                }
            }
        } else {
            Day = oStudentWaitList.ClDay;
        }
        holder.tvDay.setText(Day + " from ");
        holder.tvStart.setText(oStudentWaitList.ClStart + " - ");
        holder.tvStop.setText(oStudentWaitList.ClStop);
        holder.tvRoom.setText(" - " + oStudentWaitList.ClRoom);
        holder.tvNotes.setText(oStudentWaitList.Notes);
        return convertView;

    }

    public class ViewHolder {
        TextView tvClassType;
        TextView tvClassLevel;
        TextView tvInstructor;
        TextView tvDay;
        TextView tvStart;
        TextView tvStop;
        TextView tvRoom;
        TextView tvNotes;
    }
}


