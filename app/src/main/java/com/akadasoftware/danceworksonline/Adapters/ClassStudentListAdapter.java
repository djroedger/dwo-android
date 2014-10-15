package com.akadasoftware.danceworksonline.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 8/6/2014.
 */
public class ClassStudentListAdapter extends ArrayAdapter<Student> {


    Activity activity;
    int resource;
    private int selectedPos;
    List<Student> Students;

    public ClassStudentListAdapter(Context context, int resource, ArrayList<Student> items) {
        super(context, resource, items);
        this.resource = resource;
        Students = items;
    }


    public class ViewHolder {
        TextView tvStudentInformation;

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
            convertView = vi.inflate(R.layout.item_studentlist, null);

            holder = new ViewHolder();
            holder.tvStudentInformation = (TextView) convertView
                    .findViewById(R.id.tvStudentInformation);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        Student oStudent = Students.get(position);

        holder.tvStudentInformation.setText(oStudent.LName + ", " + oStudent.FName);

        holder.tvStudentInformation.setTextSize(20);


        return convertView;

    }

}
