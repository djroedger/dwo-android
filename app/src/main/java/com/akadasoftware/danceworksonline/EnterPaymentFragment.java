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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


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
            CCExpire;

    int schID, userID, chgid, acctID, ccRecNo, transPostHistID, sessionID, consentID, ccMerch, accountPosition;

    Boolean POSTrans, saveNewCreditCard;

    Float floAmount, floCCMax;
    private AppPreferences _appPrefs;
    Account oAccount;
    User oUser;
    Activity activity;
    ArrayList<Account> arrayAccounts;


    TextView tvDate, tvTitle, tvType, tvReference, tvDescription, tvAmount, tvChangeAmount;
    EditText etReference, etDescription;
    Button btnPayment;
    Spinner typeSpinner;
    Calendar cal;

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
            CardNumber = " ";
        else {
            for (int j = 4; j > 0; j--) {
                CardNumber += oAccount.CCTrail.charAt(oAccount.CCTrail.length() - j);
            }
        }

        ViewPager mViewPager = (ViewPager) activity.findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {

                refreshEnterPayment();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

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
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String today = dateFormat.format(cal.getTime());
        tvDate.setText(today);
        PDate = today;
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
            SoapObject enterPayment = null;

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

            enterPayment = EnterPayment(userID, userGUID, schID, acctID, PDate, PDesc, ChkNo, floAmount, Kind, CCard, CCDate, CCAuth,
                    ccRecNo, POSTrans, transPostHistID, sessionID, consentID, PaymentID, ProcessData, RefNo,
                    AuthCode, Invoice, AcqRefData, CardHolderName, CCToken, ccuser, ccpass, CardNumber,
                    strUserName, ccMerch, floCCMax, CVV, FName, LName, Address, City, State, Zip, chgid);
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
                Globals gloabal = new Globals();
                gloabal.updateAccount(oAccount, accountPosition, activity);
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
    public SoapObject EnterPayment(int UserID, String UserGUID, int SchID, int AcctID, String PDate,
                                   String PDesc, String ChkNo, float Amount, String Kind, String CCard,
                                   String CCDate, String CCAuth, int CCRecNo, Boolean POSTrans,
                                   int TransPostHistID, int SessionID, int ConsentID, String PaymentID,
                                   String ProcessData, String RefNo, String AuthCode, String Invoice,
                                   String AcqRefData, String CardHolderName, String CCToken, String ccuser,
                                   String ccpass, String CardNumber, String strUserName, int CCMerch, float CCMax, String CVV,
                                   String FName, String LName, String Address, String City, String State, String Zip, int ChgID) {
        SOAP_ACTION = "enterPayment";
        METHOD_NAME = "enterPayment";

        SoapObject requestEnterPayment = new SoapObject(Data.NAMESPACE, METHOD_NAME);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(UserID);
        requestEnterPayment.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(UserGUID);
        requestEnterPayment.addProperty(piUserGUID);

        PropertyInfo piSchID = new PropertyInfo();
        piSchID.setName("SchID");
        piSchID.setValue(SchID);
        requestEnterPayment.addProperty(piSchID);


        PropertyInfo piAcctID = new PropertyInfo();
        piAcctID.setName("AcctID");
        piAcctID.setValue(AcctID);
        requestEnterPayment.addProperty(piAcctID);

        PropertyInfo piPDate = new PropertyInfo();
        piPDate.setName("PDate");
        piPDate.setValue(PDate);
        requestEnterPayment.addProperty(piPDate);

        PropertyInfo piPDesc = new PropertyInfo();
        piPDesc.setName("PDesc");
        piPDesc.setValue(PDesc);
        requestEnterPayment.addProperty(piPDesc);

        PropertyInfo piChkNo = new PropertyInfo();
        piChkNo.setName("ChkNo");
        piChkNo.setValue(ChkNo);
        requestEnterPayment.addProperty(piChkNo);


        PropertyInfo piAmount = new PropertyInfo();
        piAmount.setName("Amount");
        piAmount.setType(Float.class);
        piAmount.setValue(Amount);
        requestEnterPayment.addProperty(piAmount);

        PropertyInfo piKind = new PropertyInfo();
        piKind.setName("Kind");
        piKind.setValue(Kind);
        requestEnterPayment.addProperty(piKind);

        PropertyInfo piCCard = new PropertyInfo();
        piCCard.setName("CCard");
        piCCard.setValue(CCard);
        requestEnterPayment.addProperty(piCCard);

        PropertyInfo piCCDate = new PropertyInfo();
        piCCDate.setName("CCDate");
        piCCDate.setValue(CCExpire);
        requestEnterPayment.addProperty(piCCDate);

        PropertyInfo piCCAuth = new PropertyInfo();
        piCCAuth.setName("CCAuth");
        piCCAuth.setValue("");
        requestEnterPayment.addProperty(piCCAuth);


        PropertyInfo piCCRecNo = new PropertyInfo();
        piCCRecNo.setName("CCRecNo");
        piCCRecNo.setValue(0);
        requestEnterPayment.addProperty(piCCRecNo);

        PropertyInfo piPOSTrans = new PropertyInfo();
        piPOSTrans.setName("POSTrans");
        piPOSTrans.setValue(POSTrans);
        requestEnterPayment.addProperty(piPOSTrans);


        PropertyInfo piTransPostHistID = new PropertyInfo();
        piTransPostHistID.setName("TransPostHistID");
        piTransPostHistID.setValue(0);
        requestEnterPayment.addProperty(piTransPostHistID);

        PropertyInfo piSessionID = new PropertyInfo();
        piSessionID.setName("SessionID");
        piSessionID.setValue(SessionID);
        requestEnterPayment.addProperty(piSessionID);

        PropertyInfo piConsentID = new PropertyInfo();
        piConsentID.setName("ConsentID");
        piConsentID.setValue(ConsentID);
        requestEnterPayment.addProperty(piConsentID);

        PropertyInfo piPaymentID = new PropertyInfo();
        piPaymentID.setName("PaymentID");
        piPaymentID.setValue("");
        requestEnterPayment.addProperty(piPaymentID);


        PropertyInfo piProcessData = new PropertyInfo();
        piProcessData.setName("ProcessData");
        piProcessData.setValue("");
        requestEnterPayment.addProperty(piProcessData);

        PropertyInfo piRefNo = new PropertyInfo();
        piRefNo.setName("RefNo");
        piRefNo.setValue("");
        requestEnterPayment.addProperty(piRefNo);

        PropertyInfo piAuthCode = new PropertyInfo();
        piAuthCode.setName("AuthCode");
        piAuthCode.setValue("");
        requestEnterPayment.addProperty(piAuthCode);

        PropertyInfo piInvoice = new PropertyInfo();
        piInvoice.setName("Invoice");
        piInvoice.setValue("");
        requestEnterPayment.addProperty(piInvoice);


        PropertyInfo piAcqRefData = new PropertyInfo();
        piAcqRefData.setName("AcqRefData");
        piAcqRefData.setValue("");
        requestEnterPayment.addProperty(piAcqRefData);

        PropertyInfo piCardHolderName = new PropertyInfo();
        piCardHolderName.setName("CardHolderName");
        piCardHolderName.setValue("");
        requestEnterPayment.addProperty(piCardHolderName);

        PropertyInfo piCCToken = new PropertyInfo();
        piCCToken.setName("CCToken");
        piCCToken.setValue("");
        requestEnterPayment.addProperty(piCCToken);

        PropertyInfo piCCUser = new PropertyInfo();
        piCCUser.setName("ccuser");
        piCCUser.setValue(ccuser);
        requestEnterPayment.addProperty(piCCUser);

        PropertyInfo piCCPass = new PropertyInfo();
        piCCPass.setName("ccpass");
        piCCPass.setValue(ccpass);
        requestEnterPayment.addProperty(piCCPass);

        PropertyInfo piCardNumber = new PropertyInfo();
        piCardNumber.setName("CardNumber");
        piCardNumber.setValue(CardNumber);
        requestEnterPayment.addProperty(piCardNumber);


        PropertyInfo piStrUserName = new PropertyInfo();
        piStrUserName.setName("strUserName");
        piStrUserName.setValue(strUserName);
        requestEnterPayment.addProperty(piStrUserName);

        PropertyInfo piCCMerch = new PropertyInfo();
        piCCMerch.setName("CCMerch");
        piCCMerch.setValue(CCMerch);
        requestEnterPayment.addProperty(piCCMerch);

        PropertyInfo piCCMaxAmount = new PropertyInfo();
        piCCMaxAmount.setName("CCMaxAmount");
        piCCMaxAmount.setType(Float.class);
        piCCMaxAmount.setValue(CCMax);
        requestEnterPayment.addProperty(piCCMaxAmount);

        PropertyInfo piCVV = new PropertyInfo();
        piCVV.setName("CVV");
        piCVV.setValue(CVV);
        requestEnterPayment.addProperty(piCVV);

        PropertyInfo piFName = new PropertyInfo();
        piFName.setName("FName");
        piFName.setValue(FName);
        requestEnterPayment.addProperty(piFName);


        PropertyInfo piLName = new PropertyInfo();
        piLName.setName("LName");
        piLName.setValue(LName);
        requestEnterPayment.addProperty(piLName);

        PropertyInfo piAddress = new PropertyInfo();
        piAddress.setName("Address");
        piAddress.setValue(Address);
        requestEnterPayment.addProperty(piAddress);

        PropertyInfo piCity = new PropertyInfo();
        piCity.setName("City");
        piCity.setValue(City);
        requestEnterPayment.addProperty(piCity);

        PropertyInfo piState = new PropertyInfo();
        piState.setName("State");
        piState.setValue(State);
        requestEnterPayment.addProperty(piState);

        PropertyInfo piZip = new PropertyInfo();
        piZip.setName("Zip");
        piZip.setValue(Zip);
        requestEnterPayment.addProperty(piZip);

        PropertyInfo piSaveCard = new PropertyInfo();
        piSaveCard.setName("saveCard");
        piSaveCard.setValue(saveNewCreditCard);
        requestEnterPayment.addProperty(piSaveCard);

        PropertyInfo piChgID = new PropertyInfo();
        piChgID.setName("ChgID");
        piChgID.setValue(ChgID);
        requestEnterPayment.addProperty(piChgID);

        SoapSerializationEnvelope envelopePayment = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        MarshalFloat mf = new MarshalFloat();
        mf.register(envelopePayment);

        envelopePayment.dotNet = true;
        envelopePayment.setOutputSoapObject(requestEnterPayment);

        SoapObject responsePayment = null;
        HttpTransportSE HttpTransport = new HttpTransportSE("http://app.akadasoftware.com/MobileAppWebService/Android.asmx");
        try {
            HttpTransport.call(SOAP_ACTION, envelopePayment);

            responsePayment = (SoapObject) envelopePayment.getResponse();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return responsePayment;
    }


    public String[] EnterPaymentFromSoap(SoapObject soap) {


        String[] response = new String[2];
        if (soap.getProperty(0).toString() == "string : Result: Declined - FAILED ERROR CODE: 999 - Invalid Credit Card Number TR: 0") {
            response[0] = "Payment not submitted.";
        } else
            response[0] = soap.getProperty(0).toString();

        if (soap.getProperty(1).toString() == "anyType{}" || soap.getProperty(1).toString().isEmpty()) {
            response[1] = "Card not saved.";
        } else
            response[1] = soap.getProperty(1).toString();

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
