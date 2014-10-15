package com.akadasoftware.danceworksonline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Dialogs.EditAmountDialog;
import com.akadasoftware.danceworksonline.Dialogs.EditCreditCardDialog;
import com.akadasoftware.danceworksonline.Dialogs.EditDateDialog;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * This is the parent activity from which all fragments are linked and where all of the interfaces
 * are linked. In the case of dates both the dialog and the fragment itself have things that need
 * to be implemented.
 */
public class AccountInformation extends ActionBarActivity implements ActionBar.TabListener,
        AccountTransactionsFragment.OnTransactionSelected,
        AccountStudentsFragment.OnStudentFragmentInteractionListener,
        EnterChargeFragment.onEditAmountDialog,
        EnterChargeFragment.onEditDateDialog,
        EnterPaymentFragment.onEditAmountDialog,
        EnterPaymentFragment.onEditDateDialog,
        EnterPaymentFragment.onEditCreditCardDialog,
        EditDateDialog.EditDateDialogListener,
        EditAmountDialog.EditAmountDialogListener,
        EditCreditCardDialog.EditCreditCardDialogListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AccountPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private AppPreferences _appPrefs;


    /**
     * Uses the saved position from the onAccountSelected method in Home.java to fill an empty
     * account with the matching position in the account list array.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);


        _appPrefs = new AppPreferences(getApplicationContext());

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new AccountPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter. setOFfScreenPageLimit handles the number
        // of tabs that are preloaded
        mViewPager = (ViewPager) findViewById(R.id.pager);
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
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_information, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        int position = tab.getPosition();
        /*if (position==3){
            Fragment f = this.getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.pager+":"+position);
            if (f!=null){
                ((EnterPaymentFragment) f).onPause();
                ((EnterPaymentFragment) f).refreshEnterPayment();
            }
        }*/
        mViewPager.setCurrentItem(position, true);

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onStudentFragmentInteraction(String id) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class AccountPagerAdapter extends FragmentPagerAdapter {

        public AccountPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //Handles the tabs and which fragments fill them
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a newFragment that is defined based on the switch case

            Fragment newFragment;

            int listPosition = _appPrefs.getAccountListPosition();


            switch (position) {
                case 0:
                    newFragment = AccountInformationFragment.newInstance(listPosition);
                    break;
                case 1:
                    newFragment = AccountStudentsFragment.newInstance(listPosition);
                    break;
                case 2:
                    newFragment = AccountTransactionsFragment.newInstance(listPosition);
                    break;
                case 3:
                    newFragment = EnterPaymentFragment.newInstance(listPosition, "", Float.parseFloat("0.00"));
                    break;
                case 4:
                    newFragment = EnterChargeFragment.newInstance(listPosition);
                    break;
                default:
                    newFragment = AccountInformationFragment.newInstance(listPosition);
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
                case 5:
                    return "PullFragment".toUpperCase(l);
                default:
                    return "";
            }
        }

    }

    // Interface created on the Transaction page.. Is handled in the on click of a list item
    // passes in balance and TID from the listview and then does appropriate actions with them
    public void OnTransactionSelected(float balance, int TID, String description) {

        //Change to whatever activity I want to start.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSelectedNavigationItem(3);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        EnterPaymentFragment pf = (EnterPaymentFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 3);
        pf.tvChangeAmount.setText(String.valueOf(format.format(balance)));
        _appPrefs.saveChgID(TID);
        pf.chgid = TID;
        pf.etDescription.setText("Payment - " + description);

    }

    public void onEditCreditCardDialog(int accountPosition) {

        FragmentManager fm = getSupportFragmentManager();
        EditCreditCardDialog editCCDialog = new EditCreditCardDialog();
        Bundle args = new Bundle();
        args.putInt("AccountPosition", accountPosition);
        editCCDialog.setArguments(args);
        //Just the name of the dialog. Has no effect on it.
        editCCDialog.show(fm, "");
    }


    @Override
    public void onDialogPositiveClick(String CCNo, String CVV, String Date, Boolean saveCard) {
        EnterPaymentFragment pf = (EnterPaymentFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 3);
        pf.CardNumber = CCNo;
        pf.CVV = CVV;
        pf.CCExpire = Date;
        pf.saveNewCreditCard = saveCard;
        pf.CCard = CCNo.substring(CCNo.length() - 4);
        switch (pf.CardNumber.charAt(0)) {
            case '3':
                pf.etReference.setText("AmEx");
                pf.ChkNo = "AmEx";
                pf.Kind = "A";
                break;
            case '4':
                pf.etReference.setText("Discover");
                pf.ChkNo = "Discover";
                pf.Kind = "D";
                break;
            case '5':
                pf.etReference.setText("MC");
                pf.ChkNo = "MC";
                pf.Kind = "M";
                break;
            case '6':
                pf.etReference.setText("Visa");
                pf.ChkNo = "Visa";
                pf.Kind = "V";
                break;
            default:
                Toast toast = Toast.makeText(this, "Invalid Credit Card, please try again.", Toast.LENGTH_LONG);
                toast.show();
                break;
        }


    }

    @Override
    public void onDialogNegativeClick() {
        Toast toast = Toast.makeText(this, "No information saved", Toast.LENGTH_LONG);
        toast.show();
        /**
         * Do nothing and none of the information is saved.
         */
    }

    /**
     * Passes info from fragment to dialog so that you can pre-fill the dialog with the stored value
     * for amount using the bundle
     */


    public void onEditAmountDialog(String input) {

        FragmentManager fm = getSupportFragmentManager();
        EditAmountDialog editAmountDialog = new EditAmountDialog();
        Bundle args = new Bundle();
        args.putString("Input", input);
        editAmountDialog.setArguments(args);
        //Just the name of the dialog. Has no effect on it.
        editAmountDialog.show(fm, "");
    }

    /**
     * The inputText is the value from the edit text from the dialog which we then use set tvChangeAmount
     * and then run the async task.
     */

    @Override
    public void onFinishEditAmountDialog(String inputText) {

        int position = getActionBar().getSelectedTab().getPosition();

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        if (inputText.endsWith(".##")) {
            inputText += ".00";
        }

        inputText = nf.format(Float.parseFloat(inputText));

        if (position == 3) {
            EnterPaymentFragment pf = (EnterPaymentFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 3);
            pf.tvChangeAmount.setText(inputText);
        } else {
            EnterChargeFragment cf = (EnterChargeFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 4);
            cf.tvChangeAmount.setText(inputText);
            cf.runChargeAmountAsync();
        }

    }

    /**
     * The date is the value from the datePicker from the dialog which we then use set tvDate in the
     * EnterCharge Fragment.
     */


    /**
     * After we get the date from the datepicker dialog we use the interface to pass that value back
     * to EnterCharge to set the date to be used for various other methods
     */


    @Override
    public void onEditDateDialog(Calendar date) {

        FragmentManager fm = getSupportFragmentManager();
        EditDateDialog editDateDialog = new EditDateDialog();


        getIntent().putExtra("Calendar", date);
        /**
         * Bundle args = new Bundle();
         * int year = date.get(Calendar.YEAR);
         * int month = date.get(Calendar.MONTH);
         * int day = date.get(Calendar.DAY_OF_MONTH);
         * args.putInt("Year", year);
         * args.putInt("Month", month);
         * args.putInt("Day", day);
         * editDateDialog.setArguments(args);
         */
        editDateDialog.show(fm, "");
    }

    @Override
    public void onFinishEditDateDialog(Calendar dateSelected) {
        int position = getActionBar().getSelectedTab().getPosition();

        if (position == 3) {
            EnterPaymentFragment pf = (EnterPaymentFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 3);
            pf.setDate(dateSelected);

        } else {
            EnterChargeFragment cf = (EnterChargeFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, 4);
            cf.setDate(dateSelected);
        }

    }


}
