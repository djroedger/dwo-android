package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Adapters.StudentRosterAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.StudentRoster;
import com.akadasoftware.danceworksonline.Classes.User;
import com.akadasoftware.danceworksonline.dummy.DummyContent;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;


public class ClassRosterFragment extends ListFragment implements AbsListView.OnItemClickListener {


    Activity activity;
    Globals oGlobals;
    User oUser;
    SchoolClasses oSchoolClass;
    ArrayList<SchoolClasses> schoolClassList;
    ArrayList<StudentRoster> studentRosterArray;
    private AppPreferences _appPrefs;

    private StudentRosterAdapter studentRosterAdapter;
    int position;
    private OnRosterInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public static ClassRosterFragment newInstance(int position) {
        ClassRosterFragment fragment = new ClassRosterFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClassRosterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();


        oGlobals = new Globals();
        _appPrefs = new AppPreferences(activity);
        oUser = _appPrefs.getUser();
        position = getArguments().getInt("Position");

        schoolClassList = _appPrefs.getSchoolClassList();
        oSchoolClass = schoolClassList.get(position);

        getClassRosterAsync getRoster = new getClassRosterAsync();
        getRoster.execute();
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
            mListener = (OnRosterInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRosterInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onRosterInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText("No Students in Class");
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
    public interface OnRosterInteractionListener {
        public void onRosterInteraction(String id);
    }

    public class getClassRosterAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<StudentRoster>> {

        @Override
        protected ArrayList<StudentRoster> doInBackground(Globals.Data... data) {

            return getRoster();
        }

        protected void onPostExecute(ArrayList<StudentRoster> result) {

            studentRosterArray = result;
            studentRosterAdapter = new StudentRosterAdapter(getActivity(),
                    R.layout.item_student_roster, studentRosterArray);
            setListAdapter(studentRosterAdapter);
            studentRosterAdapter.setNotifyOnChange(true);
        }
    }

    public ArrayList<StudentRoster> getRoster() {
        String MethodName = "getClassRoster";
        SoapObject response = InvokeStudentRosterMethod(Globals.Data.URL, MethodName);
        return RetrieveStudentRosterFromSoap(response);

    }

    public SoapObject InvokeStudentRosterMethod(String URL, String MethodName) {

        SoapObject request = GetSoapObject(MethodName);

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
        return MakeStudentRosterCall(URL, envelope, Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapObject GetSoapObject(String MethodName) {
        return new SoapObject(Globals.Data.NAMESPACE, MethodName);
    }

    public static SoapObject MakeStudentRosterCall(String URL,
                                                   SoapSerializationEnvelope envelope, String NAMESPACE,
                                                   String METHOD_NAME) {
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        SoapObject response = null;
        try {
            envelope.addMapping(NAMESPACE, "StudentRoster",
                    new StudentRoster().getClass());
            HttpTransport.call(METHOD_NAME, envelope);
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }

    public static ArrayList<StudentRoster> RetrieveStudentRosterFromSoap(SoapObject soap) {

        ArrayList<StudentRoster> rosterStudents = new ArrayList<StudentRoster>();
        for (int i = 0; i < soap.getPropertyCount(); i++) {

            SoapObject studentrosteritem = (SoapObject) soap.getProperty(i);

            StudentRoster roster = new StudentRoster();
            for (int j = 0; j < studentrosteritem.getPropertyCount(); j++) {
                roster.setProperty(j, studentrosteritem.getProperty(j)
                        .toString());
                if (studentrosteritem.getProperty(j).equals("anyType{}")) {
                    studentrosteritem.setProperty(j, "");
                }

            }
            rosterStudents.add(i, roster);
        }

        return rosterStudents;
    }
}
