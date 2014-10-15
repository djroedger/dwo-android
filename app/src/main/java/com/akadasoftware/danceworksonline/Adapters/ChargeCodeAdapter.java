package com.akadasoftware.danceworksonline.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.ChargeCodes;
import com.akadasoftware.danceworksonline.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 2/5/14.
 * In Android development, any time you want to show a list of items vertically you will want to use
 * a ListView and an Adapter. The simplest adapter is called an ArrayAdapter because the adapter
 * takes an Array and converts the items into View objects to be loaded into the ListView container.
 * The ArrayAdapter fits in the middle between the Array (data source) and the List View
 * (representation) and describes two things:
 * Which array to use as the data source
 * How to convert any item in the array to a View object
 * <p/>
 * In this case it was used for a spinner object instead of a listview
 */
public class ChargeCodeAdapter extends ArrayAdapter<ChargeCodes> {

    private AppPreferences _appPrefs;
    List<ChargeCodes> Codes;
    Context context;
    int resource;

    public ChargeCodeAdapter(Context context, int resource, ArrayList<ChargeCodes> items) {
        super(context, resource, items);
        this.resource = resource;
        Codes = items;
    }

    public class ViewHolder {

        TextView tvChargeCode;
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
        convertView = vi.inflate(R.layout.chargecodes, null);


        holder = new ViewHolder();
        holder.tvChargeCode = (TextView) convertView.findViewById(R.id.tvChargeCodes);

        convertView.setTag(holder);

        ChargeCodes oChargeCodes = Codes.get(position);


        holder.tvChargeCode.setText(oChargeCodes.ChgDesc);
        holder.tvChargeCode.setTag(oChargeCodes);


        return convertView;
    }

    /**
     * This is run to create the initial value shown when the spinner is created. Not all spinner
     * items will be created.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("ConvertView", String.valueOf(position));
        ViewHolder holder = null;

        //Inflate the view
        if (convertView == null) {
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            convertView = vi.inflate(R.layout.chargecodes, null);

            holder = new ViewHolder();
            holder.tvChargeCode = (TextView) convertView.findViewById(R.id.tvChargeCodes);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        ChargeCodes oChargeCodes = Codes.get(position);


        holder.tvChargeCode.setText(oChargeCodes.ChgDesc);
        holder.tvChargeCode.setTag(oChargeCodes);


        return convertView;
    }
}
