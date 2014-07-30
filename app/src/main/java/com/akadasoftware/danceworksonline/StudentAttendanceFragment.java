package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.akadasoftware.danceworksonline.Adapters.SessionAdapter;
import com.akadasoftware.danceworksonline.Adapters.StudentAttendanceAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.Globals.Data;
import com.akadasoftware.danceworksonline.Classes.School;
import com.akadasoftware.danceworksonline.Classes.Session;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Classes.StudentAttendance;
import com.akadasoftware.danceworksonline.Classes.User;

import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;


/**
 *
 *
 */
public class StudentAttendanceFragment extends ListFragment {

    Activity activity;
    private AppPreferences _appPrefs;
    Globals oGlobal;
    User oUser;
    static SoapSerializationEnvelope envelopeOutput;
    String METHOD_NAME = "";
    String SOAP_ACTION = "";
    Student oStudent;
    Session oSession;
    School oSchool;


    ArrayList<Session> sessionArrayList = new ArrayList<Session>();
    SessionAdapter sessionAdapter;

    ArrayList<StudentAttendance> studentAttenanceArray = new ArrayList<StudentAttendance>();
    private StudentAttendanceAdapter attendanceAdapter;

    ArrayList<Student> students = new ArrayList<Student>();

    private OnAttendanceInteractionListener mListener;

    int position, SessionID;

    Spinner sessionAttendanceSpinner;


    public static StudentAttendanceFragment newInstance(int position) {
        StudentAttendanceFragment fragment = new StudentAttendanceFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);

        fragment.setArguments(args);
        return fragment;
    }

    public StudentAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        _appPrefs = new AppPreferences(activity);


        students = _appPrefs.getStudents();
        position = getArguments().getInt("Position");

        oStudent = students.get(position);
        oUser = _appPrefs.getUser();
        oSchool = _appPrefs.getSchool();
        oGlobal = new Globals();
    }

    @Override
    public void onResume() {
        super.onResume();


        getSessionsAsync getSessions = new getSessionsAsync();
        getSessions.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_attendance, container, false);

        sessionAttendanceSpinner = (Spinner) rootView.findViewById(R.id.sessionAttendanceSpinner);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAttendanceInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAttendanceInteractionListener");
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
    public interface OnAttendanceInteractionListener {
        // TODO: Update argument type and name
        public void onAttendanceInteraction(Uri uri);
    }

    //Asycn task to get the ChgDesc field to be used to populate the spinner
    public class getSessionsAsync extends
            AsyncTask<Data, Void, ArrayList<Session>> {

        @Override
        protected ArrayList<Session> doInBackground(Data... data) {

            return oGlobal.getSessions(oSchool.SchID, oUser.UserID, oUser.UserGUID);


        }

        protected void onPostExecute(ArrayList<Session> result) {
            sessionArrayList = result;
            addItemsOnSpinner(sessionArrayList);

        }
    }


    //Adds all items from the Session field to the spinner
    public void addItemsOnSpinner(ArrayList<Session> sess) {

        sessionAdapter = new SessionAdapter(activity,
                R.layout.fragment_student_attendance, sess);

        sessionAttendanceSpinner.setAdapter(sessionAdapter);
        oGlobal.setCurrentSession(sessionAttendanceSpinner, oSchool);
        sessionAttendanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectedSession(sessionAttendanceSpinner);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //Handles if the selected field for the spinner
    public void setSelectedSession(Spinner sessionSpinner) {

        int selected = sessionSpinner.getSelectedItemPosition();
        oSession = (Session) sessionSpinner.getItemAtPosition(selected);
        SessionID = oSession.SessionID;

        getStudentAttendanceAsync getStudentAttendance = new getStudentAttendanceAsync();
        getStudentAttendance.execute();
    }

    /**
     * Get's the Student's attendance record after the the session is choosen.
     */
    public class getStudentAttendanceAsync extends
            AsyncTask<Data, Void, ArrayList<StudentAttendance>> {
        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(activity, "Getting attendance", "Loading...", true);
        }

        @Override
        protected ArrayList<StudentAttendance> doInBackground(Data... data) {

            //return getAttendance();
            return oGlobal.getAttendance(_appPrefs, oStudent.StuID);
        }

        protected void onPostExecute(ArrayList<StudentAttendance> result) {
            progress.dismiss();
            studentAttenanceArray = result;
            attendanceAdapter = new StudentAttendanceAdapter(activity,
                    R.layout.item_student_attendance, studentAttenanceArray);
            setListAdapter(attendanceAdapter);
            attendanceAdapter.setNotifyOnChange(true);


        }
    }

}
