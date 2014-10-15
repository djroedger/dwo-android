package com.akadasoftware.danceworksonline.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.Session;
import com.akadasoftware.danceworksonline.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 4/16/2014.
 */
public class SessionAdapter extends ArrayAdapter<Session> {

    Activity activity;
    int resource;
    List<Session> Sessions;

    public SessionAdapter(Context context, int resource, ArrayList<Session> items) {
        super(context, resource, items);
        this.resource = resource;
        Sessions = items;
    }

    public class ViewHolder {
        TextView tvSessions;
    }


    /**
     * Run when the spinner is open to show the items.
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Log.v("ConvertView", String.valueOf(position));
        ViewHolder holder = null;

        //Inflate the view

        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi;
        vi = (LayoutInflater) getContext().getSystemService(inflater);
        convertView = vi.inflate(R.layout.sessions, null);


        holder = new ViewHolder();
        holder.tvSessions = (TextView) convertView.findViewById(R.id.tvSessions);

        convertView.setTag(holder);

        Session oSessions = Sessions.get(position);

        holder.tvSessions.setText(oSessions.SessionName);
        holder.tvSessions.setTag(oSessions);


        return convertView;
    }


    /**
     * This is run to create the initial value shown when the spinner is created. Not all spinner
     * items will be created.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("ConvertView", String.valueOf(position));
        ViewHolder holder = null;


        /**
         * WE took out the if statement cause it caused the thign to break for some reason.?
         */


        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi;
        vi = (LayoutInflater) getContext().getSystemService(inflater);
        convertView = vi.inflate(R.layout.sessions, null);

        holder = new ViewHolder();

        holder.tvSessions = (TextView) convertView
                .findViewById(R.id.tvSessions);

        convertView.setTag(holder);

        Session oSessions = Sessions.get(position);

        holder.tvSessions.setText(oSessions.SessionName);
        holder.tvSessions.setTag(oSessions);

        return convertView;

    }
}
