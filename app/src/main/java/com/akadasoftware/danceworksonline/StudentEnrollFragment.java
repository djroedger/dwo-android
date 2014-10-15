package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.akadasoftware.danceworksonline.Adapters.SchoolClassAdapter;
import com.akadasoftware.danceworksonline.Adapters.SessionAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.School;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Session;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Classes.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Kyle on 5/14/2014.
 */
public class StudentEnrollFragment extends ListFragment {

    private AppPreferences _appPrefs;
    String METHOD_NAME = "";
    String SOAP_ACTION = "";
    Activity activity;
    Student oStudent;
    User oUser;
    School oSchool;
    SchoolClasses oSchoolClass;
    Session session;
    int SessionID, position, positionOfClass;
    Globals oGlobals;

    Button btnDone;


    ArrayList<SchoolClasses> schoolClassesArray = new ArrayList<SchoolClasses>();

    ArrayList<String> conflictsArray = new ArrayList<String>();

    ArrayList<Student> studentsArray = new ArrayList<Student>();
    ArrayList<Session> sessionArrayList = new ArrayList<Session>();
    SessionAdapter sessionAdapter;
    ViewGroup objContainer;
    View view;

    Spinner sessionStudentEnrollSpinner;
    private SchoolClassAdapter classAdapter;

    private onEnrollDialog dialogListener;
    //private OnStudentEnrollListener enrollListener;


    public interface onEnrollDialog {

        public void onEnrollDialog(SchoolClasses objSchoolClass, Student oStudent,
                                   ArrayList<String> conflictsArray, int positionOfClass, SchoolClassAdapter classAdapter);
    }

    /**
     * Handles by enrolldialog
     * public interface OnStudentEnrollListener {
     * public void onStudentEnrollInteraction(int classPosition, int newClRID);  }
     */
    public StudentEnrollFragment() {
    }


    /**
     * @param position of student in arraylist
     * @return Returns ClRID, if > 0 then it was a success, otherwise failure
     */
    public static StudentEnrollFragment newInstance(int position) {
        StudentEnrollFragment fragment = new StudentEnrollFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        _appPrefs = new AppPreferences(activity);

        studentsArray = _appPrefs.getStudents();
        position = _appPrefs.getStudentListPosition();

        oSchool = _appPrefs.getSchool();
        oStudent = studentsArray.get(position);
        oUser = _appPrefs.getUser();
        oGlobals = new Globals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container,
                savedInstanceState);
        view = inflater.inflate(R.layout.fragment_studentenroll, container, false);
        objContainer = container;

