package com.akadasoftware.danceworksonline.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.AccountTransactions;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 1/15/14.
 * In Android development, any time you want to show a list of items vertically you will want to use
 * a ListView and an Adapter. The simplest adapter is called an ArrayAdapter because the adapter
 * takes an Array and converts the items into View objects to be loaded into the ListView container.
 * The ArrayAdapter fits in the middle between the Array (data source) and the List View
 * (representation) and describes two things:
 * Which array to use as the data source
 * How to convert any item in the array to a View object
 */
public class AccountTransactionAdapter extends ArrayAdapter<AccountTransactions> {

    private AppPreferences _appPrefs;
    Activity activity;
    int resource;
    private int selectedPos;
    String strResponse, strKind, strType;
    List<AccountTransactions> transactionsList;


    public AccountTransactionAdapter(Context context, int resource, ArrayList<AccountTransactions> items) {
        super(context, resource, items);
        this.resource = resource;
        transactionsList = items;
    }

    public class ViewHolder {
        TextView tvDate;
        TextView tvType;
        TextView tvDescription;
        TextView tvAmount;
        TextView tvBalance;
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
            convertView = vi.inflate(R.layout.item_transactionlist, null);

            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView
                    .findViewById(R.id.tvDate);

            holder.tvType = (TextView) convertView
                    .findViewById(R.id.tvType);

            holder.tvDescription = (TextView) convertView
                    .findViewById(R.id.tvDescription);

            holder.tvAmount = (TextView) convertView
                    .findViewById(R.id.tvAmount);

            holder.tvBalance = (TextView) convertView
                    .findViewById(R.id.tvBalance);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        AccountTransactions oAccountTransactions = transactionsList.get(position);
        NumberFormat format = NumberFormat.getCurrencyInstance();

        if (oAccountTransactions.Kind.equals("$")) {
            strKind = "CASH";

        } else if (oAccountTransactions.Kind.equals("C")) {
            strKind = "CHECK";

        } else if (oAccountTransactions.Kind.equals("V")) {
            strKind = "VISA";

        } else if (oAccountTransactions.Kind.equals("M")) {
            strKind = "M/C";

        } else if (oAccountTransactions.Kind.equals("D")) {
            strKind = "DISC";

        } else if (oAccountTransactions.Kind.equals("A")) {
            strKind = "AMEX";

        } else if (oAccountTransactions.Kind.equals("O")) {
            strKind = "OTHER";

        } else {
            strKind = "Refund";

        }

        if (oAccountTransactions.Type.equals("C")) {
            strType = "Charge";

        } else if (oAccountTransactions.Type.equals("P")) {
            strType = "Payment";

        } else if (oAccountTransactions.Type.equals("R")) {
            strType = "Refund";

        } else if (oAccountTransactions.Type.equals("M")) {
            strType = "Credit";

        } else {
            strType = "I have no freaking clue";

        }

        //String date with time portion chopped off
        String strDate = oAccountTransactions.TDate.substring(0, 10);
        //Switching order around so that mm/dd/yyyy
        String strTestDate = strDate.substring(5, strDate.length()) + "/" + strDate.substring(0, 4);
        String strFinalDate = strTestDate.replace("-", "/");

        holder.tvDate.setText(strFinalDate + " ");
        holder.tvDate.setTag(position);
        holder.tvDate.setWidth(175);

        if (oAccountTransactions.Type.contains("P") || oAccountTransactions.Type.contains("R"))
            holder.tvType.setText(strType + "\n" + strKind);
        else
            holder.tvType.setText(strType + " ");
        holder.tvType.setWidth(175);

        holder.tvDescription.setText(oAccountTransactions.TDesc + " ");
        holder.tvDescription.setWidth(175);
        holder.tvAmount.setText(" " + String.valueOf(format.format(oAccountTransactions.Amount)));
        holder.tvAmount.setWidth(160);


        return convertView;

    }

}
