package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akadasoftware.danceworksonline.Classes.Account;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.School;
import com.akadasoftware.danceworksonline.Classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * Enterpayment Fragment
 * Here the user can enter payment that will be saved to the web service. The user can use a saved
 * credit card on file or enter a new credit card on file and have it be saved to their account. The
 * switch case is where we handle the payment methods. Several different listeners are used to
 * handle the different dialogs there are.. Calendar Dialog, Amount Dialog, and Credit Card Dialog.
 */
public class EnterPaymentFragment extends Fragment {


    String SOAP_ACTION, METHOD_NAME, userGUID, PDate, PDesc, ChkNo, Kind, CCard, CCDate, CCAuth,
            PaymentID, ProcessData, RefNo, AuthCode, Invoice, AcqRefData, CardHolderName, CCToken,
            ccuser, ccpass, CardNumber, strUserName, CVV, FName, LName, Address, City, State, Zip,
            CCExpire, paymentDate;

    int schID, userID, chgid, acctID, ccRecNo, transPostHistID, sessionID, consentID, ccMerch, accountPosition;

    Boolean POSTrans, saveNewCreditCard;

    Float floAmount, floCCMax;
    private AppPreferences _appPrefs;
    Account oAccount;
    User oUser;
    Activity activity;
    ArrayList<Account> arrayAccounts;
    Globals oGlobals;

    TextView tvDate, tvTitle, tvType, tvReference, tvDescription, tvAmount, tvChangeAmount;
    EditText etReference, etDescription;
    Button btnPayment;
    Spinner typeSpinner;
    Calendar cal;

    ViewPager mViewPager;

    // Listeners for the interface used to handle the dialog pop-ups
    private onEditAmountDialog mListener;
    private onEditDateDialog dateListener;
    private onEditCreditCardDialog ccListener;


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

    public interface onEditCreditCardDialog {
        // TODO: Update argument type and name
        public void onEditCreditCardDialog(int accountPosition);
    }

    /**
     * I added this
     * Boogity Boo
     */

    public static EnterPaymentFragment newInstance(int position, String description, Float amount) {
        EnterPaymentFragment fragment = new EnterPaymentFragment();
        Bundle args = new Bundle();
        args.putFloat("Amount", amount);
        args.putString("Description", description);
        args.putInt("Position", position);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EnterPaymentFragment.
     */
    public EnterPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        _appPrefs = new AppPreferences(activity);

        arrayAccounts = _appPrefs.getAccounts();
        accountPosition = getArguments().getInt("Position");

        oAccount = arrayAccounts.get(accountPosition);

        saveNewCreditCard = false;
        if (oAccount.CCTrail.equals(""))
            CardNumber = "";
        else {
            for (int j = 4; j > 0; j--) {
                CardNumber += oAccount.CCTrail.charAt(oAccount.CCTrail.length() - j);
            }
        }

        oGlobals = new Globals();

    }

    public void refreshEnterPayment() {
        chgid = 0;
        etDescription.setText("Payment");
        tvChangeAmount.setText("$0.00");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_enterpayment, container, false);

        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        tvType = (TextView) rootView.findViewById(R.id.tvType);
        tvReference = (TextView) rootView.findViewById(R.id.tvReference);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        tvAmount = (TextView) rootView.findViewById(R.id.tvAmount);
        tvChangeAmount = (TextView) rootView.findViewById(R.id.tvChangeAmount);
        etReference = (EditText) rootView.findViewById(R.id.etReference);
        etDescription = (EditText) rootView.findViewById(R.id.etDescription);
        typeSpinner = (Spinner) rootView.findViewById(R.id.typeSpinner);
        btnPayment = (Button) rootView.findViewById(R.id.btnPayment);


        tvChangeAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEditAmountDialog(tvChangeAmount.getText().toString().replace("$", ""));
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