        btnDone = (Button) view.findViewById(R.id.btnDone);
        sessionStudentEnrollSpinner = (Spinner) view.findViewById(R.id.sessionStudentEnrollSpinner);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openStudentPage = new Intent("com.akadasoftware.danceworksonline.StudentInformation");
                startActivity(openStudentPage);
            }
        });
        return view;
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
            //enrollListener = (OnStudentEnrollListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onStudentEnrollInteraction");
        }

        try {
            dialogListener = (onEnrollDialog) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onEnrollDialog");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //enrollListener = null;
        dialogListener = null;
    }


    /**
     * When one of the Classes is chosen, a dialog will pop up with the option to enroll or waitlist
     * a student based on their current enrollment status. Prior to all of this we check for conflicts
     * in schedule.
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        super.onListItemClick(l, v, position, id);
        oSchoolClass = (SchoolClasses) this.getListAdapter().getItem(position);

        positionOfClass = position;

        checkClassConflicts checkConflicts = new checkClassConflicts();
        checkConflicts.execute();
    }

    //Asycn task to get the ChgDesc field to be used to populate the spinner
    public class getSessionsAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<Session>> {
        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(getActivity(), "Gathering Energy", "Loading...", true);
        }

        @Override
        protected ArrayList<Session> doInBackground(Globals.Data... data) {

            /**
             * SoapObject session = oGlobals.getSoapRequest(Globals.Data.NAMESPACE, "getSessions");
             * session = oGlobals.setSessionPropertyInfo(session, oStudent.SchID, "getSessions", oUser);
             * return oGlobals.RetrieveSessionsFromSoap(session);
             */

            return oGlobals.getSessions(oSchool.SchID, oUser.UserID, oUser.UserGUID);

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
                R.layout.fragment_studentenroll, sess);

        sessionStudentEnrollSpinner.setAdapter(sessionAdapter);
        oGlobals.setCurrentSession(sessionStudentEnrollSpinner, oSchool);
        sessionStudentEnrollSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectedSession(sessionStudentEnrollSpinner);

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


    /**
     * Gets list of Classes, runs in onpost of session to ensure we have a session id
     */
    public class getStudentClassesAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<SchoolClasses>> {

        @Override
        protected ArrayList<SchoolClasses> doInBackground(Globals.Data... data) {


            return oGlobals.getClasses(_appPrefs, SessionID, oStudent.StuID, oUser.StaffID);
        }

        protected void onPostExecute(ArrayList<SchoolClasses> result) {
            schoolClassesArray = result;
            _appPrefs.saveSchoolClassList(schoolClassesArray);

            classAdapter = new SchoolClassAdapter(activity,
                    R.layout.item_studentclass, schoolClassesArray);
            updateListAdapter();


        }
    }


    /**
     * Checking if class that is to be enrolled conflicks with any other previously registered
     * Classes
     */
    public class checkClassConflicts extends AsyncTask<Globals.Data, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Globals.Data... data) {

            return CheckConflicts();
        }

        protected void onPostExecute(ArrayList<String> result) {

            conflictsArray = result;
            dialogListener.onEnrollDialog(oSchoolClass, oStudent, conflictsArray, positionOfClass, classAdapter);

        }
    }

    public ArrayList<String> CheckConflicts() {
        String MethodName = "checkClassEnrollment";
        SoapObject response = InvokeEnrollmentMethod(Globals.Data.URL, MethodName);
        return RetrieveConflictsFromSoap(response);

    }

    public static SoapObject GetSoapObject(String MethodName) {
        return new SoapObject(Globals.Data.NAMESPACE, MethodName);
    }


    public SoapObject InvokeEnrollmentMethod(String URL, String MethodName) {

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

        PropertyInfo piClMax = new PropertyInfo();
        piClMax.setName("intClMax");
        piClMax.setValue(oSchoolClass.ClMax);
        request.addProperty(piClMax);

        PropertyInfo piClCur = new PropertyInfo();
        piClCur.setName("intClCur");
        piClCur.setValue(oSchoolClass.ClCur);
        request.addProperty(piClCur);

        PropertyInfo piClTime = new PropertyInfo();
        piClTime.setName("strClTime");
        piClTime.setValue(oSchoolClass.ClTime);
        request.addProperty(piClTime);

        PropertyInfo piClStop = new PropertyInfo();
        piClStop.setName("strClStop");
        piClStop.setValue(oSchoolClass.ClStop);
        request.addProperty(piClStop);

        PropertyInfo piMultiDay = new PropertyInfo();
        piMultiDay.setName("boolMultiDay");
        piMultiDay.setValue(oSchoolClass.MultiDay);
        request.addProperty(piMultiDay);

        PropertyInfo piMonday = new PropertyInfo();
        piMonday.setName("boolMonday");
        piMonday.setValue(oSchoolClass.Monday);
        request.addProperty(piMonday);

        PropertyInfo piTuesday = new PropertyInfo();
        piTuesday.setName("boolTuesday");
        piTuesday.setValue(oSchoolClass.Tuesday);
        request.addProperty(piTuesday);

        PropertyInfo piWednesday = new PropertyInfo();
        piWednesday.setName("boolWednesday");
        piWednesday.setValue(oSchoolClass.Wednesday);
        request.addProperty(piWednesday);

        PropertyInfo piThursday = new PropertyInfo();
        piThursday.setName("boolThursday");
        piThursday.setValue(oSchoolClass.Thursday);
        request.addProperty(piThursday);

        PropertyInfo piFriday = new PropertyInfo();
        piFriday.setName("boolFriday");
        piFriday.setValue(oSchoolClass.Friday);
        request.addProperty(piFriday);

        PropertyInfo piSaturday = new PropertyInfo();
        piSaturday.setName("boolSaturday");
        piSaturday.setValue(oSchoolClass.Saturday);
        request.addProperty(piSaturday);

        PropertyInfo piSunday = new PropertyInfo();
        piSunday.setName("boolSunday");
        piSunday.setValue(oSchoolClass.Sunday);
        request.addProperty(piSunday);

        PropertyInfo piClDayNo = new PropertyInfo();
        piClDayNo.setName("strClDayNo");
        piClDayNo.setValue(oSchoolClass.ClDayNo);
        request.addProperty(piClDayNo);

        PropertyInfo piStuID = new PropertyInfo();
        piStuID.setName("intStuID");
        piStuID.setValue(oStudent.StuID);
        request.addProperty(piStuID);

        PropertyInfo piClID = new PropertyInfo();
        piClID.setName("intClID");
        piClID.setValue(oSchoolClass.ClID);
        request.addProperty(piClID);

        PropertyInfo piSessionID = new PropertyInfo();
        piSessionID.setName("intSessionID");
        piSessionID.setValue(SessionID);
        request.addProperty(piSessionID);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeConflictCall(URL, envelope, Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapObject MakeConflictCall(String URL,
                                              SoapSerializationEnvelope envelope, String NAMESPACE,
                                              String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject response = null;
        try {
            //envelope.addMapping(Globals.Data.NAMESPACE, "SchoolClasses",new SchoolClasses().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            //envelopeOutput = envelope;
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<String> RetrieveConflictsFromSoap(SoapObject soap) {

        ArrayList<String> strConflictsArray = new ArrayList<String>();

        for (int i = 0; i < soap.getPropertyCount(); i++) {

            //SoapObject conflickItem = (SoapObject) soap.getProperty(i);
            if (soap.getProperty(i).equals("anyType{}"))
                strConflictsArray.add(i, "");
            else
                strConflictsArray.add(i, soap.getProperty(i).toString());

        }

        return strConflictsArray;
    }


    public void updateListAdapter() {
        setListAdapter(classAdapter);

        classAdapter.setNotifyOnChange(true);
    }
}
