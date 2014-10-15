package com.akadasoftware.danceworksonline;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.akadasoftware.danceworksonline.Classes.Account;
import com.akadasoftware.danceworksonline.Classes.AppPreferences;
import com.akadasoftware.danceworksonline.Classes.Globals;
import com.akadasoftware.danceworksonline.Classes.School;
import com.akadasoftware.danceworksonline.Classes.SchoolClasses;
import com.akadasoftware.danceworksonline.Classes.Student;
import com.akadasoftware.danceworksonline.Classes.User;

import java.util.ArrayList;

public class Login extends ActionBarActivity {

    private static SharedPreferences loginPreferences;
    private static SharedPreferences.Editor loginEditor;
    private AppPreferences _appPrefs;

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _appPrefs = new AppPreferences(getApplicationContext());
        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
    }


    /**
     * A Login fragment containing edit texts for the email and password and various other
     * views.
     */
    public static class LoginFragment extends Fragment {
        View rootView;
        Activity activity;
        private AppPreferences _appPrefs;
        Button btnLogin;
        EditText etEmail, etPassword;
        CheckBox chkShowPassword, chkRemember;
        TextView tvForgotPassword;
        TextView tvError;
        String strEmail, strPassword;
        Globals oGlobals;
        User oUser;
        int SessionID;

        public LoginFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
            activity = this.getActivity();
            _appPrefs = new AppPreferences(activity);
            _appPrefs.saveNavDrawerPosition(0);
            oGlobals = new Globals();

            btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
            etEmail = (EditText) rootView.findViewById(R.id.etEmail);
            etPassword = (EditText) rootView.findViewById(R.id.etPassword);
            chkShowPassword = (CheckBox) rootView.findViewById(R.id.chkShowPassword);
            chkRemember = (CheckBox) rootView.findViewById(R.id.chkRemember);
            tvForgotPassword = (TextView) rootView.findViewById(R.id.tvForgotPassword);
            tvError = (TextView) rootView.findViewById(R.id.tvError);
            //
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            chkShowPassword.setChecked(false);
            chkShowPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (chkShowPassword.isChecked()) {
                        etPassword.setTransformationMethod(null);
                        etPassword.setSelection(etPassword.getText().length());
                    } else {
                        etPassword.setTransformationMethod(new PasswordTransformationMethod());
                        etPassword.setSelection(etPassword.getText().length());
                    }
                }
            });
            tvError.setText("");
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvError.setText("");
                    strEmail = etEmail.getText().toString();
                    strPassword = etPassword.getText().toString();
                    getUserAsync getNewUser = new getUserAsync();
                    getNewUser.execute();
                }
            });

            loginPreferences = activity.getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginEditor = loginPreferences.edit();
            //Boolean loggedin = loginPreferences.getBoolean("loggedin", false);

            tvForgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etEmail.getText().length() > 0)
                        _appPrefs.saveEmail(etEmail.getText().toString());
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new RecoverPasswordFragment())
                            .commit();
                }
            });
            return rootView;

        }

        @Override
        public void onStart() {
            super.onStart();
        }

        class Data {

            static final String NAMESPACE = "http://app.akadasoftware.com/MobileAppWebService/";
            private static final String URL = "http://app.akadasoftware.com/MobileAppWebService/Android.asmx";
        }


        /**
         * Gets User for the initial login or if they did not check the remember me check box
         */
        public class getUserAsync extends AsyncTask<Globals.Data, Void, User> {

            @Override
            protected User doInBackground(Globals.Data... data) {

                return oGlobals.getUser(_appPrefs, strEmail, strPassword);

            }

            protected void onPostExecute(User inputUser) {
                oUser = inputUser;
                if (oUser.UserID > 0) {
                    if (chkRemember.isChecked()) {
                        loginEditor.putBoolean("loggedin", true);
                        loginEditor.putInt("UserID", oUser.UserID);
                        loginEditor.putString("UserGUID", oUser.UserGUID);
                        loginEditor.commit();
                    } else {
                        loginEditor.clear();
                        loginEditor.putBoolean("loggedin", false);
                        loginEditor.commit();
                    }

                    /**
                     * Creates Query string that handles the sorting and selecting of accounts
                     * when the accounts list is populated
                     */
                    Globals global = new Globals();
                    String strQuery = global.BuildQuery(oUser.AccountSelection, oUser.AccountSort, "Accounts");

                    ArrayList<User> userarray = new ArrayList<User>();
                    userarray.add(0, oUser);

                    _appPrefs.saveSchID(oUser.SchID);
                    _appPrefs.saveUserID(oUser.UserID);
                    _appPrefs.saveUserGUID(oUser.UserGUID);
                    _appPrefs.saveAccountSortBy(oUser.AccountSort);
                    _appPrefs.saveAccountSelectBy(oUser.AccountSelection);
                    _appPrefs.saveStudentSortBy(oUser.StudentSort);
                    _appPrefs.saveStudentSelectBy(oUser.StudentSelection);
                    _appPrefs.saveUser(userarray);
                    _appPrefs.saveAccountQuery(strQuery);

                    boolean isBeingDebugged = android.os.Debug.isDebuggerConnected();
                    if (isBeingDebugged) {
                        ArrayList<Account> AccountsArray = new ArrayList<Account>();
                        _appPrefs.saveAccounts(AccountsArray);

                        ArrayList<Student> StudentArray = new ArrayList<Student>();
                        _appPrefs.saveStudents(StudentArray);

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
                    tvError.setText("Login information is incorrect");
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
         * Gets account list
         */
        private class getAccountsListAsync extends
                AsyncTask<Globals.Data, Void, ArrayList<Account>> {

            @Override
            protected ArrayList<Account> doInBackground(Globals.Data... data) {

                return oGlobals.getAccounts(_appPrefs);
            }

            protected void onPostExecute(ArrayList<Account> result) {
                _appPrefs.saveAccounts(result);

            }
        }

        /**
         * Gets student list
         */
        private class getStudentsListAsync extends
                AsyncTask<Globals.Data, Void, ArrayList<Student>> {

            protected ArrayList<Student> doInBackground(Globals.Data... data) {
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

    public static class RecoverPasswordFragment extends Fragment {
        View rootView;
        Activity activity;
        private AppPreferences _appPrefs;
        TextView tvMessage;
        TextView tvResponse;
        EditText etRecoverPassword;
        Button btnSendEmail;
        Button btnCancel;


        public RecoverPasswordFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_recover_password, container, false);
            activity = this.getActivity();
            _appPrefs = new AppPreferences(activity);
            btnSendEmail = (Button) rootView.findViewById(R.id.btnSendEmail);
            btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
            etRecoverPassword = (EditText) rootView.findViewById(R.id.etRecoverPassword);
            tvMessage = (TextView) rootView.findViewById(R.id.tvMessage);
            tvResponse = (TextView) rootView.findViewById(R.id.tvResponse);
            //

            etRecoverPassword.setText(_appPrefs.getEmailPref());
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new LoginFragment())
                            .commit();
                }
            });

            return rootView;

        }

    }


}
