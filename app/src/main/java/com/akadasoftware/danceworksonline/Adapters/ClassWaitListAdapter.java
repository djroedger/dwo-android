package com.akadasoftware.danceworksonline.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.ClassWaitList;
import com.akadasoftware.danceworksonline.R;

import java.util.List;

/**
 * Created by Kyle on 7/21/2014.
 */
public class ClassWaitListAdapter extends ArrayAdapter<ClassWaitList> {

    Activity activity;
    int resource;
    List<ClassWaitList> waitList;

    public ClassWaitListAdapter(Context context, int resource, List<ClassWaitList> items) {
        super(context, resource, items);
        this.resource = resource;
        waitList = items;
    }


    public class ViewHolder {
        TextView tvFNameLName;
        TextView tvStudentStatus;
        TextView tvPhone;
        TextView tvAcctName;
        TextView tvAcctStatus;

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
            convertView = vi.inflate(R.layout.item_class_waitlist, null);

            holder = new ViewHolder();
            holder.tvFNameLName = (TextView) convertView
                    .findViewById(R.id.tvFNameLName);

            holder.tvStudentStatus = (TextView) convertView
                    .findViewById(R.id.tvStudentStatus);
            holder.tvPhone = (TextView) convertView
                    .findViewById(R.id.tvPhone);

            holder.tvAcctName = (TextView) convertView
                    .findViewById(R.id.tvAcctName);
            holder.tvAcctStatus = (TextView) convertView
                    .findViewById(R.id.tvAcctStatus);


            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        ClassWaitList oWaitList = waitList.get(position);


        String strAStatus = "";

        String strSStatus = "";

        switch (oWaitList.AStatus) {
            case 0:
                strAStatus = "Active";
                break;
            case 1:
                strAStatus = "Inactive";
                break;
            case 2:
                strAStatus = "Prospect";
                break;
            case 3:
                strAStatus = "Deleted";
                break;
            case 4:
                strAStatus = "No Account";
                break;
        }

        switch (oWaitList.SStatus) {
            case 0:
                strSStatus = "Active";
                break;
            case 1:
                strSStatus = "Inactive";
                break;
            case 2:
                strSStatus = "Prospect";
                break;
            case 3:
                strSStatus = "Deleted";
                break;
            case 4:
                strSStatus = "No Account";
                break;
        }

        holder.tvFNameLName.setText(oWaitList.CName + " - ");
        holder.tvFNameLName.setTag(position);

        holder.tvStudentStatus.setText(strSStatus);
        holder.tvAcctName.setText(" (Account: " + oWaitList.PName);
        holder.tvAcctStatus.setText(" - " + strAStatus + ")");
        holder.tvPhone.setText("Phone: " + oWaitList.Phone);


        return convertView;

    }
}
