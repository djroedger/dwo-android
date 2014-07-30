package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.akadasoftware.danceworksonline.Adapters.SessionAdapter;
import com.akadasoftware.danceworksonline.Adapters.StudentClassAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.Globals.Data;
import com.akadasoftware.danceworksonline.Classes.School;
import com.akadasoftware.danceworksonline.Classes.Session;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Classes.StudentClasses;
import com.akadasoftware.danceworksonline.Classes.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * List Fragment that gets the array of student Classes. Also has a spinner that chna
 */
public class StudentClassFragment extends ListFragment {


    private AppPreferences _appPrefs;
    String METHOD_NAME = "";
    String SOAP_ACTION = "";
    static SoapSerializationEnvelope envelopeOutput;
    Activity activity;
    Student oStudent;
    User oUser;
    School oSchool;
    Session session;
    int SessionID;
    Globals oGlobal;

    ArrayList<Session> sessionArrayList = new ArrayList<Session>();
    SessionAdapter sessionAdapter;

    ArrayList<StudentClasses> studentClassesArray = new ArrayList<StudentClasses>();
    ArrayList<Student> Students = new ArrayList<Student>();


    Spinner sessionStudentClassesSpinner;
    private OnStudentClassListener mListener;

    int position;


    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private StudentClassAdapter classAdapter;

    // TODO: Rename and change types of parameters
    public static StudentClassFragment newInstance(int position) {
        StudentClassFragment fragment = new StudentClassFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentClassFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        _appPrefs = new AppPreferences(activity);

        Students = _appPrefs.getStudents();
        position = getArguments().getInt("Position");

        oStudent = Students.get(position);
        oUser = _appPrefs.getUser();
        oSchool = _appPrefs.getSchool();
        oGlobal = new Globals();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container,
                savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_studentclass_list, container, false);

        sessionStudentClassesSpinner = (Spinner) view.findViewById(R.id.sessionStudentClassesSpinner);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        getSessionsAsync getSessions = new getSessionsAsync();
        getSessions.execute();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnStudentClassListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onStudentClassInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnStudentClassListener {
        // TODO: Update argument type and name
        public void onStudentClassInteraction(String id);
    }

    //Asycn task to get the ChgDesc field to be used to populate the spinner
    public class getSessionsAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<Session>> {

        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), "Getting stuff from da interwebs", "Loading...", true);
        }

        @Override
        protected ArrayList<Session> doInBackground(Globals.Data... data) {

            return oGlobal.getSessions(oSchool.SchID, oUser.UserID, oUser.UserGUID);


        }

        protected void onPostExecute(ArrayList<Session> result) {
            progress.dismiss();
            sessionArrayList = result;
            addItemsOnSpinner(sessionArrayList);

        }
    }


    //Adds all items from the Session field to the spinner
    public void addItemsOnSpinner(ArrayList<Session> sess) {

        sessionAdapter = new SessionAdapter(activity,
                R.layout.fragment_studentclass_list, sess);

        sessionStudentClassesSpinner.setAdapter(sessionAdapter);
        oGlobal.setCurrentSession(sessionStudentClassesSpinner, oSchool);

        sessionStudentClassesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectedSession(sessionStudentClassesSpinner);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //Handles if the selected field for the spinner
    public void setSelectedSession(Spinner sessionSpinner) {

        int selected = sessionSpinner.getSelectedItemPosition();
        session = (Session) sessionSpinner.getItemAtPosition(selected);
        SessionID = session.SessionID;

        getStudentClassesAsync getStudentClass = new getStudentClassesAsync();
        getStudentClass.execute();
    }

    public class getStudentClassesAsync extends
            AsyncTask<Data, Void, ArrayList<StudentClasses>> {

        @Override
        protected ArrayList<StudentClasses> doInBackground(Data... data) {

            return getClasses();
        }

        protected void onPostExecute(ArrayList<StudentClasses> result) {

            studentClassesArray = result;
            classAdapter = new StudentClassAdapter(getActivity(),
                    R.layout.item_studentclass, studentClassesArray);
            setListAdapter(classAdapter);
            classAdapter.setNotifyOnChange(true);

        }
    }

    public ArrayList<StudentClasses> getClasses() {
        String MethodName = "getStuClasses";
        SoapObject response = InvokeMethod(Data.URL, MethodName);
        return RetrieveFromSoap(response);

    }

    public SoapObject InvokeMethod(String URL, String MethodName) {

        SoapObject request = GetSoapObject(MethodName);


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

        PropertyInfo piOLReg = new PropertyInfo();
        piOLReg.setName("OLReg");
        piOLReg.setValue(false);
        request.addProperty(piOLReg);

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
        SoapObject response = null;
        try {
            envelope.addMapping(Data.NAMESPACE, "StudentClasses",
                    new StudentClasses().getClass());
            HttpTransport.call("getStuClasses", envelope);
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }

    public static ArrayList<StudentClasses> RetrieveFromSoap(SoapObject soap) {

        ArrayList<StudentClasses> stuClassesArray = new ArrayList<StudentClasses>();
        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject classItem = (SoapObject) soap.getProperty(i);

            StudentClasses classes = new StudentClasses();
            for (int j = 0; j < classItem.getPropertyCount(); j++) {
                classes.setProperty(j, classItem.getProperty(j)
                        .toString());
                if (classItem.getProperty(j).equals("anyType{}")) {
                    classItem.setProperty(j, "");
                }

            }
            stuClassesArray.add(i, classes);
        }

        return stuClassesArray;
    }


}
