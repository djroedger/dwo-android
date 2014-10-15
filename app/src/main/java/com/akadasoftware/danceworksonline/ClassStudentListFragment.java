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
import android.widget.Button;
import android.widget.ListView;

import com.akadasoftware.danceworksonline.Adapters.StudentListAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Classes.User;

import java.util.ArrayList;


public class ClassStudentListFragment extends ListFragment {

    int classPosition;

    Globals oGlobals;
    User oUser;
    Student oStudent;
    SchoolClasses oSchoolClass;

    ArrayList<SchoolClasses> schoolClassList;
    ArrayList<Student> studentsArray = new ArrayList<Student>();
    ArrayList<String> conflictsArray = new ArrayList<String>();

    private AppPreferences _appPrefs;
    Activity activity;

    StudentListAdapter stuListAdapter;

    //private OnClassListInteractionListener mListener;

    private onClassStudentListDialog dListener;

    Button btnDone;

    /**
     * public interface OnClassListInteractionListener {
     * public void onClassListInteraction(Uri uri); }
     */

    public interface onClassStudentListDialog {
        public void onClassStudentListDialog(ArrayList<String> conflictsArray, SchoolClasses oSchoolClass, Student oStudent, int position);
    }

    public static ClassStudentListFragment newInstance(int position) {
        ClassStudentListFragment fragment = new ClassStudentListFragment();
        Bundle args = new Bundle();
        args.putInt("Position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public ClassStudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();


        oGlobals = new Globals();
        _appPrefs = new AppPreferences(activity);
        oUser = _appPrefs.getUser();
        classPosition = _appPrefs.getClassListPosition();

        schoolClassList = _appPrefs.getSchoolClassList();
        oSchoolClass = schoolClassList.get(classPosition);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_class_list_of_students, container, false);
        btnDone = (Button) view.findViewById(R.id.btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openClassPage = new Intent("com.akadasoftware.danceworksonline.ClassInformation");
                startActivity(openClassPage);
            }
        });
        return view;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnClassListInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        try {
            dListener = (onClassStudentListDialog) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onClassStudentListDialog");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getStudentsListAsync getStudentsList = new getStudentsListAsync();
        getStudentsList.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
        dListener = null;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        super.onListItemClick(l, v, position, id);

        oStudent = (Student) this.getListAdapter().getItem(position);

        checkClassConflicts conflictCheck = new checkClassConflicts();
        conflictCheck.execute();

        dListener.onClassStudentListDialog(conflictsArray, oSchoolClass, oStudent, classPosition);
    }


    /**
     * Gets student list
     */
    private class getStudentsListAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<Student>> {
        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(activity, "Refreshing List", "Loading...", true);
        }

        protected ArrayList<Student> doInBackground(Globals.Data... data) {
            return oGlobals.getStudents(_appPrefs, 0);
        }

        protected void onPostExecute(ArrayList<Student> result) {
            progress.dismiss();
            _appPrefs.saveStudents(result);
            studentsArray = result;
            stuListAdapter = new StudentListAdapter(activity,
                    R.layout.item_studentlist, studentsArray);
            setListAdapter(stuListAdapter);
            stuListAdapter.setNotifyOnChange(true);
        }

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
