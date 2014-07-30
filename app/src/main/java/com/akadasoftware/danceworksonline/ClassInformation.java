package com.akadasoftware.danceworksonline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TimePicker;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Dialogs.EditEndTimeDialog;
import com.akadasoftware.danceworksonline.Dialogs.EditStartTimeDialog;

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
        ClassWaitListFragment.OnWaitListInteractionListener {

    ViewPager mViewPager;
    private AppPreferences _appPrefs;
    ClassPagerAdapter mSectionsPagerAdapter;
    String strSession;
    Globals oGlobals;

    /**
     * Uses the saved position from the onAccountSelected method in Home.java to fill an empty
     * account with the matching position in the account list array.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);

        _appPrefs = new AppPreferences(getApplicationContext());

        strSession = getIntent().getStringExtra("SessionName");

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ClassPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter. setOFfScreenPageLimit handles the number
        // of tabs that are preloaded
        mViewPager = (ViewPager) findViewById(R.id.studentPager);
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
    public void onWaitListInteraction(String id) {

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
                case 4:
                    newFragment = ClassEnrollFragment.newInstance(listPosition);
                    break;
                default:
                    newFragment = StudentInformationFragment.newInstance(listPosition);
                    break;
            }

            return newFragment;
        }

        @Override
        public int getCount() {
            return 5;
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
                case 4:
                    return getString(R.string.class_enroll).toUpperCase(l);
                /**
                 * case 4:
                 return getString(R.string.title_student_enroll).toUpperCase(l);
                 */

                default:
                    return "";
            }
        }
    }


}
