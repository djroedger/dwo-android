package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Adapters.ClassWaitListAdapter;
import com.akadasoftware.danceworksonline.Adapters.SchoolClassAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.ClassWaitList;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Classes.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Kyle on 7/21/2014.
 */
public class ClassWaitListFragment extends ListFragment {

    Activity activity;
    Globals oGlobals;
    User oUser;
    SchoolClasses oSchoolClass;
    Student oStudent;
    ArrayList<SchoolClasses> schoolClassList;

    ArrayList<ClassWaitList> classWaitArray;

    ArrayList<Student> studentArray;

    ArrayList<String> conflictsArray = new ArrayList<String>();
    private AppPreferences _appPrefs;

    private ClassWaitListAdapter classAdapter;
    private SchoolClassAdapter schoolClassAdapter;


    int position;

    private onEnrollDialog eListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public static ClassWaitListFragment newInstance(int position) {
        ClassWaitListFragment fragment = new ClassWaitListFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);

        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClassWaitListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();


        oGlobals = new Globals();
        _appPrefs = new AppPreferences(activity);
        oUser = _appPrefs.getUser();
        position = getArguments().getInt("Position");
        studentArray = _appPrefs.getStudents();
        schoolClassList = _appPrefs.getSchoolClassList();
        oSchoolClass = schoolClassList.get(position);

        getClassWaitListAsync getClass = new getClassWaitListAsync();
        getClass.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container,
                savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_classroster_list, container, false);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            eListener = (onEnrollDialog) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onWaitListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        eListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        for (int i = 0; i < studentArray.size(); i++) {
            if (studentArray.get(i).StuID == classWaitArray.get(position).StuID) {
                oStudent = studentArray.get(i);
            }
        }

        checkClassConflicts conflictCheck = new checkClassConflicts();
        conflictCheck.execute();

        eListener.onEnrollDialog(oSchoolClass, oStudent, conflictsArray, position, schoolClassAdapter);
    }


    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText("No Students on WaitList");
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


    public interface onEnrollDialog {

        public void onEnrollDialog(SchoolClasses objSchoolClass, Student oStudent,
                                   ArrayList<String> conflictsArray, int positionOfClass, SchoolClassAdapter classAdapter);
    }

    public class getClassWaitListAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<ClassWaitList>> {

        @Override
        protected ArrayList<ClassWaitList> doInBackground(Globals.Data... data) {

            return getWaitList();
        }

        protected void onPostExecute(ArrayList<ClassWaitList> result) {
            classWaitArray = result;
            classAdapter = new ClassWaitListAdapter(getActivity(),
                    R.layout.item_class_waitlist, classWaitArray);
            setListAdapter(classAdapter);
            classAdapter.setNotifyOnChange(true);

        }
    }

    public ArrayList<ClassWaitList> getWaitList() {
        String MethodName = "getClassWaitList";
        SoapObject response = InvokeStudentRosterMethod(Globals.Data.URL, MethodName);
        return RetrieveStudentRosterFromSoap(response);

    }

    public SoapObject InvokeStudentRosterMethod(String URL, String METHOD_NAME) {

        SoapObject request = new SoapObject(Globals.Data.NAMESPACE, METHOD_NAME);

        PropertyInfo piUserID = new PropertyInfo();
        piUserID.setName("UserID");
        piUserID.setValue(oUser.UserID);
        request.addProperty(piUserID);

        PropertyInfo piUserGUID = new PropertyInfo();
        piUserGUID.setName("UserGUID");
        piUserGUID.setValue(oUser.UserGUID);
        request.addProperty(piUserGUID);

        PropertyInfo piClID = new PropertyInfo();
        piClID.setName("ClID");
        piClID.setValue(oSchoolClass.ClID);
        request.addProperty(piClID);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        return MakeStudentRosterCall(URL, envelope, Globals.Data.NAMESPACE, METHOD_NAME);
    }


    public static SoapObject MakeStudentRosterCall(String URL,
                                                   SoapSerializationEnvelope envelope, String NAMESPACE,
                                                   String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject response = null;
        try {
            envelope.addMapping(NAMESPACE, "ClassWaitList",
                    new ClassWaitList().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }

    public static ArrayList<ClassWaitList> RetrieveStudentRosterFromSoap(SoapObject soap) {

        ArrayList<ClassWaitList> classWait = new ArrayList<ClassWaitList>();
        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject waitItem = (SoapObject) soap.getProperty(i);

            ClassWaitList wait = new ClassWaitList();
            for (int j = 0; j < waitItem.getPropertyCount(); j++) {
                wait.setProperty(j, waitItem.getProperty(j)
                        .toString());
                if (waitItem.getProperty(j).equals("anyType{}")) {
                    waitItem.setProperty(j, "");
                }

            }
            classWait.add(i, wait);
        }

        return classWait;
    }


    /**
     * Checking if class that is to be enrolled conflicks with any other previously registered
     * Classes
     */
    public class checkClassConflicts extends AsyncTask<Globals.Data, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Globals.Data... data) {

            return oGlobals.checkConflicts(_appPrefs, oSchoolClass, oStudent);
        }

        protected void onPostExecute(ArrayList<String> result) {
            conflictsArray = result;

        }
    }

}