        /**
         * Button click to submit payment to database. Makes sure there are no 0 payments and there
         * description field is not blank.
         */
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeSpinner.getSelectedItemPosition() == 0) {
                    Toast toast = Toast.makeText(getActivity(), "Please select a charge type.",
                            Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (tvChangeAmount.getText().toString().trim().length() > 0) {
                        Float floatAmount = Float.parseFloat(tvChangeAmount.getText().toString().replace("$", ""));
                        //Float floatAmount = Float.parseFloat(tvChangeAmount.getText().toString().substring(1, tvChangeAmount.length()));
                        if (floatAmount == 0) {
                            Toast toast = Toast.makeText(getActivity(), "Cannot enter a payment with an amount of $0.00 ",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (etDescription.getText().toString().trim().length() == 0) {
                            Toast toast = Toast.makeText(getActivity(), "Cannot enter a payment with a blank description",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            enterPaymentAsync enter = new enterPaymentAsync();
                            enter.execute();
                        }
                    } else {
                        Toast toast = Toast.makeText(getActivity(), "Cannot enter a payment with an amount of $0.00 ",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }

                }

            }
        });

        /**
         * Creating a list of resources to populate the spinner with. Because the list is pre-determined
         * I created a resource of string array and put them in there and created a simple array
         * adapter to populate the list with.
         */
        ArrayAdapter<CharSequence> paymentType = null;
        if (_appPrefs.getCCProcessor().equals("PPAY")) {
            if (oAccount.CCConsentID > 0) {
                //If they have credit card processing and a credit card on file
                paymentType = ArrayAdapter.createFromResource(activity,
                        R.array.payment_types3, android.R.layout.simple_spinner_item);
            } else {
                //If they have credit card processing but no card on file
                paymentType = ArrayAdapter.createFromResource(activity,
                        R.array.payment_types2, android.R.layout.simple_spinner_item);
            }

        } else {
            //If credit card processing is not enabled
            paymentType = ArrayAdapter.createFromResource(activity,
                    R.array.payment_types1, android.R.layout.simple_spinner_item);

        }

        // Specify the layout to use when the list of choices appears
        paymentType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        typeSpinner.setAdapter(paymentType);


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                CardNumber = "";
                CCExpire = "";
                CCard = "";
                CVV = "";
                consentID = 0;
                int position = typeSpinner.getSelectedItemPosition();
                switch (position) {
                    case 1:
                        etReference.setText("Cash");
                        ChkNo = "Cash";
                        Kind = "$";
                        break;
                    case 2:
                        etReference.setText("Chk");
                        ChkNo = "Chk";
                        Kind = "C";
                        break;
                    case 3:
                        etReference.setText("Other");
                        ChkNo = "Other";
                        Kind = "O";
                        break;
                    case 4:
                        ccListener.onEditCreditCardDialog(accountPosition);
                        break;
                    case 5:
                        switch (oAccount.CCType) {
                            case 1:
                                etReference.setText("AmEx");
                                ChkNo = "AmEx";
                                Kind = "A";
                                break;
                            case 2:
                                etReference.setText("Discover");
                                ChkNo = "Discover";
                                Kind = "D";
                                break;
                            case 3:
                                etReference.setText("MC");
                                ChkNo = "MC";
                                Kind = "M";
                                break;
                            case 4:
                                etReference.setText("Visa");
                                ChkNo = "Visa";
                                Kind = "V";
                                break;
                        }
                        CCard = oAccount.CCTrail;
                        CCExpire = String.valueOf(oAccount.CCExpire);
                        consentID = oAccount.CCConsentID;
                        break;
                    default:
                        etReference.getText().clear();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Returning the populated layout for this fragment
        return rootView;
    }

    public void setDate(Calendar calinput) {
        cal = calinput;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        paymentDate = dateFormat.format(cal.getTime());
        tvDate.setText(paymentDate);
        PDate = paymentDate;
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
        try {
            ccListener = (onEditCreditCardDialog) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEditCreditCardDialog");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        dateListener = null;
        mViewPager = null;
    }


    class Data {

        static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
        static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
    }


    public class enterPaymentAsync extends AsyncTask<Data, Void, String[]> {
        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(activity, "Charges", "Loading...", true);
        }

        public String[] doInBackground(Data... data) {
            ArrayList<String> enterPayment = null;

            School school = _appPrefs.getSchool();
            oUser = _appPrefs.getUser();
            userID = oUser.UserID;
            userGUID = oUser.UserGUID;
            strUserName = oUser.UserName;
            schID = oAccount.SchID;
            acctID = oAccount.AcctID;
            PDesc = etDescription.getText().toString();

            floAmount = Float.valueOf(tvChangeAmount.getText().toString().replace("$", ""));
            FName = oAccount.CCFName;
            LName = oAccount.CCLName;
            Address = oAccount.CCAddress;
            City = oAccount.CCCity;
            State = oAccount.CCState;
            Zip = oAccount.CCZip;
            POSTrans = false;
            sessionID = school.SessionID;
            if (typeSpinner.getSelectedItemPosition() == 5) {
                ccuser = school.CCUserName;
                ccpass = school.CCPassword;
            }

            ccMerch = school.CCMerchantNo;
            floCCMax = school.CCMaxAmt;
            chgid = _appPrefs.getChgID();

            enterPayment = EnterPayment(userID, userGUID, schID, acctID, PDate, PDesc, ChkNo, floAmount, Kind, CCard, CCDate,
                    sessionID, consentID, ccuser, ccpass, CardNumber, strUserName, ccMerch, floCCMax,
                    CVV, FName, LName, Address, City, State, Zip, chgid);
            return EnterPaymentFromSoap(enterPayment);
        }

        protected void onPostExecute(String result[]) {
            progress.dismiss();
            CardNumber = "";
            CCExpire = "";
            CCard = "";
            CVV = "";
            consentID = 0;
            etReference.getText().clear();
            etDescription.getText().clear();
            tvChangeAmount.setText("0.00");
            typeSpinner.setSelection(0);

            Toast toast = Toast.makeText(getActivity(), result[0] + " " + result[1], Toast.LENGTH_LONG);
            toast.show();

            if (result[1].length() > 0) {
                oGlobals.updateAccount(oAccount, accountPosition, activity);
            }
        }
    }

    /**
     * Some of the variables aren't being used because they end up being 0/"" anyway so i just set
     * that manually in the property info side to save time. I just left them in there so it'd match
     * our webmethod. They can easily be removed later.
     *
     * @return Returns a soap object which is basically a success or fail message.
     */
    public ArrayList<String> EnterPayment(int UserID, String UserGUID, int SchID, int AcctID, String PDate,
                                   String PDesc, String ChkNo, float Amount, String Kind, String CCard,
                                   String CCDate, int SessionID, int ConsentID, String ccuser,
                                   String ccpass, String CardNumber, String strUserName, int CCMerch, float CCMax, String CVV,
                                   String FName, String LName, String Address, String City, String State, String Zip, int ChgID) {

        int intSaveNewCard = 0;
        if (saveNewCreditCard)
            intSaveNewCard = 1;
        else
            intSaveNewCard = 0;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("AcctID", String.valueOf(AcctID));
        params.put("UserID", String.valueOf(UserID));
        params.put("UserGUID", UserGUID);
        params.put("SchID", String.valueOf(SchID));
        params.put("PDate", PDate);
        params.put("PDesc", PDesc);
        params.put("ChkNo", ChkNo);
        params.put("Amount", String.valueOf(Amount));
        params.put("Kind", Kind);
        params.put("CCard", CCard);
        params.put("CCDate", CCExpire);
        params.put("SessionID", String.valueOf(SessionID));
        params.put("ConsentID", String.valueOf(ConsentID));
        params.put("ccuser", ccuser);
        params.put("ccpass", ccpass);
        params.put("CardNumber", CardNumber);
        params.put("strUserName", strUserName);
        params.put("CCMerch", String.valueOf(CCMerch));
        params.put("CCMaxAmount", String.valueOf(CCMax));
        params.put("CVV", CVV);
        params.put("LName", LName);
        params.put("FName", FName);
        params.put("Address", Address);
        params.put("City", City);
        params.put("State", State);
        params.put("Zip", Zip);
        params.put("saveCard", String.valueOf(intSaveNewCard));
        params.put("ChgID", String.valueOf(ChgID));

        String url = oGlobals.URLBuilder("enterPayment?", params);
        String response = oGlobals.callJSON(url);
        ArrayList<String> result = new ArrayList<String>();
        try {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            Type collectionType = new TypeToken<ArrayList<String>>() {
            }.getType();
            result = gson.fromJson(response, collectionType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;


    }


    public String[] EnterPaymentFromSoap(ArrayList<String> stringResponse) {


        String[] response = new String[2];
        if (stringResponse.get(0).toString() == "string : Result: Declined - FAILED ERROR CODE: 999 - Invalid Credit Card Number TR: 0") {
            response[0] = "Payment not submitted.";
        } else {
            response[0] = stringResponse.get(0).toString();
            return response;

        }
        if (stringResponse.get(1).toString() == "anyType{}" || stringResponse.get(1).toString().isEmpty()) {
            response[1] = "Card not saved.";
        } else
            response[1] = stringResponse.get(1).toString();

        return response;
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
}
