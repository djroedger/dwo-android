package com.akadasoftware.danceworksonline;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

import com.akadasoftware.danceworksonline.Classes.Account;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.School;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Classes.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Splash extends ActionBarActivity {

    private static SharedPreferences loginPreferences;
    private static SharedPreferences.Editor loginEditor;
    private AppPreferences _appPrefs;
    String METHOD_NAME, SOAP_ACTION, UserGUID, LogoName, logoUrl;
    int UserID, SchID, SessionID;
    Boolean isTablet;

    Activity activity;
    static User oUser;
    Globals oGlobals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _appPrefs = new AppPreferences(getApplicationContext());
        /**
         * Uses loginPreferences to get the loggedin field to check whether or not the user has a
         * saved profile. From there it either goes to the login screen or the home screen.
         */
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginEditor = loginPreferences.edit();


        _appPrefs.saveNavDrawerPosition(0);


        oUser = _appPrefs.getUser();
        oGlobals = new Globals();

        if (android.os.Build.VERSION.SDK_INT > 16) {

            setContentView(R.layout.activity_logo_splash);
            LogoName = _appPrefs.getLogoName();

            getBackground bg = new getBackground();
            bg.execute();

        } else {
            setContentView(R.layout.activity_default_splash);
        }


        /**
         * Test if they have an api high enough to be able to set their own icon otherwise goes to
         * spalsh page with default
         */


        Thread timer = new Thread() {
            public void run() {
                try {
                    //Displays splash image for 1 second
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //Checks if logged in and where to go next
                    Boolean loggedin = loginPreferences.getBoolean("loggedin", false);
                    if (loggedin) {
                        UserID = loginPreferences.getInt("UserID", 0);
                        if (UserID > 0) {
                            UserGUID = loginPreferences.getString("UserGUID", "");
                            new getUserByIDAsync().execute();
                        } else {
                            loginEditor.clear();
                            loginEditor.putBoolean("loggedin", false);
                            loginEditor.commit();

                        }
                    } else {

                        Intent openMainPage = new Intent("com.akadasoftware.danceworksonline.Login");
                        startActivity(openMainPage);
                    }

                }
            }
        };
        timer.start();

    }

    public class getBackground extends AsyncTask<Data, Void, Void> {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected Void doInBackground(Data... datas) {
            final FrameLayout fm = (FrameLayout) findViewById(R.id.container);

            /**
             * Here we test the newLogoName(logo name gotten from web service) with the LogoName which
             * is stored with app Prefs. If they are different the logo has changed and we need to
             * create a new link. Otherwise continue using the same logo, we then save the new
             * logo name for future reference
             */


            logoUrl = "https://a77f5e8a78b68f5605b7-acb3eef5f1b156a5a4173453f521b028.ssl.cf1.rackcdn.com/" + LogoName;

            final Drawable background = ImageOperations(logoUrl, "company_logo");


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (LogoName.equals("") || LogoName.equals("anyType{}")) {
                        fm.setBackground(getResources().getDrawable(R.drawable.logo_dancworks));
                    } else {
                        fm.setBackground(background);
                    }

                    /**
                     *  isTablet = getResources().getBoolean(R.bool.isTablet);
                     if (!isTablet) {
                     fm.setScaleX(Float.valueOf("0.5"));
                     fm.setScaleY(Float.valueOf("0.5"));
                     }
                     */

                }
            });

            return null;
        }

    }

    private Drawable ImageOperations(String url, String saveFilename) {
        try {
            InputStream is = (InputStream) this.fetch(url);
            Drawable d = Drawable.createFromStream(is, saveFilename);
            return d;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object fetch(String address) throws IOException {
        URL url = new URL(address);
        Object content = url.getContent();
        return content;
    }

    class Data {

        static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
        private static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
    }

    /**
     * Finds user based on ID
     */
    public class getUserByIDAsync extends AsyncTask<Data, Void, User> {

        @Override
        protected User doInBackground(Data... data) {

            return oGlobals.getUserByID(_appPrefs);
        }

        protected void onPostExecute(User oNewUser) {
            if (oNewUser.UserID > 0) {
                /**
                 * The only way to use the Json parser is to save it as an array. So we create one to
                 * parse the user we just created.
                 */
                ArrayList<User> userarray = new ArrayList<User>();
                userarray.add(0, oNewUser);

                Globals global = new Globals();
                String strQuery = global.BuildQuery(oUser.AccountSelection, oUser.AccountSort, "Accounts");

                _appPrefs.saveSchID(oNewUser.SchID);
                _appPrefs.saveUserID(oNewUser.UserID);
                _appPrefs.saveUserGUID(oNewUser.UserGUID);
                _appPrefs.saveAccountSortBy(oNewUser.AccountSort);
                _appPrefs.saveAccountSelectBy(oNewUser.AccountSelection);
                _appPrefs.saveStudentSortBy(oNewUser.StudentSort);
                _appPrefs.saveStudentSelectBy(oNewUser.StudentSelection);
                _appPrefs.saveAccountQuery(strQuery);

                /**
                 * Check if app is debuggin, if so loads an empty list so that the debugging goes
                 * faster and we don't have to wait forever.
                 */
                boolean isBeingDebugged = android.os.Debug.isDebuggerConnected();
                if (isBeingDebugged) {
                    getAccountsListAsync getAccounts = new getAccountsListAsync();
                    getAccounts.execute();


                    getStudentsListAsync getStudents = new getStudentsListAsync();
                    getStudents.execute();


                    getSchoolAsync getSchool = new getSchoolAsync();
                    getSchool.execute();


                } else {
                    /**
                     * Loads all lists intially to speed up app. The majority of the async method is
                     * in globals but the actual call itself is here so it can be run before generating
                     * views so we don't end up with an empty list
                     */
                    getAccountsListAsync getAccounts = new getAccountsListAsync();
                    getAccounts.execute();

                    getStudentsListAsync getStudents = new getStudentsListAsync();
                    getStudents.execute();

                    getSchoolAsync getSchool = new getSchoolAsync();
                    getSchool.execute();

                }

                Intent openMainPage = new Intent("com.akadasoftware.danceworksonline.Home");
                startActivity(openMainPage);
            } else {
                Intent openMainPage = new Intent("com.akadasoftware.danceworksonline.Login");
                startActivity(openMainPage);
            }

        }

    }

    /**
     * Gets school
     */
    private class getSchoolAsync extends AsyncTask<Data, Void, School> {

        protected School doInBackground(Data... data) {
            return oGlobals.getSchool(_appPrefs);
        }

        protected void onPostExecute(School oSchool) {
            ArrayList<School> schoolArrayList = new ArrayList<School>();
            schoolArrayList.add(0, oSchool);
            _appPrefs.saveSchool(schoolArrayList);
            _appPrefs.saveSessionID(oSchool.SessionID);
            _appPrefs.saveCCProcessor(oSchool.CCProcessor);
            _appPrefs.saveST1Rate(oSchool.ST1Rate);
            _appPrefs.saveST2Rate(oSchool.ST2Rate);

            String newLogoName = oSchool.LogoName;
            String LogoName = _appPrefs.getLogoName();

            if (newLogoName != LogoName)
                _appPrefs.saveLogoName(newLogoName);

            SessionID = oSchool.SessionID;

            getStudentClassesAsync getClasses = new getStudentClassesAsync();
            getClasses.execute();

        }
    }


    /**
     * Gets list of accounts
     */
    private class getAccountsListAsync extends
            AsyncTask<Data, Void, ArrayList<Account>> {

        @Override
        protected ArrayList<Account> doInBackground(Data... data) {

            return oGlobals.getAccounts(_appPrefs);
        }

        protected void onPostExecute(ArrayList<Account> result) {
            _appPrefs.saveAccounts(result);

        }
    }


    /**
     * Gets list of students
     */
    private class getStudentsListAsync extends
            AsyncTask<Data, Void, ArrayList<Student>> {

        @Override
        protected ArrayList<Student> doInBackground(Data... data) {

            /**
             * 0 menas loads all students
             */
            return oGlobals.getStudents(_appPrefs, 0);
        }

        protected void onPostExecute(ArrayList<Student> result) {
            _appPrefs.saveStudents(result);

        }
    }

    /**
     * Gets list of Classes, runs in onpost of school to ensure we have a session id
     */
    public class getStudentClassesAsync extends
            AsyncTask<Globals.Data, Void, ArrayList<SchoolClasses>> {

        @Override
        protected ArrayList<SchoolClasses> doInBackground(Globals.Data... data) {


            return oGlobals.getClasses(_appPrefs, SessionID, 0, 0);
        }

        protected void onPostExecute(ArrayList<SchoolClasses> result) {
            _appPrefs.saveSchoolClassList(result);
            _appPrefs.saveAccessAllClasses(false);

        }
    }
}