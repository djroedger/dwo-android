package com.akadasoftware.danceworksonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.classes.Account;
import com.akadasoftware.danceworksonline.classes.AppPreferences;

import java.util.ArrayList;
import java.util.Locale;

public class AccountInformation extends ActionBarActivity implements ActionBar.TabListener, AccountTransactionsFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private AppPreferences _appPrefs;
    Account account;
    ArrayList<Account> accounts;

    /*Uses the saved position from the onAccountSelected method in Home.java to fill an empty
     account with the matching position in the accountlist array.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        _appPrefs = new AppPreferences(getApplicationContext());
        accounts = _appPrefs.getAccounts();

        account = accounts.get(_appPrefs.getAccountListPosition());
        _appPrefs.saveAcctID(account.AcctID);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_information, menu);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a newFragment that is defined based on the switch case

            Fragment newFragment;


            switch (position) {
                case 0:
                    newFragment = new AccountInformationFragment();
                    break;
                case 1:
                    newFragment = new AccountInformationFragment();
                    break;
                case 2:
                    newFragment = new AccountTransactionsFragment();
                    break;
                default:
                    newFragment = new AccountInformationFragment();
                    break;
            }

            return newFragment;
        }

        @Override
        public int getCount() {
            // Show x number of pages.
            return 5;
        }

        //Tab titles
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_account_info).toUpperCase(l);
                case 1:
                    return getString(R.string.title_account_students).toUpperCase(l);
                case 2:
                    return getString(R.string.title_account_transactions).toUpperCase(l);
                case 3:
                    return getString(R.string.title_account_payment).toUpperCase(l);
                case 4:
                    return getString(R.string.title_account_charge).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A fragment containing a textviews for misc. account informations such as Name and address etc.
     */
    public class AccountInformationFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public AccountInformationFragment newInstance(int sectionNumber) {
            AccountInformationFragment fragment = new AccountInformationFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public AccountInformationFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_account_information, container, false);

            TextView acctname = (TextView) rootView.findViewById(R.id.tvacctnamefield);
            acctname.setText(account.FName + " " + account.LName);

            TextView acctaddress = (TextView) rootView.findViewById(R.id.tvacctaddressfield);
            acctaddress.setText(account.Address + ", " + account.City + ", " + account.State + " " + account.ZipCode);

            TextView acctphone = (TextView) rootView.findViewById(R.id.tvacctphonefield);
            acctphone.setText(account.Phone);

            TextView tvemail = (TextView) rootView.findViewById(R.id.tvacctemailfield);
            tvemail.setText(account.EMail);

            return rootView;
        }
    }

    public void OnFragmentInteraction(int id) {

        /* Starting a new activity (AccountInformation) which is handled by intents in the manifest.
        The position is saved to fill a matching account in the listarray.
         */

        // _appPrefs = new AppPreferences(getApplicationContext());
        // _appPrefs.saveAccountListPosition(id);
        Intent openMainPage = new Intent("com.akadasoftware.danceworksonline.AccountInformation");
        startActivity(openMainPage);


    }


}