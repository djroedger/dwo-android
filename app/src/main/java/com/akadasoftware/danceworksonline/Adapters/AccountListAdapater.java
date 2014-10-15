package com.akadasoftware.danceworksonline.Adapters;

/**
 * Created by Kyle on 1/7/14.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.Account;
import com.akadasoftware.danceworksonline.R;

import java.util.ArrayList;
import java.util.List;


public class AccountListAdapater extends ArrayAdapter<Account> {

    Activity activity;
    int resource;
    private int selectedPos;
    List<Account> Accounts;

    public AccountListAdapater(Context context, int resource,
                               ArrayList<Account> items) {
        super(context, resource, items);
        this.resource = resource;
        Accounts = items;
    }


    public class ViewHolder {
        TextView tvAccountInformation;

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
            convertView = vi.inflate(R.layout.item_accountlist, null);

            holder = new ViewHolder();
            holder.tvAccountInformation = (TextView) convertView
                    .findViewById(R.id.tvAccountInformation);


            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        Account oAccount = Accounts.get(position);

        holder.tvAccountInformation.setText(oAccount.LName + ", " + oAccount.FName);

        holder.tvAccountInformation.setTextSize(20);

        return convertView;

    }

}
