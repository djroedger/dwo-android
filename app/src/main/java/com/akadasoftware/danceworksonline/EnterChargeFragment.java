package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akadasoftware.danceworksonline.Adapters.ChargeCodeAdapter;
import com.akadasoftware.danceworksonline.Classes.Account;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.ChargeCodes;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * A fragment that is used to run both the getChargeCodes and getChargeAmount web methods.
 * It gets the account from _appPrefs as a position in the accounts array list and from that
 * account uses fields like acctid, billingfreq, mtuition etc. It also uses the ChargeCode class
 * for other values. Those values are retrieved from the web method with the values userid,
 * userguid and schid which are all in _appPrefs. It uses two interfaces to be able to run
 * editAmount and editDate dialogs. The end result is a spinner populated by the
 * ChgDesc field and then values returned with DiscountedAmount and Total w/ tax textviews
 * only showing up if their values have changed.
 */


public class EnterChargeFragment extends Fragment {


    String SOAP_ACTION, METHOD_NAME, userGUID, strDate, chargeDate;
    int schID, userID;


    private AppPreferences _appPrefs;
    Account oAccount;
    Activity activity;
    ArrayList<Account> arrayAccounts;
    ArrayList<ChargeCodes> arrayChargeCodes = new ArrayList<ChargeCodes>();
    ChargeCodeAdapter adapterChargeCodes;
    ChargeCodes oChargeCodes;
    float floatAmount, floatDiscAmount, floatSTax1, floatSTax2;

    TextView tvDate, tvDescription, tvAmount, tvChangeAmount, tvAmountHint, tvDiscAmount, tvDiscAmountDisplayed, tvTotal, tvTotalDisplayed;
    EditText etDescription;

    Spinner ChargeCodeSpinner;
    Button btnCharge;
    Calendar cal;
    Globals oGlobals;

    // Listeners for the interface used to handle the dialog pop-ups
    private onEditAmountDialog mListener;
    private onEditDateDialog dateListener;

    // Called to do initial creation of a fragment. This is called after onAttach(Activity) and
    // before onCreateView(LayoutInflater, ViewGroup, Bundle).Note that this can be called while
    // the fragment's activity is still in the process of being created. As such, you can not rely
    // on things like the activity's content view hierarchy being initialized at this point.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        _appPrefs = new AppPreferences(activity);

        arrayAccounts = _appPrefs.getAccounts();

        int position = getArguments().getInt("Position");

        oAccount = arrayAccounts.get(position);

