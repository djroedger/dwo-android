package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Adapters.SessionAdapter;
import com.akadasoftware.danceworksonline.Adapters.StudentWaitListAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.School;
import com.akadasoftware.danceworksonline.Classes.Session;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Classes.StudentWaitList;
import com.akadasoftware.danceworksonline.Classes.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;


public class StudentWaitListFragment extends ListFragment {

    static SoapSerializationEnvelope envelope;
    String METHOD_NAME = "";
    String SOAP_ACTION = "";
    int position, SessionID;
    Activity activity;
    Student oStudent;
    User oUser;
    Globals oGlobal;
    School oSchool;
    ArrayList<Session> sessionArrayList = new ArrayList<Session>();
    SessionAdapter sessionAdapter;
    ArrayList<Student> Students = new ArrayList<Student>();
    Spinner sessionWaitListSpinner;
    private AppPreferences _appPrefs;
    private OnWaitListListener mListener;
    private StudentWaitListAdapter waitListAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentWaitListFragment() {
    }

    // TODO: Rename and change types of parameters
    public static StudentWaitListFragment newInstance(int position) {
        StudentWaitListFragment fragment = new StudentWaitListFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public static SoapObject MakeCall(String URL, SoapSerializationEnvelope envelope, String NAMESPACE,
                                      String METHOD_NAME, String SOAP_ACTION) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        try {
            envelope.addMapping(NAMESPACE, SOAP_ACTION,
                    new StudentWaitList().getClass());

            HttpTransport.call(METHOD_NAME, envelope);

            SoapObject response = (SoapObject) envelope.getResponse();

            return response;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static ArrayList<StudentWaitList> RetrieveWaitListFromSoap(SoapObject soap) {

        ArrayList<StudentWaitList> studentWaitList = new ArrayList<StudentWaitList>();
        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject classItem = (SoapObject) soap.getProperty(i);

            StudentWaitList list = new StudentWaitList();
            for (int j = 0; j < classItem.getPropertyCount(); j++) {
                list.setProperty(j, classItem.getProperty(j)
                        .toString());
                if (classItem.getProperty(j).equals("anyType{}")) {
                    classItem.setProperty(j, "");
                }

            }
            studentWaitList.add(i, list);
        }

        return studentWaitList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        _appPrefs = new AppPreferences(activity);

        oGlobal = new Globals();

        Students = _appPrefs.getStudents();
        position = getArguments().getInt("Position");

        oStudent = Students.get(position);
        oUser = _appPrefs.getUser();
        oSchool = _appPrefs.getSchool();
    }

    @Override
    public void onResume() {
        super.onResume();

        getSessionsAsync getSession = new getSessionsAsync();
        getSession.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container,
                savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_studentwaitlist_list, container, false);

        sessionWaitListSpinner = (Spinner) view.findViewById(R.id.sessionWaitListSpinner);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnWaitListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnWaitListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        ListView listView = (ListView) activity.findViewById(R.id.list);
        View emptyView = listView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    //Adds all items from the Session field to the spinner
    public void addItemsOnSpinner(ArrayList<Session> sess) {

        sessionAdapter = new SessionAdapter(activity,
                R.layout.fragment_studentwaitlist_list, sess);

        sessionWaitListSpinner.setAdapter(sessionAdapter);
        oGlobal.setCurrentSession(sessionWaitListSpinner, oSchool);

        sessionWaitListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectedSession(sessionWaitListSpinner);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //Handles if the selected field for the spinner
    public void setSelectedSession(Spinner sessionSpinner) {

        int selected = sessionSpinner.getSelectedItemPosition();
        Session session = (Session) sessionSpinner.getItemAtPosition(selected);
        SessionID = session.SessionID;


        getStudentWaitListAsync studentWaitList = new getStudentWaitListAsync();
        studentWaitList.execute();
    }

    public ArrayList<StudentWaitList> getWaitList() {
        METHOD_NAME = "getStudentWaitList";
        SOAP_ACTION = "StudentWaitList";
        SoapObject response = InvokeMethod(Data.URL, METHOD_NAME);
        return RetrieveWaitListFromSoap(response);

    }

    public SoapObject InvokeMethod(String URL, String MethodName) {

        SoapObject request = Globals.GetSoapObject(Data.NAMESPACE, MethodName);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setType("STRING_CLASS");
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);


        PropertyInfo piStuID = new PropertyInfo();
        piStuID.setName("StuID");
        piStuID.setValue(oStudent.StuID);
        request.addProperty(piStuID);

        PropertyInfo piSessionID = new PropertyInfo();
        piSessionID.setName("SessionID");
        piSessionID.setValue(SessionID);
        request.addProperty(piSessionID);

        envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeCall(URL, envelope, Data.NAMESPACE, METHOD_NAME, SOAP_ACTION);
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
    public interface OnWaitListListener {
        // TODO: Update argument type and name
        void onWaitListInteraction(String id);
    }

    class Data {

        private static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
        private static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
    }

    //Asycn task to get the ChgDesc field to be used to populate the spinner
    public class getSessionsAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<Session>> {

        @Override
        protected ArrayList<Session> doInBackground(Globals.Data... data) {

            return oGlobal.getSessions(oSchool.SchID, oUser.UserID, oUser.UserGUID);


        }

        protected void onPostExecute(ArrayList<Session> result) {
            sessionArrayList = result;
            addItemsOnSpinner(sessionArrayList);

        }
    }

    public class getStudentWaitListAsync extends
            AsyncTask<Data, Void, ArrayList<StudentWaitList>> {

        @Override
        protected ArrayList<StudentWaitList> doInBackground(Data... data) {

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("StuID", String.valueOf(oStudent.StuID));
            params.put("SessionID", String.valueOf(SessionID));
            params.put("UserID", String.valueOf(oUser.UserID));
            params.put("UserGUID", oUser.UserGUID);
            String url = oGlobal.URLBuilder("getStuClasses?", params);
            String response = oGlobal.callJSON(url);
            ArrayList<StudentWaitList> studentWaitListArray = new ArrayList<StudentWaitList>();
            try {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                //Sets what the the object will be deserialized too.
                Type collectionType = new TypeToken<ArrayList<StudentWaitList>>() {
                }.getType();
                studentWaitListArray = gson.fromJson(response, collectionType);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return studentWaitListArray;
        }


        protected void onPostExecute(ArrayList<StudentWaitList> result) {

            waitListAdapter = new StudentWaitListAdapter(activity,
                    R.layout.item_studentwaitlist, result);
            setListAdapter(waitListAdapter);
            waitListAdapter.setNotifyOnChange(true);

        }

    }


}
