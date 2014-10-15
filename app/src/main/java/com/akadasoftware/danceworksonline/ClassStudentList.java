package com.akadasoftware.danceworksonline;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Dialogs.ClassStudentListDialog;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ClassStudentList extends FragmentActivity implements
        //ClassStudentListFragment.OnClassListInteractionListener,
        ClassStudentListDialog.ClassStudentListDialogListener,
        ClassStudentListFragment.onClassStudentListDialog {

    private AppPreferences _appPrefs;
    Globals oGlobals;
    SchoolClasses globalSchoolClasses;
    Student globalStudents;

    int intStuID = 0;
    int intClassPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_listof_students);
        oGlobals = new Globals();
        _appPrefs = new AppPreferences(ClassStudentList.this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ClassStudentListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.class_listof_students, menu);
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
     * @Override public void onClassListInteraction(Uri uri) {}
     */


    @Override
    public void onClassStudentListDialogPositiveClick(SchoolClasses oSchoolClass, Student oStudent, int position) {
        intStuID = oStudent.StuID;
        intClassPosition = position;
        globalSchoolClasses = oSchoolClass;
        globalStudents = oStudent;

        EnrollStudentAsync enroll = new EnrollStudentAsync();
        enroll.execute();
    }

    @Override
    public void onClassStudentListDialogNuetralClick(SchoolClasses oSchoolClass, Student oStudent) {
        globalSchoolClasses = oSchoolClass;
        globalStudents = oStudent;

        WaitListStudentAsync wait = new WaitListStudentAsync();
        wait.execute();
    }

    @Override
    public void onClassStudentListDialog(ArrayList<String> conflictsArray, SchoolClasses oSchoolClass, Student oStudent, int position) {

        FragmentManager fm = getSupportFragmentManager();
        ClassStudentListDialog dialog = new ClassStudentListDialog();

        SchoolClasses[] arraySchoolClasses = new SchoolClasses[1];
        arraySchoolClasses[0] = oSchoolClass;

        Student[] arrayStudents = new Student[1];
        arrayStudents[0] = oStudent;

        Gson gson = new Gson();
        String strJsonSchoolClasses = gson.toJson(arraySchoolClasses);
        String strStudent = gson.toJson(arrayStudents);


        getIntent().putExtra("SchoolClass", strJsonSchoolClasses);
        getIntent().putExtra("Student", strStudent);
        getIntent().putExtra("Position", position);
        getIntent().putStringArrayListExtra("Conflicts", conflictsArray);
        dialog.show(fm, "");
    }

    /**
     * Enrolls a student in a class
     */
    private class EnrollStudentAsync extends
            AsyncTask<Globals.Data, Void, Integer> {

        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(ClassStudentList.this, "Enrolling Student", "Loading...", true);
        }

        @Override
        protected Integer doInBackground(Globals.Data... data) {
            /**
             * Enrolling student in class, even if there are conflicts.
             */
            return oGlobals.enrollInClass(_appPrefs, intStuID, globalSchoolClasses);
        }

        protected void onPostExecute(Integer result) {
            progress.dismiss();
            if (result > 0) {
                Toast toast = Toast.makeText(ClassStudentList.this, "The student was enrolled in the class", Toast.LENGTH_LONG);
                toast.show();

                /**
                 * Update ClRID and EnrollmentStatus
                 */
                globalSchoolClasses.ClRID = result;
                globalSchoolClasses.EnrollmentStatus = 1;

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
                Toast toast = Toast.makeText(ClassStudentList.this, "The student was not enrolled in the class", Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }


    /**
     * Place a student in a waitlist
     */
    private class WaitListStudentAsync extends
            AsyncTask<Globals.Data, Void, Integer> {

        ProgressDialog progress;

        protected void onPreExecute() {
            progress = ProgressDialog.show(ClassStudentList.this, "Placing on Wait List", "Loading...", true);
        }

        @Override
        protected Integer doInBackground(Globals.Data... data) {
            /**
             * Enrolling student in class, even if there are conflicts.
             */
            return oGlobals.placeStudentOnWaitList(_appPrefs, globalSchoolClasses, globalStudents);
        }

        protected void onPostExecute(Integer result) {
            progress.dismiss();
            if (result > 0) {
                Toast toast = Toast.makeText(ClassStudentList.this, "The student was placed on the waitlist", Toast.LENGTH_LONG);
                toast.show();
                /**
                 * Returns new WatiID
                 */
                globalSchoolClasses.WaitID = result;
                globalSchoolClasses.EnrollmentStatus = 3;

                //objClassAdapter.replaceSchoolClass(globalSchoolClasses, intClassPosition);

            } else {
                Toast toast = Toast.makeText(ClassStudentList.this, "The student was not placed on the waitlist", Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }
}