        oGlobals = new Globals();

    }

    @Override
    public void onResume() {
        super.onResume();
        getChargeCodesAsync chargeCodes = new getChargeCodesAsync();
        chargeCodes.execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onEditAmountDialog) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEditAmountDialog");
        }
        try {
            dateListener = (onEditDateDialog) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEditDateDialog");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        dateListener = null;
    }

    /**
     * Uses a interface so that it can communicate with the parent activity which is AccountInformation
     * First it comes here and then goes to AccountInformation where it is then created with the
     * onEditAmountDialog method.. That method goes to the EditAmountDialog class which takes the
     * new Amount from the dialog and returns it to the onFinishEditAmountDialog method that from there
     * runs the runChargeAmountAsync method because we could not access it otherwise from outside this
     * class.
     */
    public interface onEditAmountDialog {
        // TODO: Update argument type and name
        public void onEditAmountDialog(String input);
    }

    public interface onEditDateDialog {
        // TODO: Update argument type and name
        public void onEditDateDialog(Calendar today);
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static EnterChargeFragment newInstance(int position) {
        EnterChargeFragment fragment = new EnterChargeFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public EnterChargeFragment() {
        // Required empty public constructor
    }

    /**
     * This method is to handle if your activity is every paused by a semi-transparent activity starts
     * ie opening an app drawer. Here you would handle such things as pausing a video or stopping
     * an animation.
     */
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
    }

    // Called to have the fragment instantiate its user interface view. This is optional, and
    // non-graphical fragments can return null (which is the default implementation)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_entercharge, container, false);

        //use inflated view to find elements on page
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        tvAmount = (TextView) rootView.findViewById(R.id.tvAmount);
        tvChangeAmount = (TextView) rootView.findViewById(R.id.tvChangeAmount);
        tvAmountHint = (TextView) rootView.findViewById(R.id.tvAmountHint);
        etDescription = (EditText) rootView.findViewById(R.id.etDescription);
        ChargeCodeSpinner = (Spinner) rootView.findViewById(R.id.chargecodespinner);

        btnCharge = (Button) rootView.findViewById(R.id.btnCharge);
        tvDiscAmount = (TextView) rootView.findViewById(R.id.tvDiscAmount);
        tvDiscAmountDisplayed = (TextView) rootView.findViewById(R.id.tvDiscAmountDisplayed);
        tvTotal = (TextView) rootView.findViewById(R.id.tvTotal);
        tvTotalDisplayed = (TextView) rootView.findViewById(R.id.tvTotalDisplayed);

        setTotalsDisplay(false, false);


        //Handles when values are changed and a new charge is to be added
        btnCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvChangeAmount.getText().toString().trim().length() > 0) {
                    Float floatAmount = Float.parseFloat(tvChangeAmount.getText().toString().substring(1, tvChangeAmount.length()));
                    if (floatAmount == 0) {
                        Toast toast = Toast.makeText(getActivity(), "Cannot enter a charge with an amount of $0.00 ",
                                Toast.LENGTH_LONG);
                        toast.show();
                    } else if (etDescription.getText().toString().trim().length() == 0) {
                        Toast toast = Toast.makeText(getActivity(), "Cannot enter a charge with a blank description",
                                Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        enterChargeAsync enter = new enterChargeAsync();
                        enter.execute();
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Cannot enter a charge with an amount of $0.00 ",
                            Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });


        /**
         *  Use a listener to interact with interface on EditDateDialog and EditAmountDialogs. Sends
         *  the value in to be pre set on the dialogs. For the amount dialog, changing to 0 amount
         *  with the default charge type selected cause the app the crash.
         */

        tvChangeAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ChargeCodeSpinner.getSelectedItemPosition() == 0) {
                    Toast toast = Toast.makeText(getActivity(), "Please select a charge type.",
                            Toast.LENGTH_LONG);
                    toast.show();
                } else
                    mListener.onEditAmountDialog(tvChangeAmount.getText().toString().replace("$", ""));
                //mListener.onEditAmountDialog(tvChangeAmount.getText().toString());
            }
        });


        cal = Calendar.getInstance();
        setDate(cal);
        tvDate.setTextSize(25);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateListener.onEditDateDialog(cal);
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    public void setDate(Calendar calinput) {
        cal = calinput;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        chargeDate = dateFormat.format(cal.getTime());
        tvDate.setText(chargeDate);
    }


    class Data {

        static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
        private static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
    }

    //Asycn task to get the ChgDesc field to be used to populate the spinner
    public class getChargeCodesAsync extends
            AsyncTask<Data, Void, ArrayList<ChargeCodes>> {

        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(activity, "Payments", "Loading...", true);
        }

        @Override
        protected ArrayList<ChargeCodes> doInBackground(Data... data) {
            return RetrieveChargeCodesFromSoap(getChargeCodes());

        }

        protected void onPostExecute(ArrayList<ChargeCodes> result) {
            progress.dismiss();
            arrayChargeCodes = result;
            addItemsOnSpinner(arrayChargeCodes);

        }
    }

    public ArrayList<ChargeCodes> getChargeCodes() {
        String strWhere = " where schid = " + _appPrefs.getSchID();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("Where", strWhere);
        params.put("UserID", String.valueOf(_appPrefs.getUserID()));
        params.put("UserGUID", _appPrefs.getUserGUID());
        String url = oGlobals.URLBuilder("getChargeCodes?", params);
        String response = oGlobals.callJSON(url);
        ArrayList<ChargeCodes> chargeCodesArray = new ArrayList<ChargeCodes>();
        try {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            //Sets what the the object will be deserialized too.
            Type collectionType = new TypeToken<ArrayList<ChargeCodes>>() {
            }.getType();
            chargeCodesArray = gson.fromJson(response, collectionType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return chargeCodesArray;


    }

    public static ArrayList<ChargeCodes> RetrieveChargeCodesFromSoap(ArrayList<ChargeCodes> result) {

        ArrayList<ChargeCodes> codes = new ArrayList<ChargeCodes>();
        ChargeCodes defaultCC = new ChargeCodes();
        defaultCC.ChgDesc = "Select Charge Type";
        defaultCC.Kind = "X";
        defaultCC.ChgNo = "0";
        defaultCC.ChgID = 0;
        defaultCC.Amount = 0;
        codes.add(0, defaultCC);
        result.add(0, defaultCC);

        return result;
    }

    //Adds all items from the ChgDesc field to the spinner
    public void addItemsOnSpinner(ArrayList<ChargeCodes> codes) {

        adapterChargeCodes = new ChargeCodeAdapter(activity,
                R.layout.fragment_entercharge, codes);

        ChargeCodeSpinner.setAdapter(adapterChargeCodes);

        ChargeCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectedCharge(ChargeCodeSpinner);

                if (!tvChangeAmount.getText().toString().equals("$0.00")) {
                    runChargeAmountAsync();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * We need this method so that we can run it form onFinishEditAmoutnDialog which is in the parent
     * activity.
     */
    public void runChargeAmountAsync() {
        getChargeAmountAsync ChargeAmount = new getChargeAmountAsync();
        ChargeAmount.execute();
    }

    //Handles if the selected field for the spinner
    public void setSelectedCharge(Spinner spinnerChargeCode) {

        int selected = spinnerChargeCode.getSelectedItemPosition();
        oChargeCodes = (ChargeCodes) spinnerChargeCode.getItemAtPosition(selected);

        NumberFormat nf = NumberFormat.getCurrencyInstance();

        if (oChargeCodes.ChgID == 0) {
            tvChangeAmount.setText("$0.00");
            etDescription.setText("");

        } else if (oChargeCodes.Kind.equals("T") && Integer.parseInt(oChargeCodes.ChgNo) < 4) {

            tvChangeAmount.setText(String.valueOf(nf.format(oAccount.MTuition)));
            etDescription.setText(oChargeCodes.ChgDesc);
        } else {
            tvChangeAmount.setText(String.valueOf(nf.format(oChargeCodes.Amount)));
            etDescription.setText(oChargeCodes.ChgDesc);
        }
    }


    public class getChargeAmountAsync extends
            AsyncTask<Data, Void, ArrayList<Float>> {

        protected void onPreExecute() {
            /**
             * dialog = new ProgressDialog(activity);
             * dialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
             * dialog.setMax(100);
             * dialog.show();
             * */
        }

        @Override
        protected ArrayList<Float> doInBackground(Data... data) {
            ArrayList<Float> arrayListOfAmounts = null;

            float ST1Rate = _appPrefs.getST1Rate();
            float ST2Rate = _appPrefs.getST2Rate();
            float amount = Float.parseFloat(tvChangeAmount.getText().toString().substring(1, tvChangeAmount.length()));
            arrayListOfAmounts = getChargeAmount(_appPrefs.getUserID(), _appPrefs.getUserGUID(), oChargeCodes.ChgID, oAccount.AcctID, oAccount.BillingFreq, amount,
                    oAccount.TuitionSel, oAccount.AccountFeeAmount, ST1Rate, ST2Rate);
            return arrayListOfAmounts;

        }


        protected void onPostExecute(ArrayList<Float> result) {

            NumberFormat format = NumberFormat.getCurrencyInstance();
            floatAmount = Float.parseFloat(tvChangeAmount.getText().toString().substring(1, tvChangeAmount.length()));
            floatDiscAmount = result.get(0);
            floatSTax1 = result.get(1);
            floatSTax2 = result.get(2);

            Boolean discount = false;
            Boolean tax = false;

            if (floatDiscAmount != floatAmount) {
                discount = true;
                tvDiscAmountDisplayed.setText(String.valueOf(format.format(floatDiscAmount)));
            }
            if (floatSTax1 + floatSTax2 > 0) {
                tax = true;
                float total = floatDiscAmount + floatSTax1 + floatSTax2;
                tvTotalDisplayed.setText(String.valueOf(format.format(total)));
            }

            setTotalsDisplay(discount, tax);
        }
    }

    public ArrayList<Float> getChargeAmount(int UserID, String UserGUID, int ChgID, int AcctID, int BillingFreq,
                                            float Amount, int TuitionSel, float AccountFeeAmount, float ST1Rate, float ST2Rate) {


        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("AcctID", String.valueOf(AcctID));
        params.put("UserID", String.valueOf(UserID));
        params.put("UserGUID", UserGUID);
        params.put("ChgID", String.valueOf(ChgID));
        params.put("BillingFreq", String.valueOf(BillingFreq));
        params.put("Amount", String.valueOf(Amount));
        params.put("TuitionSel", String.valueOf(TuitionSel));
        params.put("AccountFeeAmount", String.valueOf(AccountFeeAmount));
        params.put("ST1Rate", String.valueOf(ST1Rate));
        params.put("ST2Rate", String.valueOf(ST2Rate));
        String url = oGlobals.URLBuilder("getChargeAmount?", params);
        String response = oGlobals.callJSON(url);
        ArrayList<Float> result = new ArrayList<Float>();
        try {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            Type collectionType = new TypeToken<ArrayList<Float>>() {
            }.getType();
            result = gson.fromJson(response, collectionType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }


    public class enterChargeAsync extends
            AsyncTask<Data, Void, Boolean> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "Entering Charge", "Loading...", true);
        }

        @Override
        protected Boolean doInBackground(Data... data) {
            Boolean enterCharge = null;
            float totalAmount = 0;
            float DiscAmount = 0;
            User user = _appPrefs.getUser();
            ;

            if (tvDiscAmountDisplayed.isShown()) {
                totalAmount = floatDiscAmount;
                DiscAmount = oChargeCodes.Amount - floatDiscAmount;
            } else if (tvTotalDisplayed.isShown()) {
                totalAmount = floatDiscAmount + floatSTax1 + floatSTax2;
                DiscAmount = 0;
            } else
                totalAmount = oChargeCodes.Amount;


            enterCharge = EnterCharge(userID, userGUID, schID, oAccount.AcctID, chargeDate, oChargeCodes.ChgDesc,
                    oChargeCodes.GLNo, floatDiscAmount, totalAmount, oChargeCodes.Kind, oChargeCodes.Tax,
                    false, 0, oChargeCodes.PayOnline, 0, _appPrefs.getSessionID(), DiscAmount,
                    floatSTax1, floatSTax2, user.DisplayName);
            return enterCharge;


        }


        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            if (result) {
                Toast toast = Toast.makeText(getActivity(), "Charge Successfully entered", Toast.LENGTH_LONG);
                toast.show();
                ChargeCodeSpinner.setSelection(0);
                setTotalsDisplay(false, false);
            } else {
                Toast toast = Toast.makeText(getActivity(), "Charge failed", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public Boolean EnterCharge(int UserID, String UserGUID, int SchID, int AcctID, String ChgDate,
                               String ChgDesc, String GLNo, float Amount, float totalAmount,
                               String Kind, int STax, Boolean POSTrans, int POSInv, Boolean PayOnline,
                               int TransPostHistID, int SessionID, float DiscAmt, float STax1,
                               float STax2, String DisplayName) {


        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("AcctID", String.valueOf(AcctID));
        params.put("UserID", String.valueOf(UserID));
        params.put("UserGUID", UserGUID);
        params.put("SchID", String.valueOf(SchID));
        params.put("StrChgDate", ChgDate);
        params.put("ChgDesc", ChgDesc);
        params.put("GLNo", GLNo);
        params.put("totalAmount", String.valueOf(totalAmount));
        params.put("Amount", String.valueOf(Amount));
        params.put("Kind", Kind);
        params.put("STax", String.valueOf(STax));
        params.put("POSTrans", String.valueOf(0));
        params.put("POSInv", String.valueOf(POSInv));
        params.put("PayOnline", String.valueOf(1));
        params.put("TransPostHistID", String.valueOf(TransPostHistID));
        params.put("SessionID", String.valueOf(SessionID));
        params.put("DiscAmt", String.valueOf(DiscAmt));
        params.put("STax1", String.valueOf(STax1));
        params.put("STax2", String.valueOf(STax2));
        params.put("DisplayName", DisplayName);
        String url = oGlobals.URLBuilder("enterCharge?", params);
        String response = oGlobals.callJSON(url);
        Boolean postedCharge = null;
        try {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            Type collectionType = new TypeToken<ArrayList<Boolean>>() {
            }.getType();
            postedCharge = Boolean.valueOf(response);
            //postedCharge = gson.fromJson(response, collectionType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return postedCharge;
    }

    public void setTotalsDisplay(Boolean discount, Boolean tax) {
        if (discount) {

            tvDiscAmount.setVisibility(View.VISIBLE);
            tvDiscAmountDisplayed.setVisibility(View.VISIBLE);
        } else {
            tvDiscAmount.setVisibility(View.GONE);
            tvDiscAmountDisplayed.setVisibility(View.GONE);
        }

        if (tax) {
            tvTotal.setVisibility(View.VISIBLE);
            tvTotalDisplayed.setVisibility(View.VISIBLE);
        } else {
            tvTotal.setVisibility(View.GONE);
            tvTotalDisplayed.setVisibility(View.GONE);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */


}
