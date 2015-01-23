package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.akadasoftware.danceworksonline.Adapters.AccountStudentsAdapter;
import com.akadasoftware.danceworksonline.Classes.Account;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 */
public class AccountStudentsFragment extends ListFragment implements AbsListView.OnItemClickListener {


    ArrayList<Student> studentsArray = new ArrayList<Student>();
    ArrayList<Account> accountsArray;
    private AppPreferences _appPrefs;
    private OnStudentFragmentInteractionListener mListener;
    private AccountStudentsAdapter stuAdapter;
    static SoapSerializationEnvelope envelopeOutput;
    String METHOD_NAME = "";
    static String SOAP_ACTION = "getStudents";
    Activity activity;
    Account oAccount;
    Globals oGlobals;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static AccountStudentsFragment newInstance(int position) {
        AccountStudentsFragment fragment = new AccountStudentsFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AccountStudentsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activity = getActivity();
        _appPrefs = new AppPreferences(activity);

        accountsArray = _appPrefs.getAccounts();
        int position = getArguments().getInt("Position");
        oAccount = accountsArray.get(position);
        oGlobals = new Globals();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container,
                savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_accountstudents_list, container, false);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setListAdapter(null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnStudentFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStudentFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        getAccountStudentsAsync getStudents = new getAccountStudentsAsync();
        getStudents.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    class Data {

        private static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
        private static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
    }

    /**
     * Gets list of students
     */
    private class getAccountStudentsAsync extends
            AsyncTask<Data, Void, ArrayList<Student>> {
        ProgressDialog progress;


        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), "Getting stuff from da interwebs", "Loading...", true);
        }

        @Override
        protected ArrayList<Student> doInBackground(Data... data) {

            String strOrder = " Order by FNAME,LNAME";
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("Order", strOrder);
            params.put("SchID", String.valueOf(_appPrefs.getSchID()));
            params.put("AcctID", String.valueOf(_appPrefs.getAcctID()));
            params.put("UserID", String.valueOf(_appPrefs.getUserID()));
            params.put("UserGUID", _appPrefs.getUserGUID());
            String url = oGlobals.URLBuilder("getStudents?", params);
            String response = oGlobals.callJSON(url);
            ArrayList<Student> StudentsArray = new ArrayList<Student>();
            try {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                //Sets what the the object will be deserialized too.
                Type collectionType = new TypeToken<ArrayList<Student>>() {
                }.getType();
                StudentsArray = gson.fromJson(response, collectionType);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return StudentsArray;
            //return oGlobals.getStudents(_appPrefs, _appPrefs.getAcctID());
        }

        protected void onPostExecute(ArrayList<Student> result) {
            progress.dismiss();
            studentsArray = result;
            stuAdapter = new AccountStudentsAdapter(activity,
                    R.layout.item_accountstudents, studentsArray);

            setListAdapter(stuAdapter);
            stuAdapter.setNotifyOnChange(true);


        }
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
    public interface OnStudentFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onStudentFragmentInteraction(String id);
    }

}
