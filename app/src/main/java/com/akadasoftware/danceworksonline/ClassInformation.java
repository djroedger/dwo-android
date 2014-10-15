package com.akadasoftware.danceworksonline;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TimePicker;
import android.widget.Toast;

import com.akadasoftware.danceworksonline.Adapters.SchoolClassAdapter;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Dialogs.EditEndTimeDialog;
import com.akadasoftware.danceworksonline.Dialogs.EditStartTimeDialog;
import com.akadasoftware.danceworksonline.Dialogs.EnrollDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ksudu94 on 6/26/2014.
 */
public class ClassInformation extends ActionBarActivity implements ActionBar.TabListener,
        ClassInformationFragment.onEditStartTimeDialog,
        ClassInformationFragment.onEditEndTimeDialog,
        ClassRosterFragment.OnRosterInteractionListener,
        EditEndTimeDialog.EditStopTimeDialogListener,
        EditStartTimeDialog.EditTimeDialogListener,
        EnrollDialog.EnrollDialogListener,
        ClassWaitListFragment.onEnrollDialog {

    ViewPager mViewPager;
    private AppPreferences _appPrefs;
    ClassPagerAdapter mSectionsPagerAdapter;
    String strSession;
    Globals oGlobals;
    SchoolClasses globalSchoolClasses;
    Student globalStudent;
    int intClassPosition;

    /**
     * Uses the saved position from the onAccountSelected method in Home.java to fill an empty
     * account with the matching position in the account list array.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_information);

        _appPrefs = new AppPreferences(getApplicationContext());
        oGlobals = new Globals();
        strSession = getIntent().getStringExtra("SessionName");

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ClassPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter. setOFfScreenPageLimit handles the number
        // of tabs that are preloaded
        mViewPager = (ViewPager) findViewById(R.id.classPager);
        //How many adjacent pages it loads
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        int pageCount = mSectionsPagerAdapter.getCount();
        for (int i = 0; i < pageCount; i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onEditEndTimeDialog(String endTime) {
        EditEndTimeDialog endStart = new EditEndTimeDialog();
        Bundle args = new Bundle();
        args.putString("Stop", endTime);
        endStart.setArguments(args);
        endStart.show(getFragmentManager(), "");

    }

    @Override
    public void onEditStartTimeDialog(String startTime) {
        EditStartTimeDialog editStart = new EditStartTimeDialog();
        Bundle args = new Bundle();
        args.putString("Start", startTime);
        editStart.setArguments(args);
        editStart.show(getFragmentManager(), "");
    }

    public void onFinishEditTimeDialog(TimePicker tpStartTime) {

        ClassInformationFragment cf = (ClassInformationFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 0);
        oGlobals = new Globals();

        int hour = tpStartTime.getCurrentHour();


        if (hour > 12) {
            hour -= 12;
        }
        String startHour = String.valueOf(hour);

        int minute = tpStartTime.getCurrentMinute();
        String startMinute = "00";
        if (minute < 10) {
            startMinute = "0" + minute;
        } else {
            startMinute = String.valueOf(minute);
        }
        if (tpStartTime.getCurrentHour() > 11) {
            cf.isAm = true;
        }


        cf.newStartTime = startHour + ":" + startMinute;
        cf.newStartTime = oGlobals.BuildTimeString(cf.newStartTime, cf.oSchoolClass, true);

        cf.tvStartTimeClick.setText(cf.newStartTime);

    }

    @Override
    public void onFinishEditStopTimeDialog(TimePicker tpEndTime) {

        ClassInformationFragment cf = (ClassInformationFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 0);
        oGlobals = new Globals();

        int hour = tpEndTime.getCurrentHour();

        if (hour > 12) {
            hour -= 12;
        }

        String endHour = String.valueOf(hour);
        int minute = tpEndTime.getCurrentMinute();
        String endMinute = "00";
        if (minute < 10) {
            endMinute = "0" + minute;
        } else {
            endMinute = String.valueOf(minute);
        }
        if (tpEndTime.getCurrentHour() > 11) {
            cf.isAm = true;
        }

        cf.newEndTime = endHour + ":" + endMinute;
        cf.newEndTime = oGlobals.BuildTimeString(cf.newEndTime, cf.oSchoolClass, false);

        cf.tvEndTimeClick.setText(cf.newEndTime);
    }

    @Override
    public void onRosterInteraction(String id) {

    }


    @Override
    public void onEnrollDialog(SchoolClasses objSchoolClass, Student oStudent, ArrayList<String> conflictsArray, int positionOfClass, SchoolClassAdapter classAdapter) {
        FragmentManager fm = getSupportFragmentManager();
        EnrollDialog enrollDialog = new EnrollDialog();


        SchoolClasses[] arraySchoolClasses = new SchoolClasses[1];
        arraySchoolClasses[0] = objSchoolClass;

        Student[] arrayStudents = new Student[1];
        arrayStudents[0] = oStudent;

        Gson gson = new Gson();
        String strJsonSchoolClasses = gson.toJson(arraySchoolClasses);
        String strStudent = gson.toJson(arrayStudents);

        getIntent().putExtra("SchoolClasses", strJsonSchoolClasses);
        getIntent().putExtra("Student", strStudent);
        getIntent().putExtra("StuID", oStudent.StuID);
        getIntent().putExtra("Position", positionOfClass);


        getIntent().putStringArrayListExtra("Conflicts", conflictsArray);

        //Just the name of the dialog. Has no effect on it.
        enrollDialog.show(fm, "");
    }

    @Override
    public void onEnrollDialogPositiveClick(SchoolClasses oSchoolClasses, Student oStudent, int position) {
        globalSchoolClasses = oSchoolClasses;
        globalStudent = oStudent;
        intClassPosition = position;

        EnrollStudentAsync enrollStudent = new EnrollStudentAsync();
        enrollStudent.execute();


    }

    @Override
    public void onEnrollDialogNuetralClick(SchoolClasses oSchoolClasses, Student oStudent, int position) {
        globalSchoolClasses = oSchoolClasses;
        globalStudent = oStudent;
        intClassPosition = position;


        WaitListStudentAsync waitList = new WaitListStudentAsync();
        waitList.execute();


    }


    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class ClassPagerAdapter extends FragmentPagerAdapter {

        public ClassPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //Handles the tabs and which fragments fill them
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a newFragment that is defined based on the switch case

            Fragment newFragment;

            int listPosition = _appPrefs.getClassListPosition();


            switch (position) {
                case 0:
                    newFragment = ClassInformationFragment.newInstance(listPosition, strSession);
                    break;
                case 1:
                    newFragment = ClassRosterFragment.newInstance(listPosition);
                    break;
                case 2:
                    newFragment = ClassWaitListFragment.newInstance(listPosition);
                    break;
                case 3:
                    newFragment = ClassAttendance.newInstance(listPosition);
                    break;
                default:
                    newFragment = ClassInformationFragment.newInstance(listPosition, strSession);
                    break;
            }

            return newFragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        //Tab titles
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.class_information).toUpperCase(l);
                case 1:
                    return getString(R.string.class_roster).toUpperCase(l);
                case 2:
                    return getString(R.string.class_wait_list).toUpperCase(l);
                case 3:
                    return getString(R.string.class_attendance).toUpperCase(l);
                /**
                 * case 4:
                 return getString(R.string.title_student_enroll).toUpperCase(l);
                 */

                default:
                    return "";
            }
        }
    }


    /**
     * Places student on waitlist
     */
    private class WaitListStudentAsync extends
            AsyncTask<Globals.Data, Void, Integer> {

        ProgressDialog progress;

        protected void onPreExecute() {
            //progress = ProgressDialog.show(ClassInformation.this, "Parsing the Internet", "Accessing WaitList...", true);
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
            //progress.dismiss();
            if (result > 0) {
                Toast toast = Toast.makeText(ClassInformation.this, "The student was placed on the waitlist", Toast.LENGTH_LONG);
                toast.show();
                /**
                 * Returns new WatiID
                 */
                globalSchoolClasses.WaitID = result;
                globalSchoolClasses.EnrollmentStatus = 3;
                globalSchoolClasses.ClWait = String.valueOf(Integer.valueOf(globalSchoolClasses.ClWait) + 1);

                /**
                 * Get Class list
                 */
                ArrayList<SchoolClasses> classesArray;
                if (_appPrefs.getAccessAllClasses()) {
                    classesArray = _appPrefs.getSchoolClassList();

                    /**
                     * Update class list with new class with updated variables
                     */
                    classesArray.set(intClassPosition, globalSchoolClasses);
                    _appPrefs.saveSchoolClassList(classesArray);
                } else {
                    classesArray = _appPrefs.getMySchoolClassList();

                    /**
                     * Update class list with new class with updated variables
                     */
                    classesArray.set(intClassPosition, globalSchoolClasses);
                    _appPrefs.saveMySchoolClassList(classesArray);
                }

            } else {
                Toast toast = Toast.makeText(ClassInformation.this, "The student was not placed on the waitlist", Toast.LENGTH_LONG);
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
            //Causing problems going from one dialog to another
            //progress = ProgressDialog.show(ClassInformation.this, "Enrolling Student", "Loading...", true);
        }

        @Override
        protected Integer doInBackground(Globals.Data... data) {
            /**
             * Enrolling student in class, even if there are conflicts.
             */
            return oGlobals.enrollInClass(_appPrefs, globalStudent.StuID, globalSchoolClasses);
        }

        protected void onPostExecute(Integer result) {
            //progress.dismiss();
            if (result > 0) {
                Toast toast = Toast.makeText(ClassInformation.this, "The student was enrolled in the class", Toast.LENGTH_LONG);
                toast.show();

                /**
                 * Update ClRID and EnrollmentStatus
                 */
                globalSchoolClasses.ClRID = result;
                globalSchoolClasses.EnrollmentStatus = 1;
                globalSchoolClasses.ClCur += 1;
                /**
                 * Get Class list
                 */
                ArrayList<SchoolClasses> classesArray;
                if (_appPrefs.getAccessAllClasses()) {
                    classesArray = _appPrefs.getSchoolClassList();

                    /**
                     * Update class list with new class with updated variables
                     */
                    classesArray.set(intClassPosition, globalSchoolClasses);
                    _appPrefs.saveSchoolClassList(classesArray);
                } else {
                    classesArray = _appPrefs.getMySchoolClassList();

                    /**
                     * Update class list with new class with updated variables
                     */
                    classesArray.set(intClassPosition, globalSchoolClasses);
                    _appPrefs.saveMySchoolClassList(classesArray);

                }


            } else {
                Toast toast = Toast.makeText(ClassInformation.this, "The student was not enrolled in the class", Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }

}
