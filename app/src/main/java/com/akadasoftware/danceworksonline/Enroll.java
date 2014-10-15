package com.akadasoftware.danceworksonline;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.akadasoftware.danceworksonline.Adapters.SchoolClassAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Dialogs.EnrollDialog;
import com.google.gson.Gson;

import java.util.ArrayList;


public class Enroll extends FragmentActivity implements
        //StudentEnrollFragment.OnStudentEnrollListener,
        StudentEnrollFragment.onEnrollDialog,
        EnrollDialog.EnrollDialogListener {

    SchoolClasses globalSchoolClasses;
    Student globalStudent;
    private AppPreferences _appPrefs;
    SchoolClassAdapter objClassAdapter;
    int intClassPosition, intStuID;
    Globals oGlobals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        oGlobals = new Globals();
        _appPrefs = new AppPreferences(Enroll.this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StudentEnrollFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enroll, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Don't need this cause we have a dialog that handles interaction
     *
     * @Override public void onStudentEnrollInteraction(int classPosition, int newClRID) {}
     */


    @Override
    public void onEnrollDialog(SchoolClasses objSchoolClass, Student oStudent, ArrayList<String> conflictsArray
            , int positionOfClass, SchoolClassAdapter inputClassAdapter) {
        FragmentManager fm = getSupportFragmentManager();
        EnrollDialog enrollDialog = new EnrollDialog();


        SchoolClasses[] arraySchoolClasses = new SchoolClasses[1];
        arraySchoolClasses[0] = objSchoolClass;

        Student[] arrayStudents = new Student[1];
        arrayStudents[0] = oStudent;

        Gson gson = new Gson();
        String strJsonSchoolClasses = gson.toJson(arraySchoolClasses);
        String strStudent = gson.toJson(arrayStudents);


        intStuID = oStudent.StuID;
        getIntent().putExtra("SchoolClasses", strJsonSchoolClasses);
        getIntent().putExtra("Student", strStudent);
        getIntent().putExtra("StuID", oStudent.StuID);
        getIntent().putExtra("Position", positionOfClass);


        objClassAdapter = inputClassAdapter;

        getIntent().putStringArrayListExtra("Conflicks", conflictsArray);

        //Just the name of the dialog. Has no effect on it.
        enrollDialog.show(fm, "");
    }

    @Override
    public void onEnrollDialogPositiveClick(SchoolClasses inputSchoolClasses, Student inputStudent, int classPosition) {

        globalSchoolClasses = inputSchoolClasses;
        globalStudent = inputStudent;
        intClassPosition = classPosition;

        EnrollStudentAsync enrollStudent = new EnrollStudentAsync();
        enrollStudent.execute();

    }

    @Override
    public void onEnrollDialogNuetralClick(SchoolClasses inputSchoolClasses, Student inputStudent,
                                           int classPosition) {
        globalSchoolClasses = inputSchoolClasses;
        globalStudent = inputStudent;
        intClassPosition = classPosition;


        WaitListStudentAsync waitList = new WaitListStudentAsync();
        waitList.execute();

    }


    /**
     * Places student on waitlist
     */
    private class WaitListStudentAsync extends
            AsyncTask<Globals.Data, Void, Integer> {

        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(Enroll.this, "Parsing the Internet", "Accessing WaitList...", true);
        }

        @Override
        protected Integer doInBackground(Globals.Data... data) {
            /**
             * Placing student on waitlist
             *
             */
            return oGlobals.placeStudentOnWaitList(_appPrefs, globalSchoolClasses, globalStudent);
        }

        protected void onPostExecute(Integer result) {
            progress.dismiss();
            if (result > 0) {
                Toast toast = Toast.makeText(Enroll.this, "The student was placed on the waitlist", Toast.LENGTH_LONG);
                toast.show();
                /**
                 * Returns new WatiID
                 */
                globalSchoolClasses.WaitID = result;
                globalSchoolClasses.EnrollmentStatus = 3;

                objClassAdapter.replaceSchoolClass(globalSchoolClasses, intClassPosition);

            } else {
                Toast toast = Toast.makeText(Enroll.this, "The student was not placed on the waitlist", Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }

    /**
     * Enrolls a student in a class
     *

     * @return Integer greater than 0 means success
     */
    private class EnrollStudentAsync extends
            AsyncTask<Globals.Data, Void, Integer> {

        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(Enroll.this, "Enrolling Student", "Loading...", true);
        }

        @Override
        protected Integer doInBackground(Globals.Data... data) {
            /**
             * Enrolling student in class, even if there are conflicts.
             */
            return oGlobals.enrollInClass(_appPrefs, globalStudent.StuID, globalSchoolClasses);
        }

        protected void onPostExecute(Integer result) {
            progress.dismiss();
            if (result > 0) {
                Toast toast = Toast.makeText(Enroll.this, "The student was enrolled in the class", Toast.LENGTH_LONG);
                toast.show();

                /**
                 * Update ClRID and EnrollmentStatus
                 */
                globalSchoolClasses.ClRID = result;
                globalSchoolClasses.EnrollmentStatus = 1;

                objClassAdapter.replaceSchoolClass(globalSchoolClasses, intClassPosition);


                /**
                 * Get Class list
                 */
                ArrayList<SchoolClasses> classesArray = _appPrefs.getSchoolClassList();

                /**
                 * Update class list with new class with updated variables
                 */
                classesArray.set(intClassPosition, globalSchoolClasses);
                _appPrefs.saveSchoolClassList(classesArray);

            } else {
                Toast toast = Toast.makeText(Enroll.this, "The student was not enrolled in the class", Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }

}
