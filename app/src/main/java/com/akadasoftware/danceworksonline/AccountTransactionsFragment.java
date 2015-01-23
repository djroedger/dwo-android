package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akadasoftware.danceworksonline.Adapters.AccountTransactionAdapter;
import com.akadasoftware.danceworksonline.Classes.Account;
import com.akadasoftware.danceworksonline.Classes.AccountTransactions;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the CallBacks
 * interface.
 */
public class AccountTransactionsFragment extends ListFragment {


    ArrayList<AccountTransactions> TransactionArray = new ArrayList<AccountTransactions>();
    String METHOD_NAME = "";
    static String SOAP_ACTION = "getAccountTransactions";
    static SoapSerializationEnvelope envelopeOutput;
    static User oUser;
    private AppPreferences _appPrefs;
    Globals oGlobals;
    Account oAccount;
    Activity activity;
    ArrayList<Account> arrayAccounts;

    private PullToRefreshLayout mPullToRefreshLayout;
    private OnTransactionSelected mListener;


    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private AccountTransactionAdapter transAdapter;

    /**
     * Interface used to communicate between two fragments. The method OnTrasnactionSelected is
     * defined in the AccountInformation page and the two items are found in the list item click
     * by getting the position of the Transaction array because it corresponds to the position
     * in the list.
     */
    public interface OnTransactionSelected {
        // TODO: Update argument type and name
        public void OnTransactionSelected(float amount, int TID, String description);
    }

    public static AccountTransactionsFragment newInstance(int position) {
        AccountTransactionsFragment fragment = new AccountTransactionsFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activity = getActivity();
        _appPrefs = new AppPreferences(activity);
        oGlobals = new Globals();
        arrayAccounts = _appPrefs.getAccounts();
        int position = getArguments().getInt("Position");

        oAccount = arrayAccounts.get(position);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTransactionSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTransactionSelected");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAccountTransactions trans = new getAccountTransactions();
        trans.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container,
                savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_accounttransactions_list, container, false);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setListAdapter(null);
    }

    /**
     * Using ActionBar-PullToRefresh with a ListFragment is slightly different, as ListFragment
     * provides the whole content view itself. This means that we need to insert ourselves into the
     * view hierachy. That is why the code for it is put in the onViewCreated instead of
     * onCreateView
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup viewGroup = (ViewGroup) view;

        mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

        ActionBarPullToRefresh.from(activity)

                // We need to insert the PullToRefreshLayout into the Fragment's ViewGroup
                .insertLayoutInto(viewGroup)

                        // We need to mark the ListView and it's Empty View as pull-able
                        // This is because they are not direct children of the ViewGroup
                .theseChildrenArePullable(getListView(), getListView().getEmptyView())

                        // Set the OnRefreshListener
                .options(Options.create().refreshOnUp(true).build())


                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        Toast toast = Toast.makeText(activity, "Refreshing :)"
                                , Toast.LENGTH_SHORT);
                        toast.show();
                        getAccountTransactions trans = new getAccountTransactions();
                        trans.execute();
                        mPullToRefreshLayout.setRefreshComplete();

                    }

                })

                        // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);

    }


    /**
     * Creates a new instance of the AccountTransaction = to the position of the listview
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item

        AccountTransactions trans = TransactionArray.get(position);

        if (trans.Type.equals("C")) {
            if (trans.Status.equals("V")) {
                Toast toast = Toast.makeText(activity, "This is a voided charge and cannot be paid."
                        , Toast.LENGTH_LONG);
                toast.show();
            } else if (trans.Balance == 0) {
                Toast toast = Toast.makeText(activity, "Balance is $0 and cannot be paid.",
                        Toast.LENGTH_LONG);
                toast.show();
            } else {
                _appPrefs.saveChgID(trans.TID);
                mListener.OnTransactionSelected(trans.Balance, trans.TID, trans.TDesc);
            }
        }

    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        ListView listView = (ListView) activity.findViewById(R.id.list);
        //listView.setEmptyView( activity.findViewById( R.id.empty_list_item ) );
        View emptyView = listView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    class Data {

        private static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
        private static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
    }

    public class getAccountTransactions extends
            AsyncTask<Data, Void, ArrayList<AccountTransactions>> {

        @Override
        protected ArrayList<AccountTransactions> doInBackground(Data... data) {

            String strOrder = " Order by TDate,TNO";
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("Order", strOrder);
            params.put("AcctID", String.valueOf(_appPrefs.getAcctID()));
            params.put("UserID", String.valueOf(_appPrefs.getUserID()));
            params.put("UserGUID", _appPrefs.getUserGUID());
            String url = oGlobals.URLBuilder("getAccountTransactions?", params);
            String response = oGlobals.callJSON(url);
            ArrayList<AccountTransactions> transactionsArray = new ArrayList<AccountTransactions>();
            try {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                //Sets what the the object will be deserialized too.
                Type collectionType = new TypeToken<ArrayList<AccountTransactions>>() {
                }.getType();
                transactionsArray = gson.fromJson(response, collectionType);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return transactionsArray;
            //return getTransactions();
        }

        protected void onPostExecute(ArrayList<AccountTransactions> result) {

            TransactionArray = result;
            transAdapter = new AccountTransactionAdapter(getActivity(),
                    R.layout.item_transactionlist, TransactionArray);
            setListAdapter(transAdapter);
            transAdapter.setNotifyOnChange(true);

        }
    }

  /*  public ArrayList<AccountTransactions> getTransactions() {
        String MethodName = "getAccountTransactions";
        SoapObject response = InvokeMethod(Data.URL, MethodName);
        return RetrieveAccountTransactionsFromSoap(response);

    }

    public SoapObject InvokeMethod(String URL, String MethodName) {

        SoapObject request = GetSoapObject(MethodName);
        oUser = _appPrefs.getUser();

        PropertyInfo piOrder = new PropertyInfo();
        piOrder.setType("STRING_CLASS");
        piOrder.setName("Order");
        piOrder.setValue(" ORDER BY TDate DESC");
        request.addProperty(piOrder);

        PropertyInfo piAcctID = new PropertyInfo();
        piAcctID.setName("AcctID");
        piAcctID.setValue(oAccount.AcctID);
        request.addProperty(piAcctID);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setType("STRING_CLASS");
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

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
            envelope.addMapping(Data.NAMESPACE, "AccountTransactions",
                    new AccountTransactions().getClass());
            HttpTransport.call(SOAP_ACTION, envelope);
            envelopeOutput = envelope;
            SoapObject response = (SoapObject) envelope.getResponse();

            return response;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static ArrayList<AccountTransactions> RetrieveAccountTransactionsFromSoap(SoapObject soap) {

        ArrayList<AccountTransactions> AccountTrans = new ArrayList<AccountTransactions>();
        for (int i = 0; i < soap.getPropertyCount() - 1; i++) {

            SoapObject accounttransitem = (SoapObject) soap.getProperty(i);

            AccountTransactions trans = new AccountTransactions();
            for (int j = 0; j < accounttransitem.getPropertyCount() - 1; j++) {
                trans.setProperty(j, accounttransitem.getProperty(j)
                        .toString());
                if (accounttransitem.getProperty(j).equals("anyType{}")) {
                    accounttransitem.setProperty(j, "");
                }

            }
            AccountTrans.add(i, trans);
        }

        return AccountTrans;
    }*/

}
