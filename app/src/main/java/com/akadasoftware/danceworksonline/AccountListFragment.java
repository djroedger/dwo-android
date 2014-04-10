package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.akadasoftware.danceworksonline.classes.Account;
import com.akadasoftware.danceworksonline.classes.AppPreferences;
import com.akadasoftware.danceworksonline.classes.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 */
public class AccountListFragment extends ListFragment {

    ArrayList<Account> AccountsArray = new ArrayList<Account>();
    private AppPreferences _appPrefs;
    String METHOD_NAME = "";
    static String SOAP_ACTION = "getAccounts";
    static String strQuery = "";
    static SoapSerializationEnvelope envelopeOutput;
    Activity activity;
    static User user;

    /**
     * Listener to handle clicks on the list
     */
    private OnAccountSelectedListener mListener;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    AccountListAdapater acctListAdpater;


    /**
     * Creates a new instance of the Accountlist fragment and sets the arguments of that fragment to
     * a list of accounts. Creating a new AccountListFragment runs the onCreate method which then
     * checks whether the accountsarray is populated or not and takes the appropriate action. Bundle
     * args is used to send in arguments to the onCreate method (we think).
     */
    public interface OnAccountSelectedListener {
        // TODO: Update argument type and name
        public void OnAccountSelected(int id);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _appPrefs = new AppPreferences(getActivity());
        AccountsArray = _appPrefs.getAccounts();
        user = _appPrefs.getUser();
        strQuery = _appPrefs.getAccountQuery();

        if (AccountsArray.size() > 0) {
            acctListAdpater = new AccountListAdapater(getActivity(),
                    R.layout.item_accountlist, AccountsArray);
            setListAdapter(acctListAdpater);
            acctListAdpater.setNotifyOnChange(true);
        } else {
            getAccountsList accountlist = new getAccountsList();
            accountlist.execute();
        }

    }

    /**
     * Change the divider line color
     *
     * @Override public void onActivityCreated(Bundle savedInstanceState) {
     * super.onActivityCreated(savedInstanceState);
     * ColorDrawable light_blue = new ColorDrawable(this.getResources().getColor(R.color.light_blue));
     * getListView().setDivider(light_blue);
     * getListView().setDividerHeight(2);
     * }
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAccountSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAccountSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Called when one of the accounts is selected in the accountlist page calling onAccountSelected
     * takes us to the onAccountSelected method on the home page were it saves the account position
     * in the list and then starts our new activity which is opening the AccountInformationFragment
     * page.
     */

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mListener.OnAccountSelected(position);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    class Data {

        private static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
        private static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
    }

    private class getAccountsList extends
            AsyncTask<Data, Void, ArrayList<Account>> {

        @Override
        protected ArrayList<Account> doInBackground(Data... data) {

            return getAccounts();
        }

        protected void onPostExecute(ArrayList<Account> result) {

            AccountsArray = result;
            acctListAdpater = new AccountListAdapater(getActivity(),
                    R.layout.item_accountlist, AccountsArray);
            setListAdapter(acctListAdpater);
            _appPrefs.saveAccounts(result);
            acctListAdpater.setNotifyOnChange(true);


        }
    }

    public static ArrayList<Account> getAccounts() {
        String MethodName = "getAccounts";
        SoapObject response = InvokeMethod(Data.URL, MethodName);
        return RetrieveFromSoap(response);

    }

    public static SoapObject InvokeMethod(String URL, String MethodName) {

        SoapObject request = GetSoapObject(MethodName);


        PropertyInfo Order = new PropertyInfo();
        Order.setType("STRING_CLASS");
        Order.setName("Order");
        Order.setValue(strQuery);
        request.addProperty(Order);

        PropertyInfo SchID = new PropertyInfo();
        SchID.setName("SchID");
        SchID.setValue(user.SchID);
        request.addProperty(SchID);

        PropertyInfo UserID = new PropertyInfo();
        UserID.setName("UserID");
        UserID.setValue(user.UserID);
        request.addProperty(UserID);

        PropertyInfo UserGUID = new PropertyInfo();
        UserGUID.setType("STRING_CLASS");
        UserGUID.setName("UserGUID");
        UserGUID.setValue(user.UserGUID);
        request.addProperty(UserGUID);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeCall(URL, envelope, Data.NAMESPACE, MethodName);
    }

    public static SoapObject GetSoapObject(String MethodName) {
        return new SoapObject(Data.NAMESPACE, MethodName);
    }

    public static SoapObject MakeCall(String URL,
                                      SoapSerializationEnvelope envelope, String NAMESPACE,
                                      String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        try {
            envelope.addMapping(Data.NAMESPACE, "Account",
                    new Account().getClass());
            HttpTransport.call(SOAP_ACTION, envelope);
            envelopeOutput = envelope;
            SoapObject response = (SoapObject) envelope.getResponse();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Account> RetrieveFromSoap(SoapObject soap) {

        ArrayList<Account> Accounts = new ArrayList<Account>();
        for (int i = 0; i < soap.getPropertyCount() - 1; i++) {

            SoapObject accountlistitem = (SoapObject) soap.getProperty(i);
            Account account = new Account();
            for (int j = 0; j < accountlistitem.getPropertyCount() - 1; j++) {
                account.setProperty(j, accountlistitem.getProperty(j)
                        .toString());
                if (accountlistitem.getProperty(j).equals("anyType{}")) {
                    accountlistitem.setProperty(j, "");
                }

            }
            Accounts.add(i, account);
        }

        return Accounts;
    }
}
