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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * List Fragment that gets the array of student Classes. Also has a spinner that chna
 */
public class StudentClassFragment extends ListFragment {


    static SoapSerializationEnvelope envelopeOutput;
    String METHOD_NAME = "";
    String SOAP_ACTION = "";
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
    int position;
    private AppPreferences _appPrefs;
    private OnStudentClassListener mListener;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private StudentClassAdapter classAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentClassFragment() {
    }

    // TODO: Rename and change types of parameters
    public static StudentClassFragment newInstance(int position) {
        StudentClassFragment fragment = new StudentClassFragment();
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
        void onStudentClassInteraction(String id);
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

    public class getStudentClassesAsync extends
            AsyncTask<Data, Void, ArrayList<StudentClasses>> {

        @Override
        protected ArrayList<StudentClasses> doInBackground(Data... data) {

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("OLReg", String.valueOf(0));
            params.put("StuID", String.valueOf(oStudent.StuID));
            params.put("SessionID", String.valueOf(SessionID));
            params.put("UserID", String.valueOf(oUser.UserID));
            params.put("UserGUID", oUser.UserGUID);
            String url = oGlobal.URLBuilder("getStuClasses?", params);
            String response = oGlobal.callJSON(url);
            ArrayList<StudentClasses> studentClasseArray = new ArrayList<StudentClasses>();
            try {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                //Sets what the the object will be deserialized too.
                Type collectionType = new TypeToken<ArrayList<StudentClasses>>() {
                }.getType();
                studentClasseArray = gson.fromJson(response, collectionType);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return studentClasseArray;

    }

        protected void onPostExecute(ArrayList<StudentClasses> result) {

            studentClassesArray = result;
            classAdapter = new StudentClassAdapter(getActivity(),
                    R.layout.item_studentclass, studentClassesArray);
            setListAdapter(classAdapter);
            classAdapter.setNotifyOnChange(true);

        }
    }


}
