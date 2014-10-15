package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.akadasoftware.danceworksonline.Adapters.SchoolClassAdapter;
import com.akadasoftware.danceworksonline.Adapters.SessionAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.School;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Session;
import com.akadasoftware.danceworksonline.Classes.User;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

public class ClassesListFragment extends ListFragment {


    Globals oGlobal;
    Activity activity;
    User oUser;
    School oSchool;
    SchoolClasses oSchoolClass;
    private AppPreferences _appPrefs;
    Session session;
    int SessionID, position;
    Boolean showAllClasses;
    String METHOD_NAME = "";
    String SOAP_ACTION = "";


    ArrayList<Session> sessionArrayList = new ArrayList<Session>();
    SessionAdapter sessionAdapter;

    ArrayList<SchoolClasses> schoolClasssesArray = new ArrayList<SchoolClasses>();


    Spinner sessionClassSpinner;
    private SchoolClassAdapter classAdapter;

    private OnClassInteractionListener classListener;
    private PullToRefreshLayout mPullToRefreshLayout;


    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;


    public interface OnClassInteractionListener {
        // TODO: Update argument type and name
        public void onClassSelected(int id, String sessionName);
    }

    // TODO: Rename and change types of parameters
    public static ClassesListFragment newInstance(int position, boolean allClasses) {
        ClassesListFragment fragment = new ClassesListFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        args.putBoolean("allClasses", allClasses);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClassesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        _appPrefs = new AppPreferences(activity);
        oUser = _appPrefs.getUser();
        oGlobal = new Globals();
        oSchool = _appPrefs.getSchool();
        showAllClasses = getArguments().getBoolean("allClasses");
        classAdapter = new SchoolClassAdapter(getActivity(),
                R.layout.item_studentclass, schoolClasssesArray);
        setListAdapter(classAdapter);
        classAdapter.setNotifyOnChange(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classeslistfragment_list, container, false);

        sessionClassSpinner = (Spinner) view.findViewById(R.id.sessionClassSpinner);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup viewGroup = (ViewGroup) view;

        mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

        ActionBarPullToRefresh.from(activity)

                // We need to insert the PullToRefreshLayout into the Fragment's ViewGroup
                .insertLayoutInto(viewGroup)

                        // We need to mark the ListView and it's Empty View as pull-able
                        // This is because they are not direct children of the ViewGroup
                .theseChildrenArePullable(getListView(), getListView().getEmptyView())

                        // Set the OnRefreshListener
                .options(Options.create().refreshOnUp(true).build())


                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        getSessionsAsync getSessions = new getSessionsAsync();
                        getSessions.execute();
                        mPullToRefreshLayout.setRefreshComplete();

                    }

                })

                        // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            classListener = (OnClassInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnClassInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        classListener = null;
    }


    @Override
    public void onResume() {
        super.onResume();

        getSessionsAsync getSessions = new getSessionsAsync();
        getSessions.execute();

    }

    /**
     * Creates a new instance
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        super.onListItemClick(l, v, position, id);
        oSchoolClass = (SchoolClasses) this.getListAdapter().getItem(position);

        /**
         * Home.Java
         */
        classListener.onClassSelected(position, session.SessionName);
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


    //Adds all items from the Session field to the spinner
    public void addItemsOnSpinner(ArrayList<Session> sess) {

        sessionAdapter = new SessionAdapter(activity,
                R.layout.fragment_studentenroll, sess);

        sessionClassSpinner.setAdapter(sessionAdapter);
        oGlobal.setCurrentSession(sessionClassSpinner, oSchool);
        sessionClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setSelectedSession(sessionClassSpinner);

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
            AsyncTask<Globals.Data, Void, ArrayList<SchoolClasses>> {

        @Override
        protected ArrayList<SchoolClasses> doInBackground(Globals.Data... data) {
            //Picked the Classes tab, shows all of the classes
            if (showAllClasses)
                return oGlobal.getClasses(_appPrefs, SessionID, 0, 0);
                //Picked my Classes tab, shows only classes they are teaching
            else
                return oGlobal.getClasses(_appPrefs, SessionID, 0, oUser.StaffID);
        }

        protected void onPostExecute(ArrayList<SchoolClasses> result) {

            schoolClasssesArray = result;
            if (_appPrefs.getAccessAllClasses()) {
                //List of all classes
                _appPrefs.saveSchoolClassList(schoolClasssesArray);
            } else {
                //List of my classes
                _appPrefs.saveMySchoolClassList(schoolClasssesArray);
            }

            classAdapter = new SchoolClassAdapter(getActivity(),
                    R.layout.item_studentclass, schoolClasssesArray);
            setListAdapter(classAdapter);
            classAdapter.setNotifyOnChange(true);

        }
    }

}
